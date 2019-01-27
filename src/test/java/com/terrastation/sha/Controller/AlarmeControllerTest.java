package com.terrastation.sha.Controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.terrastation.sha.Entity.Alarme;
import com.terrastation.sha.Entity.Lumiere;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class AlarmeControllerTest {
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    public void addProfil() throws Exception {
        MvcResult mvcResult = null;
        mvcResult = mockMvc.perform(post("/terrarium/alarme/addProfil")
                .param("email","hajar.bouhallaf@gmail.com"))
                .andExpect(status().is2xxSuccessful())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

        System.out.println("resultat " + mvcResult.getResponse().getContentAsString());
    }

    @Test
    public void addAlarme() throws Exception {
        Alarme alarme = new Alarme("CH",5,9,4,5,"attention");
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson=ow.writeValueAsString(alarme);

        MvcResult mvcResult = null;
        mvcResult = mockMvc.perform(post("/terrarium/alarme/addAlarme")
                .contentType(APPLICATION_JSON)
                .content(requestJson)
                .accept(APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

        System.out.println("resultat " + mvcResult.getResponse().getContentAsString());
    }

    @Test
    public void alarmeTemperature() throws Exception {

        MvcResult mvcResult = mockMvc.perform(get("/terrarium/alarme/sendEmailTemperature")
        )
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        System.out.println("resultat " + mvcResult.getResponse().getContentAsString());

    }
}
