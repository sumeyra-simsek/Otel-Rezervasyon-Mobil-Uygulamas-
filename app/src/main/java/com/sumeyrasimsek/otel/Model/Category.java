package com.sumeyrasimsek.otel.Model;

public class Category {
    private String Name;
    private String Image;
    private String id;
    private String Otel;
    private String Image2;
    private String Image3;
    private String Mail;
    private String Telefon;
    private String video;

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public Category() {
    }

    public Category(String name, String image, String id, String otel, String image2, String image3, String mail, String telefon, String video) {
        Name = name;
        Image = image;
        this.id = id;
        Otel = otel;
        Image2 = image2;
        Image3 = image3;
        Mail = mail;
        Telefon = telefon;
        this.video = video;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOtel() {
        return Otel;
    }

    public void setOtel(String otel) {
        Otel = otel;
    }

    public String getImage2() {
        return Image2;
    }

    public void setImage2(String image2) {
        Image2 = image2;
    }

    public String getImage3() {
        return Image3;
    }

    public void setImage3(String image3) {
        Image3 = image3;
    }

    public String getMail() {
        return Mail;
    }

    public void setMail(String mail) {
        Mail = mail;
    }

    public String getTelefon() {
        return Telefon;
    }

    public void setTelefon(String telefon) {
        Telefon = telefon;
    }
}
