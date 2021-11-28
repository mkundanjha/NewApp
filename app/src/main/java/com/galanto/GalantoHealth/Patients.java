package com.galanto.GalantoHealth;

public class Patients {
    String p_name;
    int p_id;
    int age;
    int p_weight;
    String gender;
    String handImp;



    public Patients(String p_name, int p_id, int age, int p_weight, String gender, String handImp) {
        this.p_name = p_name;
        this.p_id = p_id;
        this.age = age;
        this.p_weight = p_weight;
        this.gender = gender;
        this.handImp = handImp;
    }

    public int getP_weight() {
        return p_weight;
    }

    public int getP_id() {
        return p_id;
    }

    public String getHandImp() {
        return handImp;
    }

    public int getAge() {
        return age;
    }

    public String getGender() {
        return gender;
    }

    public String getName() {
        return p_name;
    }

    public int getId() {
        return p_id;
    }






}
