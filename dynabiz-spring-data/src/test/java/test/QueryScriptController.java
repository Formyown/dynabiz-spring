package test;

import org.dynabiz.spring.data.QueryScript;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/")
public class QueryScriptController {
    @GetMapping("queryWithQueryScript")
    public String queryWithQueryScript(QueryScript queryScript){
        System.out.println(queryScript.getScript());
        System.out.println(queryScript.getSql());

        return queryScript.getScript();
    }

    @GetMapping("queryWithPathVarQueryScript/{qs}")
    public String queryWithPathVarQueryScript(@PathVariable("qs") QueryScript queryScript){
        System.out.println(queryScript.getScript());
        System.out.println(queryScript.getSql());
        return queryScript.getScript();
    }

    @GetMapping("queryWithParamQueryScript")
    public String queryWithParamQueryScript(@RequestParam QueryScript queryScript){
        System.out.println(queryScript.getScript());
        System.out.println(queryScript.getSql());
        return queryScript.getScript();
    }
}
