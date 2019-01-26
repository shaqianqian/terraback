package com.terrastation.sha.Controller;



import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.transaction.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class LumiereControllerTest {
    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @Autowired
    private LumiereController terrariumService;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

        @Test
        @Transactional
        @Rollback
        public void add() throws Exception {
            MvcResult mvcResult = null;
            mvcResult = mockMvc.perform(post("/terrarium/lumiere/add")
                    .param("moisDebut","5")
                    .param("moisFin","10")
                    .param("heureDebut","21")
                    .param("heureFin","23"))
                    .andExpect(status().is4xxClientError())
                    .andDo(MockMvcResultHandlers.print())
                    .andReturn();

            System.out.println("resultat " + mvcResult.getResponse().getContentAsString());

        }
    }

