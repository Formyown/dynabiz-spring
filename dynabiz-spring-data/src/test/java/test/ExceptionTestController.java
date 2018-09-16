package test;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
public class ExceptionTestController {
    @GetMapping("exception")
    public void exception(){

    }

}
