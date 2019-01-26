package com.terrastation.sha.Controller;



import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.terrastation.sha.Entity.Lumiere;
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

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
        public void addCorrectLumiere() throws Exception {

            Lumiere lumiere = new Lumiere(4,5,6,7);
            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
            ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
            String requestJson=ow.writeValueAsString(lumiere);

            MvcResult mvcResult = null;
            mvcResult = mockMvc.perform(post("/terrarium/lumiere/add")
                    .contentType(APPLICATION_JSON)
                    .content(requestJson)
                    .accept(APPLICATION_JSON))
                    .andExpect(status().is2xxSuccessful())
                    .andDo(MockMvcResultHandlers.print())
                    .andReturn();

            System.out.println("resultat " + mvcResult.getResponse().getContentAsString());

        }


    @Test
    @Transactional
    @Rollback
    public void addLumieremoisMoisDebutError() throws Exception {
        Lumiere lumiere = new Lumiere(6,5,6,7);
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson=ow.writeValueAsString(lumiere);

        MvcResult mvcResult = null;
        mvcResult = mockMvc.perform(post("/terrarium/lumiere/add")
                .contentType(APPLICATION_JSON)
                .content(requestJson)
                .accept(APPLICATION_JSON))
                .andExpect(status().is5xxServerError())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

        System.out.println("resultat " + mvcResult.getResponse().getContentAsString());

    }

    @Test
    @Transactional
    @Rollback
    public void addLumieremoisheureDebutError() throws Exception {
        Lumiere lumiere = new Lumiere(4,5,9,7);
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson=ow.writeValueAsString(lumiere);

        MvcResult mvcResult = null;
        mvcResult = mockMvc.perform(post("/terrarium/lumiere/add")
                .contentType(APPLICATION_JSON)
                .content(requestJson)
                .accept(APPLICATION_JSON))
                .andExpect(status().is5xxServerError())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

        System.out.println("resultat " + mvcResult.getResponse().getContentAsString());

    }

    @Test
    public void getEtatInterrupterLumiere() throws Exception {

        MvcResult mvcResult = mockMvc.perform(get("/terrarium/lumiere/getEtatLumiere")
        )
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        System.out.println("resultat " + mvcResult.getResponse().getContentAsString());



    }
    }

