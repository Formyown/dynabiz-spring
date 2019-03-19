/*
 * Copyright (c) 2018, Deyu Heng. All rights reserved.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NON INFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package test;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GlobalControllerErrorHandlerTest {

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mock;

    @Before
    public void setUp (){
        mock = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    public void testMapper() throws Exception {
        mock.perform(MockMvcRequestBuilders.get("/throwerror"))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk());
        mock.perform(MockMvcRequestBuilders.post("/throwerror"))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk());
        mock.perform(MockMvcRequestBuilders.delete("/throwerror"))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk());
        mock.perform(MockMvcRequestBuilders.head("/throwerror"))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk());
        mock.perform(MockMvcRequestBuilders.put("/throwerror"))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk());

    }

}
