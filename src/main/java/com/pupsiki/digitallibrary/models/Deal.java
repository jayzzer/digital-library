package com.pupsiki.digitallibrary.models;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "Deal")
public class Deal {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String date_of_deal;
    private String user;
    private String book;


    public Deal() {
    }


}
