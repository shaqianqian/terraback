package com.terrastation.sha;
/*
import com.terrastation.sha.Exception.ApiError;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.xml.ws.Response;

import static org.skyscreamer.jsonassert.JSONAssert.assertEquals;
import static org.springframework.test.util.AssertionErrors.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ShaApplicationTests {

    @Test
    public void contextLoads() {
    }

    @Test
    public void whenMethodArgumentMismatch_thenBadRequest() {
        Response response = givenAuth().get(URL_PREFIX + "/api/foos/ccc");
        ApiError error = response.as(ApiError.class);

        assertEquals(HttpStatus.BAD_REQUEST, error.getStatus());
        assertEquals(1, error.getErrors().size());
        assertTrue(error.getErrors().get(0).contains("should be of type"));
    }

    @Test
    public void whenNoHandlerForHttpRequest_thenNotFound() {
        Response response = givenAuth().delete(URL_PREFIX + "/api/xx");
        ApiError error = response.as(ApiError.class);

        assertEquals(HttpStatus.NOT_FOUND, error.getStatus());
        assertEquals(1, error.getErrors().size());
        assertTrue(error.getErrors().get(0).contains("No handler found"));
    }

    @Test
    public void whenHttpRequestMethodNotSupported_thenMethodNotAllowed() {
        Response response = givenAuth().delete(URL_PREFIX + "/api/foos/1");
        ApiError error = response.as(ApiError.class);

        assertEquals(HttpStatus.METHOD_NOT_ALLOWED, error.getStatus());
        assertEquals(1, error.getErrors().size());
        assertTrue(error.getErrors().get(0).contains("Supported methods are"));
    }



    @Test
    public void whenSendInvalidHttpMediaType_thenUnsupportedMediaType() {
        Response response = givenAuth().body("").post(URL_PREFIX + "/api/foos");
        ApiError error = response.as(ApiError.class);

        assertEquals(HttpStatus.UNSUPPORTED_MEDIA_TYPE, error.getStatus());
        assertEquals(1, error.getErrors().size());
        assertTrue(error.getErrors().get(0).contains("media type is not supported"));
    }


    @Test
    public void whenSendInvalidHttpMediaType_thenUnsupportedMediaType() {
        Response response = givenAuth().body("").post(URL_PREFIX + "/api/foos");
        ApiError error = response.as(ApiError.class);

        assertEquals(HttpStatus.UNSUPPORTED_MEDIA_TYPE, error.getStatus());
        assertEquals(1, error.getErrors().size());
        assertTrue(error.getErrors().get(0).contains("media type is not supported"));
    }

}
*/