package com.example.newapp;

public class Patients {
    String p_name;
    int p_id;

    public String getName() {
        return p_name;
    }

    public int getId() {
        return p_id;
    }



    public Patients(String p_name, int p_id) {
        this.p_name = p_name;
        this.p_id = p_id;
    }


}
