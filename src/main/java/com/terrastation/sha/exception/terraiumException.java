package com.terrastation.sha.exception;

import com.terrastation.sha.enums.ResultEnum;
import lombok.Getter;

@Getter
public class terraiumException extends RuntimeException {

    private Integer code;
   //resultEnum les codes et informations d'erreurs pour retourner au front
    public terraiumException(ResultEnum resultEnum) {
        super(resultEnum.getMessage());

        this.code = resultEnum.getCode();
    }

}
