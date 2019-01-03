package com.terrastation.sha.Enums;

import lombok.Getter;

@Getter
public enum ResultEnum {

    SUCCESS(0, "reussi"),

    PARAM_ERROR(1, "les parametres ne sont pas corrects"),

    ID_NOT_EXIST(2, "cet id existe pas "),

    Time_Ordre (3, "le Debut du temps doit etre plus tot ou autant que le Fin du temps"),

    QUANTITE_ERROR (4, "vous avez pas assez lignes de donnees en database "),

    TPYE_NOT_EXIST(5, "il n'existe pas ce type de sensor"),

    Heure_range (5, "l'heure doit  etre entre 0~24"),

    Mois_range (6, "le mois doit  etre entre 1~12"),

    Valeur_temperature (7, "le valeur de la temperature doit être > 0 "),

    Facon_Controler (8, "Votre interrupteur est controle par programmation"),

    Configuration_pulverisation (9, "Vous n'avez pas encore configuré la pulverisation"),

    Mode_pulverisation (10, "Vous n'avez pas ce mode en pulverisation"),

    Heure_existe_pas (11, "Vous n'avez pas configuré le pulverisateur pour cette heure"),
    ;

    private Integer code;

    private String message;

    ResultEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

}
