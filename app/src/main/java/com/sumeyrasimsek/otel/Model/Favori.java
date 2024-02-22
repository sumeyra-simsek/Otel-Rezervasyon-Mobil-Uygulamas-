package com.sumeyrasimsek.otel.Model;

public class Favori {
    private String Image;
    private String Name;
    private String Id;

    public Favori() {
    }

    public Favori(String image, String name, String id) {
        Image = image;
        Name = name;
        Id = id;
    }

    public String getImage() {
        return Image;
    }

    public String getName() {
        return Name;
    }

    public String getId() {
        return Id;
    }
}
