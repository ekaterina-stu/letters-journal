package com.estu.letterjournal.dto;


import com.opencsv.bean.CsvBindByName;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

public class Entry {

    @CsvBindByName
    private String number;


    @CsvBindByName
    @Pattern(regexp = "^(0[1-9]|[12][0-9]|3[01])[- /.](0[1-9]|1[012])[- /.](19|20)\\d\\d$", message = "Введите дату в формате дд-мм-гггг")
    private String date;


    @CsvBindByName
    @NotEmpty(message = "Поле Организация должно быть заполнено")
    private String organization;


    @CsvBindByName
    @NotEmpty(message = "Поле Тема должно быть заполнено")
    private String theme;


    @CsvBindByName
    @NotEmpty(message = "Поле Исполнитель должно быть заполнено")
    private String executor;

    @CsvBindByName
    private String user;

    public Entry(String number, String date, String organization, String theme, String executor, String user) {
        this.number = number;
        this.date = date;
        this.organization = organization;
        this.theme = theme;
        this.executor = executor;
        this.user = user;
    }

    public Entry() {
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public String getExecutor() {
        return executor;
    }

    public void setExecutor(String executor) {
        this.executor = executor;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
}
