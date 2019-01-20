package com.terrastation.sha.Exception;

import com.terrastation.sha.Enums.ResultEnum;
import lombok.Getter;

@Getter
public class TerraiumException extends RuntimeException {

    private Integer code;
   //resultEnum les codes et informations d'erreurs pour retourner au front
    public TerraiumException(ResultEnum resultEnum) {
        super(resultEnum.getMessage());

        this.code = resultEnum.getCode();
    }

}
