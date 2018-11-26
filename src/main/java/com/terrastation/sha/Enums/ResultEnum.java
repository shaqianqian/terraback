package com.terrastation.sha.Enums;

import lombok.Getter;

@Getter
public enum ResultEnum {

    SUCCESS(0, "reussi"),

    PARAM_ERROR(1, "les parametres ne sont pas correctes"),

    ID_NOT_EXIST(2, "cet id existe pas "),

    Time_Ordre (3, "le Debut du temps doit etre plus tot ou autant que le Fin du temps"),

    QUANTITE_ERROR (4, "vous avez pas assez lignes de donnees en database "),

    TPYE_NOT_EXIST(5, "il n'existe pas cette type de sensor"),;

    private Integer code;

    private String message;

    ResultEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

}
