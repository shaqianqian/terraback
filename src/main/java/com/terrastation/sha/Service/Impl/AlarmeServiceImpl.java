package com.terrastation.sha.Service.Impl;


import com.terrastation.sha.Repositary.ProfilRepository;
import com.terrastation.sha.Service.AlarmeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AlarmeServiceImpl implements AlarmeService {
    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private ProfilRepository profilRepository;


    public void send(){

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("terrastation111@gmail.com");
        message.setTo(profilRepository.findAll().get(0).getEmail());
        message.setSubject("coucou");
        message.setText("c'est un Email de terrastation, hahaha je suis en train de tester la systeme de l'alarme");
        mailSender.send(message);

    }

}
