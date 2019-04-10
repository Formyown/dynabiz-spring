package test;

import io.dynabiz.workflow.stepped.MapSteppedTaskArgumentResolver;
import io.dynabiz.workflow.stepped.SteppedTaskArgumentsResolver;
import io.dynabiz.workflow.stepped.SteppedTaskExecutor;
import io.dynabiz.workflow.stepped.SteppedTaskResult;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.LinkedMultiValueMap;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SteppedTaskTest {


    @Test
    public void test() throws Exception {
        System.out.println("+=========================================================+");
        System.out.println("||                TEST STEPPED TASK BASIC                ||");
        System.out.println("+=========================================================+");
        SteppedTaskArgumentsResolver resolver = new MapSteppedTaskArgumentResolver(new LinkedMultiValueMap<String, Object>() {
            {
                this.add("arg", 5);
            }
        });
        SteppedTaskResult steppedTaskResult = SteppedTaskExecutor.start(SimpleSteppedTask.class, resolver);
        resolver = new MapSteppedTaskArgumentResolver(new LinkedMultiValueMap<String, Object>() {
            {
                this.add("arg0", 10);
                this.add("arg1", 100);
            }
        });
        steppedTaskResult = SteppedTaskExecutor.next(SimpleSteppedTask.class, steppedTaskResult.getToken(), resolver);
        steppedTaskResult = SteppedTaskExecutor.next(SimpleSteppedTask.class, steppedTaskResult.getToken(), null);
        Assert.assertEquals(steppedTaskResult.getRet(), 115L);

    }


}