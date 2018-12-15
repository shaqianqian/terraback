package com.terrastation.sha;


package com.bnguimgo.springbootrestserver.controller;

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

import com.terrastation.sha.Controller.reptileController;
import com.terrastation.sha.Controller.terraiumController;
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
@WebMvcTest(terraiumController.class)
public class ReptileControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private com.terrastation.sha.Controller.reptileController reptileController;

    @Test
    public void testFindAll() throws Exception {

        MvcResult result = mockMvc.perform(get("/reptile")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isFound())    //statut HTTP de la réponse
                .andReturn();

        // ceci est une redondance, car déjà vérifié par: isFound())
        assertEquals("Réponse incorrecte", HttpStatus.FOUND.value(), result.getResponse().getStatus());

        //on s'assure que la méthode de service getAllUsers() a bien été appelée
        verify(reptileController).findall();
    }

    @Test
    public void testSaveUser() throws Exception {

        //on exécute la requête
        mockMvc.perform(MockMvcRequestBuilders.post("/reptile")
                .contentType(MediaType.APPLICATION_XML)
                .accept(MediaType.APPLICATION_XML)
                .content("<reptile><age>3</age><name>Python</name></reptile>"))
                .andExpect(status().isCreated());

        //on s'assure que la méthode de service saveOrUpdateUser(User) a bien été appelée
        verify(reptileController).add(3,"Python");

    }

    @Test
    public void testDeleteReptile() throws Exception {
        // on exécute le test
        mockMvc.perform(MockMvcRequestBuilders.delete("/reptile/{id}", new Long(1)))
                .andExpect(status().isGone());

        // On vérifie que la méthode de service deleteUser(Id) a bien été appelée
        verify(reptileController).deleteReptile(1);
    }

    @Test
    public void testUpdateReptile() throws Exception {
        reptile rep1=new reptile();
        //on exécute la requête
        mockMvc.perform(MockMvcRequestBuilders.put("/reptile/{id}",new Long(1))
                .contentType(MediaType.APPLICATION_XML)
                .accept(MediaType.APPLICATION_XML)
                .content("<reptile><name>Titon</name></reptile>"))
                .andExpect(status().isOk());
        Optional<reptile> rep= reptileController.findById(1);
        if(rep.isPresent())
            rep1=rep.get();
        rep1.setName("Titon");
        //on s'assure que la méthode de service saveOrUpdateUser(User) a bien été appelée
        verify(reptileController).updateReptile(1,rep1);

    }
}
