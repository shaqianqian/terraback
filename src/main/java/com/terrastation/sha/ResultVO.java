package com.terrastation.sha;

import lombok.Data;

import java.io.Serializable;


@Data
public class ResultVO<T> implements Serializable {


    /**
     * code etat
     */
    private Integer code;

    /**
     * message.
     */
    private String msg;

    /**
     * les donnees.
     */
    private T data;
}
