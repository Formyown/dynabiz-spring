package test;

import io.dynabiz.workflow.stepped.Param;
import io.dynabiz.workflow.stepped.Step;
import io.dynabiz.workflow.stepped.SteppedTask;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Scope("prototype")
@Service
public class SimpleSteppedTask implements SteppedTask {


    private long number = 0;

    public long getNumber() {
        return number;
    }

    public void setNumber(long number) {
        this.number = number;
    }

    @Step(index = 0,ttl = 1000 * 60 * 15)
    public void step0(@Param("arg") long arg) {
        System.out.println("Test task step 0.. arg:" + arg);
        number += arg;
        System.out.println(number);
    }

    @Step(index = 1,ttl = 1000 * 60 * 15,retry = 5)
    public void step1(@Param("arg0") long arg0, @Param("arg1") long arg1) {
        System.out.println("Test task step 1.. arg0:" + arg0 + " arg1:" + arg1);
        number += arg0 + arg1;
        System.out.println(number);
    }

    @Step(index = 2,ttl = 1000 * 60 * 15,retry = 5)
    public long step2(){
        System.out.println("Test task step 2." + number);
        return number;
    }

}
