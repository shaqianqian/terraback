package com.terrastation.sha.Enums;

import lombok.Getter;

@Getter
public enum ResultEnum {

    SUCCESS(0, "reussi"),

    PARAM_ERROR(1, "les parametres ne sont pas correctes"),

    ID_NOT_EXIST(2, "cet id existe pas "),

    Time_Ordre (3, "le dateDebut doit etre plus tot ou autant que le dateFin "),;

    private Integer code;

    private String message;

    ResultEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

}
