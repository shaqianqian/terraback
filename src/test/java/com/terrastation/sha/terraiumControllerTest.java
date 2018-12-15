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

import com.terrastation.sha.Controller.terraiumController;
import com.terrastation.sha.domain.reptile;
import com.terrastation.sha.domain.terraium;
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
public class terraiumControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private terraiumController terrariumController;

    @Test
    public void testFindAll() throws Exception {

        MvcResult result = mockMvc.perform(get("/terrarium")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isFound())    //statut HTTP de la réponse
                .andReturn();

        // ceci est une redondance, car déjà vérifié par: isFound())
        assertEquals("Réponse incorrecte", HttpStatus.FOUND.value(), result.getResponse().getStatus());

        //on s'assure que la méthode de service getAllUsers() a bien été appelée
        verify(terrariumController).findall();
    }

    @Test
    public void testSaveUser() throws Exception {

        //on exécute la requête
        mockMvc.perform(MockMvcRequestBuilders.post("/terrarium")
                .contentType(MediaType.APPLICATION_XML)
                .accept(MediaType.APPLICATION_XML)
                .content("<terraium><temperature>120</temperature><humite>20</humite></terraium>"))
                .andExpect(status().isCreated());

        //on s'assure que la méthode de service saveOrUpdateUser(User) a bien été appelée
        verify(terrariumController).add(120,20);

    }

    @Test
    public void testDeleteReptile() throws Exception {
        // on exécute le test
        mockMvc.perform(MockMvcRequestBuilders.delete("/terrarium/{id}", new Long(1)))
                .andExpect(status().isGone());

        // On vérifie que la méthode de service deleteUser(Id) a bien été appelée
        verify(terrariumController).deleteTerraium(1);
    }

    @Test
    public void testUpdateReptile() throws Exception {
        terraium rep1=new terraium();
        //on exécute la requête
        mockMvc.perform(MockMvcRequestBuilders.put("/terrarium/{id}",new Long(1))
                .contentType(MediaType.APPLICATION_XML)
                .accept(MediaType.APPLICATION_XML)
                .content("<terraium><temperature>120</temperature></terraium>"))
                .andExpect(status().isOk());
        Optional<terraium> rep= terrariumController.findById(1);
        if(rep.isPresent())
            rep1=rep.get();
        rep1.setTemperature(120);
        //on s'assure que la méthode de service saveOrUpdateUser(User) a bien été appelée
        verify(terrariumController).updateTerraium(1,rep1);

    }
}
