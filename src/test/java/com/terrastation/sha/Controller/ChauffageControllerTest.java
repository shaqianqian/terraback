package com.terrastation.sha.Controller;

import com.terrastation.sha.Entity.Chauffage;
import com.terrastation.sha.Enums.ResultEnum;
import com.terrastation.sha.Exception.ParameterErrorException;
import com.terrastation.sha.Repositary.ChauffageRepository;
import com.terrastation.sha.Util.ResultUtil;
import com.terrastation.sha.VO.ResultVO;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.WebApplicationContext;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import java.io.UnsupportedEncodingException;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ChauffageControllerTest {

    @Autowired
    private ChauffageRepository chauffageRepository;
    @Autowired
    private ChauffageController chauffageController;
    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;
    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();   //构造MockMvc
    }
    @Test
    public void findallTest() {
        chauffageRepository.findAll();

    }
//    @Test
//    public void findAllMockTest() throws Exception {
//        MvcResult mvcResult = mockMvc.perform(get("/terrarium/chauffage/getAll")
////                .param("dayNum","8")
////                .param("date","2017-7-18 00:00:00")
////                .param("pageNum","1")
////                .param("pageSize","10")
// )
//                .andExpect(status().isOk())
//                .andDo(MockMvcResultHandlers.print())
//                .andReturn();
//        System.out.println("resultat " + mvcResult.getResponse().getContentAsString());
//    }
//
//    @Test
//    @Transactional
//    @Rollback
//    public void addMockMoisDebutEarilerThanMoisFinTest() throws Exception {
//        MvcResult mvcResult = null;
//            mvcResult = mockMvc.perform(post("/terrarium/chauffage/add")
//                    .param("moisDebut","12")
//                    .param("moisFin","10")
//                    .param("heureDebut","15")
//                    .param("heureFin","20")
//                    .param("min","20")
//                    .param("max","30"))
//                    .andExpect(status().is5xxServerError())
//                    .andDo(MockMvcResultHandlers.print())
//                    .andReturn();
//
//        System.out.println("resultat " + mvcResult.getResponse().getContentAsString());
//
//    }
//    @Test
//    @Transactional
//    @Rollback
//    public void addMockHeureDebutEarilerThanHeureFinTest() throws Exception {
//        MvcResult mvcResult = null;
//        mvcResult = mockMvc.perform(post("/terrarium/chauffage/add")
//                .param("moisDebut","5")
//                .param("moisFin","10")
//                .param("heureDebut","45")
//                .param("heureFin","20")
//                .param("min","20")
//                .param("max","30"))
//                .andExpect(status().is5xxServerError())
//                .andDo(MockMvcResultHandlers.print())
//                .andReturn();
//
//        System.out.println("resultat " + mvcResult.getResponse().getContentAsString());
//
//    }
//    @Test
//    @Transactional
//    @Rollback
//    public void addMockTemperatureMoinsZeroTest() throws Exception {
//        MvcResult mvcResult = null;
//        mvcResult = mockMvc.perform(post("/terrarium/chauffage/add")
//                .param("moisDebut","5")
//                .param("moisFin","10")
//                .param("heureDebut","21")
//                .param("heureFin","23")
//                .param("min","-10")
//                .param("max","30"))
//                .andExpect(status().is5xxServerError())
//                .andDo(MockMvcResultHandlers.print())
//                .andReturn();
//
//        System.out.println("resultat " + mvcResult.getResponse().getContentAsString());
//
//    }
//
//    @Test
//    public void getEtatInterrupterProgrammableTest() throws Exception {
//
//        MvcResult mvcResult = mockMvc.perform(get("/terrarium/chauffage/getEtatChauffage")
//        )
//                .andExpect(status().isOk())
//                .andDo(MockMvcResultHandlers.print())
//                .andReturn();
//        System.out.println("resultat " + mvcResult.getResponse().getContentAsString());
//
//
//
//    }
//

}