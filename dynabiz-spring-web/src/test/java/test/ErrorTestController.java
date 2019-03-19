package test;

import io.dynabiz.BusinessException;
import io.dynabiz.std.exception.LoginException;
import io.dynabiz.web.context.config.EnableServiceContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RequestMapping("")
@RestController
public class ErrorTestController {

    @RequestMapping("throwerror")
    public String throwError(){
        throw LoginException.WRONG_NAME;
    }
}
