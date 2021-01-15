package org.dynabiz.workflow.stepped;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.dynabiz.std.exception.TokenException;
import org.dynabiz.util.Assert;
import org.dynabiz.util.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.rmi.ServerException;

@Component
public class SteppedTaskExecutor {
    private static SteppedTaskStateStorage storage;
    private static ObjectMapper mapper;
    private static ApplicationContext springContext;


    private final static int TOKEN_LEN = 15;

    @Autowired
    public SteppedTaskExecutor(SteppedTaskStateStorage storage, ObjectMapper mapper, ApplicationContext context) {
        SteppedTaskExecutor.storage = storage;
        SteppedTaskExecutor.mapper = mapper;
        SteppedTaskExecutor.springContext = context;
    }

    public static <T extends SteppedTask> SteppedTaskResult next(Class<T> tClass, String token,
                                                                 AbstractSteppedTaskArgumentsResolver resolver) throws Exception{
        return next(tClass, token, resolver, null);
    }

    public static <T extends SteppedTask> SteppedTaskResult next(Class<T> tClass, String token,
                                                                 AbstractSteppedTaskArgumentsResolver resolver,
                                                          String key) throws Exception{

        Assert.beTrue(token.length() == TOKEN_LEN * 2, TokenException.BAD_TOKEN);

        SteppedTaskState state = storage.find(token);
        SteppedTask task = springContext.getBean(tClass);
        // 恢复状态
        mapper.readerForUpdating(task).readValue(state.getData());

        int stepIndex = state.getStepIndex() + 1; // 先获取下一步

        StepMethodData stepData = Assert.notNull(findStepMethod(tClass, stepIndex),
                new RuntimeException("There is no @Step that refers to " + stepIndex));

        //执行下一步
        StepReturnData retData = executeStep(task, stepData.getMethod(), resolver);


        long ttl = stepData.getStepMetaData().ttl() ;
        if(retData.value instanceof Exception){
            // 发生错误，增加重试次数
            state.increaseRetryNumber(1);
            saveState(state, ttl);
            if(retData.value instanceof ServerException)
                throw (ServerException)retData.value;
            else
                throw (Exception)retData.value;
        }

        if(stepIndex == state.getMaxStepIndex()){
            // 最后一步，删除token
            storage.remove(token);
            token = null;
        }
        else{
            //成功执行，移动至下一步
            state.increaseStepIndex(1);
            state.setRetryNumber(0); //清空重试次数
            state.setLastInvoked(System.currentTimeMillis());
            state.setData(retData.data);

            StepMethodData lastStep = findStepMethod(tClass, stepIndex - 1);
            if(lastStep == null || !lastStep.getStepMetaData().renewable()){ //非可重新开始任务
                storage.remove(token);
            }

            updateState(token, state, ttl);
        }


        SteppedTaskResult result = new SteppedTaskResult();
        result.setExpiredAt(state.getLastInvoked() + stepData.getStepMetaData().ttl());
        result.setIndex(state.getStepIndex());
        result.setLast(state.getMaxStepIndex() == stepIndex);
        result.setToken(token);
        result.setRet(retData.value);
        return result;
    }

    public static <T extends SteppedTask> SteppedTaskResult start(Class<T> tClass,
                                                                  AbstractSteppedTaskArgumentsResolver resolver) throws Exception{
        return start(tClass, resolver, null);
    }

    /**
     * 初始化分步任务并执行第一步
     * @param tClass
     * @param key
     * @param resolver
     * @param <T>
     * @return
     * @throws Exception
     */
    public static <T extends SteppedTask> SteppedTaskResult start(Class<T> tClass,
                                                                  AbstractSteppedTaskArgumentsResolver resolver,
                                                                  String key) throws Exception{

        //找到找到0号步骤
        StepMethodData stepMethodData = Assert.notNull(findStepMethod(tClass, 0),
                new RuntimeException("There is no @Step that refers to 0"));

        //实例化脚本
        SteppedTask task = springContext.getBean(tClass);

        SteppedTaskState state = new SteppedTaskState();
        state.setStepIndex(0);
        state.setMaxStepIndex(getMaxStep(tClass));

        StepReturnData retData = executeStep(task ,stepMethodData.getMethod(), resolver);
        checkRetValue(retData);

        state.setData(mapper.writeValueAsString(task));
        state.setLastInvoked(System.currentTimeMillis());

        long ttl = stepMethodData.getStepMetaData().ttl();

        String token = saveState(state, ttl);

        return new SteppedTaskResult(0, state.getMaxStepIndex() == 0,
                System.currentTimeMillis() + ttl, token, retData.value);
    }

    private static void checkRetValue(StepReturnData retData) throws Exception {
        if(retData.value instanceof Exception){
            if(retData.value instanceof ServerException)
                throw (ServerException)retData.value;
            else
                throw (Exception)retData.value;
            //有错误发生直接抛出
        }
    }

    /**
     * 查找指定步骤注解
     * @param tClass
     * @param index
     * @return
     */
    private static StepMethodData findStepMethod(Class tClass, int index){
        StepMethodData result = null;
        Method[] methods = tClass.getDeclaredMethods();
        Step step;
        for(Method m : methods){
            if(null != (step = m.getAnnotation(Step.class)) && step.index() == index){
                result = new StepMethodData(step, m);
                break;
            }
        }
        return result;
    }

    private static int getMaxStep(Class tClass){
        int max = 0;

        Method[] methods = tClass.getDeclaredMethods();
        for(Method m : methods){
            Step step;
            if(null != (step = m.getAnnotation(Step.class))){
                max = Math.max(step.index(), max);
            }
        }
        return max;
    }

    private static StepReturnData executeStep(SteppedTask taskClass, Method method,
                             AbstractSteppedTaskArgumentsResolver resolver) throws JsonProcessingException {
        Object ret;
        try {
            if(resolver == null){
                ret = method.invoke(taskClass);
            }
            else{
                ret = method.invoke(taskClass, resolver.resolve(method.getParameters()));
            }


        } catch (Exception e){
            return new StepReturnData(e,null);
        }
        return new StepReturnData(ret,  mapper.writeValueAsString(taskClass));
    }

    private static void updateState(String token, SteppedTaskState state, long ttl){
        storage.save(token, state, ttl, true);
    }

    private static String saveState(SteppedTaskState store, long ttl) {
        String token;
        do{
            token = RandomString.nextHex(TOKEN_LEN);
        }while(!storage.save(token, store, ttl, false));
        //设置过期时间
        storage.updateExpire(token, ttl);
        return token;
    }

//    private static void keyLimitationCheck(,String name){
//        //String Assert.beFalse(template.hasKey(LIMIT_NS + name)) throw
//    }

    private static class KeyLimitStore{
        private int retryNumber;

    }

    private static class StepReturnData{
        public StepReturnData(Object value, String data){
            this.exception = null;
            this.value = value;
            this.data = data;
        }

        public StepReturnData(Exception exception){
            this.exception = exception;
            this.value = null;
        }
        private String data;
        private Object value;
        private Exception exception;
    }



}
