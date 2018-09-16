package io.dynabiz.web.context;


import io.dynabiz.util.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Component
public class ServiceContextHolder {
    //用于存储ServiceContextCache到线程上下文
    private static final ThreadLocal<ServiceContextCache> localContextCache = new ThreadLocal<>();

    private static ServiceContextSerializer serializer;
    private static ServiceContextStorage storage;
    @Autowired
    public ServiceContextHolder(ServiceContextSerializer serializer,
                           ServiceContextStorage storage){
        ServiceContextHolder.serializer = serializer;
        ServiceContextHolder.storage = storage;
    }

    /**
     * 获取上下文内容
     * @param clazz
     * @param <T>
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T extends AbstractServiceContext> T get(Class<T> clazz){
        ServiceContextCache cache = localContextCache.get();
        if(cache == null) throw new IllegalStateException();
        String className = clazz.getName();
        Map<String, AbstractServiceContext> contextMap = cache.getContextMap();
        if(contextMap.containsKey(className)) return (T)contextMap.get(className);
        Object storedData = storage.read(cache.getSessionId(), clazz.getName());
        if(storedData == null){
            return null;
        }
        AbstractServiceContext context = serializer.deserialize(clazz, storedData);
        context.makeSnapshot();
        cache.getContextMap().put(clazz.getName(), context);
        return (T)context;
    }

    /**
     * 开启会话，当不存在上下文时自动创建
     * @return
     */
    public static String start(ServletRequest httpRequest){
        String sessionId = null;
        if(httpRequest != null){
            sessionId = getSessionId(httpRequest);
        }
        if(sessionId == null){
            do{
                sessionId = RandomString.nextHex();
            } while(storage.exists(sessionId)); //避免生成相同的随机字符
        }
        //保存到线程上下文
        //if(null != localContextCache.get()) throw new IllegalStateException("Context session already stared!");
        ServiceContextCache cache = new ServiceContextCache(sessionId);
        localContextCache.set(cache);
        return sessionId;
    }

    public static String start(){
        return start(getRequest());
    }

    /**
     * 立即检查并保存已经修改的数据
     */
    @SuppressWarnings("unchecked")
    public static void flush(){
        ServiceContextCache cache = localContextCache.get();
        Map<String, AbstractServiceContext> contextMap = cache.getContextMap();
        String sessionId = cache.getSessionId();
        for(Map.Entry<String, AbstractServiceContext> i : contextMap.entrySet()){
            //如果被修改，保存新数据
            if(i.getValue().isModified()){
                storage.save(sessionId, i.getKey(), serializer.serialize(i.getValue()));
            }

        }
    }

    /**
     * 关闭上下文
     */
    public static void close(){
        ServiceContextCache cache = localContextCache.get();
        if(null == cache){
           return;
        }
        flush();
        localContextCache.remove();
    }

    /**
     * 清除
     */
    public static void clear(){
        ServiceContextCache cache = localContextCache.get();
        if(null == cache){
            return;
        }
        storage.removeAll(cache.getSessionId());
        localContextCache.remove();
    }

    /**
     * 设置，更新上下文
     * @param context
     */
    public static void put(AbstractServiceContext context){
        ServiceContextCache cache = localContextCache.get();
        Map<String, AbstractServiceContext> contextMap = cache.getContextMap();
        contextMap.put(context.getClass().getName(), context);
    }

    /**
     * 删除上下文
     * @param clazz
     * @param <T>
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T extends AbstractServiceContext> T remove(Class<T> clazz){
        ServiceContextCache cache = localContextCache.get();
        Map<String, AbstractServiceContext> contextMap = cache.getContextMap();
        String className = clazz.getName();
        if(!contextMap.containsKey(className)) return null;
        return (T)contextMap.remove(className);
    }


    public static String getSessionId(ServletRequest request){
        return ((HttpServletRequest)request).getHeader("X-SERVICE-SESSION-ID");
    }

    public static String getSessionId(){
        ServiceContextCache cache = localContextCache.get();

        if(cache == null){
            HttpServletRequest request = getRequest();
            return request != null ? request.getHeader("X-SERVICE-SESSION-ID") : null;
        }

        else return cache.getSessionId();
    }

    private static HttpServletRequest getRequest(){
        if(RequestContextHolder.getRequestAttributes() == null) return null;
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }

}
