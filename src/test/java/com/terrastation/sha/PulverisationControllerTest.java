package com.terrastation.sha;

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

import com.terrastation.sha.Controller.PulverisationController;
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
@WebMvcTest(PulverisationController.class)
public class PulverisationControllerTest {

}
