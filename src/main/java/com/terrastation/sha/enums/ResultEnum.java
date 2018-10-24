package com.terrastation.sha.enums;

import lombok.Getter;

@Getter
public enum ResultEnum {

    SUCCESS(0, "reussi"),

    PARAM_ERROR(1, "les parametres ne sont pas correctes"),

    ID_NOT_EXIST(2, "cet id existe pas "),;

    private Integer code;

    private String message;

    ResultEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

}
