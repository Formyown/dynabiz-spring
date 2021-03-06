package test;

import org.dynabiz.workflow.stepped.*;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.LinkedMultiValueMap;

import java.util.HashMap;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SteppedTaskTest {


    @Test
    public void test() throws Exception {
        System.out.println("+=========================================================+");
        System.out.println("||                TEST STEPPED TASK BASIC                ||");
        System.out.println("+=========================================================+");
        AbstractSteppedTaskArgumentsResolver resolver = new MapSteppedTaskArgumentResolver(new HashMap<String, Object>() {
            {
                this.put("arg", 5);
            }
        });
        SteppedTaskResult steppedTaskResult = SteppedTaskExecutor.start(SimpleSteppedTask.class, resolver);

        resolver = new MultiValueMapSteppedTaskArgumentResolver(new LinkedMultiValueMap<String, Object>() {
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