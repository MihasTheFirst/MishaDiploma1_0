package com.example.mishadiploma1_0.entity;


import lombok.Getter;

@Getter
public enum Measure {
    KG("Кілограм"),
    G("Грам"),
    PCS("Одиниця"),
    L("Літр"),
    ML("Мілілітр");

    private String measure;
    Measure(String measure) { }
}

