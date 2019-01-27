package com.terrastation.sha.Controller;

import com.terrastation.sha.Service.TerrariumGenereService;
import com.terrastation.sha.Service.TerrariumSensorService;
import com.terrastation.sha.Service.TerrariumService;
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
public class TerrariumControllerTest {
    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @Autowired
    private TerrariumService terrariumService;
    @Autowired
    private TerrariumGenereService terrariumGenereService;
    @Autowired
    private TerrariumSensorService terraiumServiceSensor;
    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();   //构造MockMvc
    }
    @Test
    public void findallTest() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/terrarium/getAll")
        )
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        System.out.println("resultat " + mvcResult.getResponse().getContentAsString());

    }

    @Test
    public void getCurrentParametresVOTest() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/terrarium/getParametres"))
                .andExpect(status().is5xxServerError())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        System.out.println("resultat " + mvcResult.getResponse().getContentAsString());

    }

    @Test
    public void getlistSensorsTest() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/terrarium/listSensors")
        )
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        System.out.println("resultat " + mvcResult.getResponse().getContentAsString());
    }


    @Test
    public void getCurrentParametresGenereVOTest() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/terrarium/getCurrentParametresGenereVO")
        )
                .andExpect(status().is4xxClientError())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        System.out.println("resultat " + mvcResult.getResponse().getContentAsString());

    }


}