package com.sumeyrasimsek.otel.Model;

public class User {
    private String Name;
    private String Password;
    private String id;
    private String imgUrl;

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public User(String name, String password, String id, String imgUrl) {
        Name = name;
        Password = password;
        this.id = id;
        this.imgUrl = imgUrl;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public User() {
    }



    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }
}
