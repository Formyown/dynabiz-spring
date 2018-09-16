package test;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import io.dynabiz.spring.data.web.QueryScriptWebConfig;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = {QueryScriptWebConfig.class, QueryScriptController.class})
@AutoConfigureMockMvc
@EnableWebMvc
@PropertySource({"classpath:/application.yaml"})
public class QueryScriptTest {
    @Autowired
    private MockMvc mockMvc;



    @Test
    public void testQueryScript() throws Exception {
        System.out.println("+=========================================================+");
        System.out.println("||             QUERY SCRIPT RESOLVER TESTING             ||");
        System.out.println("+=========================================================+");

        String queryScript = "{\"age\":10}";

        MvcResult mvcResult = mockMvc.perform(get("/queryWithQueryScript?qscript={0}", queryScript)).andExpect(status().isOk()).andReturn();
        assert queryScript.equals(mvcResult.getResponse().getContentAsString());

        mvcResult = mockMvc.perform(get("/queryWithParamQueryScript?queryScript={0}", queryScript)).andExpect(status().isOk()).andReturn();
        assert queryScript.equals(mvcResult.getResponse().getContentAsString());

        mvcResult = mockMvc.perform(get("/queryWithPathVarQueryScript/{0}", queryScript)).andExpect(status().isOk()).andReturn();
        assert queryScript.equals(mvcResult.getResponse().getContentAsString());

    }
}
