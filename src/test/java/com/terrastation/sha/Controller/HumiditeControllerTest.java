package com.terrastation.sha.Controller;

import com.terrastation.sha.Service.Impl.TerrariumSensorServiceImpl;
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

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class HumiditeControllerTest {
    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();   //构造MockMvc
    }

    @Autowired
    private WebApplicationContext context;
    private MockMvc mockMvc;

    @Autowired
    private TerrariumSensorServiceImpl terraiumServiceSensor;


    @Test
    public void getCurrentHumiditeVOTest() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/terrarium/humidite/getCurrentHumiditesVO")
        )
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        System.out.println("resultat " + mvcResult.getResponse().getContentAsString());


    }



}