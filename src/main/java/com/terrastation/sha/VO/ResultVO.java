package com.terrastation.sha.VO;

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

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
