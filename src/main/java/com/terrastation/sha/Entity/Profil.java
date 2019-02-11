package com.terrastation.sha.Entity;

import com.terrastation.sha.Enums.ResultEnum;
import com.terrastation.sha.Exception.ParameterErrorException;
import lombok.Data;

import javax.persistence.*;
import java.util.regex.Pattern;

@Data
@Entity
public class Profil {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        if(isEmail(email))
        {this.email = email;}
         else{
            throw new ParameterErrorException(ResultEnum.Email_pattern);
        }
    }

    /**
     * 正则表达式：验证邮箱
     */
    public static final String REGEX_EMAIL = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
    public static boolean isEmail(String email) {
        return Pattern.matches(REGEX_EMAIL, email);
    }

}