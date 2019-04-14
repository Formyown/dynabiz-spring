package test;

import org.dynabiz.std.exception.LoginException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RequestMapping("")
@RestController
public class TestController {

    @RequestMapping("throwerror")
    public String throwError(){
        throw LoginException.WRONG_NAME;
    }

    @RequestMapping("response")
    public String stringResponse(){
        return "This is test response body";
    }


}
