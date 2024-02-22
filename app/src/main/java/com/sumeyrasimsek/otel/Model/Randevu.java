package com.sumeyrasimsek.otel.Model;

public class Randevu {
    private String gidis;
    private String donus;
    private String oda;
    private String id;
    private String Name;
    private String otel;

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getOtel() {
        return otel;
    }

    public void setOtel(String otel) {
        this.otel = otel;
    }

    public Randevu(String gidis, String donus, String oda, String id, String name, String otel) {
        this.gidis = gidis;
        this.donus = donus;
        this.oda = oda;
        this.id = id;
        Name = name;
        this.otel = otel;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getGidis() {
        return gidis;
    }

    public void setGidis(String gidis) {
        this.gidis = gidis;
    }

    public String getDonus() {
        return donus;
    }

    public void setDonus(String donus) {
        this.donus = donus;
    }

    public String getOda() {
        return oda;
    }

    public void setOda(String oda) {
        this.oda = oda;
    }

    public Randevu() {
    }
}
