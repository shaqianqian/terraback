package com.terrastation.sha;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
//pour les méthodes HTTP
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//pour JSON
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//pour HTTP status
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.*;

import com.terrastation.sha.Controller.ChauffageController;
import com.terrastation.sha.Controller.reptileController;
import com.terrastation.sha.domain.Chauffage;
import com.terrastation.sha.domain.reptile;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;



@RunWith(SpringRunner.class)
@WebMvcTest(reptileController.class)
public class ChauffageControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ChauffageController chauffageController;

    @Test
    public void testFindAll() throws Exception {

        MvcResult result = mockMvc.perform(get("/chauffage")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isFound())    //statut HTTP de la réponse
                .andReturn();

        // ceci est une redondance, car déjà vérifié par: isFound())
        assertEquals("Réponse incorrecte", HttpStatus.FOUND.value(), result.getResponse().getStatus());

        //on s'assure que la méthode de service getAllUsers() a bien été appelée
        verify(chauffageController).findall();
    }

    @Test
    public void testSaveChauffage() throws Exception {

        //on exécute la requête
        mockMvc.perform(MockMvcRequestBuilders.post("/chauffage")
                .contentType(MediaType.APPLICATION_XML)
                .accept(MediaType.APPLICATION_XML)
                .content("<chauffage><dateDebut>14/12/2018</dateDebut><dateDebut>14/01/2019</dateDebut><min>45</min><max>120</max><etat>true</etat></reptile>"))
                .andExpect(status().isCreated());

        //on s'assure que la méthode de service saveOrUpdateUser(User) a bien été appelée
        verify(chauffageController).add(new Date(2018,12,14),new Date(14,01,2019),45,120,true);

    }




    @Test
    public void testUpdateReptile() throws Exception {
        Chauffage chauff1=new Chauffage();
        //on exécute la requête
        mockMvc.perform(MockMvcRequestBuilders.put("/chauffage/{id}",new Long(1))
                .contentType(MediaType.APPLICATION_XML)
                .accept(MediaType.APPLICATION_XML)
                .content("<chauffage><min>50</min></chauffage>"))
                .andExpect(status().isOk());
        Optional<Chauffage> chauff= chauffageController.findById(1);
        if(chauff.isPresent())
            chauff1=chauff.get();
        chauff1.setMin(50);
        //on s'assure que la méthode de service saveOrUpdateUser(User) a bien été appelée
        verify(chauffageController).updateChauffage(1,chauff1);

    }
}
