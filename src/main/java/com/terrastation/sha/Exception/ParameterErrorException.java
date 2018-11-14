package com.terrastation.sha.Exception;


import com.terrastation.sha.Enums.ResultEnum;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.NOT_FOUND)
public class ParameterErrorException extends RuntimeException {
    private Integer code;
    //resultEnum les codes et informations d'erreurs pour retourner au front
    public ParameterErrorException(ResultEnum resultEnum) {
        super(resultEnum.getMessage());

        this.code = resultEnum.getCode();
    }
}