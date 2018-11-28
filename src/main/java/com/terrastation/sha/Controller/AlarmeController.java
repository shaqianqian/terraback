package com.terrastation.sha.Controller;

import com.terrastation.sha.Util.ResultUtil;
import com.terrastation.sha.VO.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/alarme")
public class AlarmeController {

    @Autowired
    private JavaMailSender mailSender;


    @RequestMapping(value = "/sendEmail", method = RequestMethod.GET)

    public ResultVO sendEmail() {
     SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("terrastation111@gmail.com");
            message.setTo("451134922@qq.com");
            message.setSubject("coucou");
            message.setText("c'est un email de terrastation, hahaha je suis en train de tester la systeme de l'alarme");
            mailSender.send(message);
        return ResultUtil.success("fini");
    }




}
