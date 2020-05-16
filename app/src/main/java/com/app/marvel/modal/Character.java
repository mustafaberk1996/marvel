package com.app.marvel.modal;

import java.io.Serializable;

public class Character implements Serializable {
    String photo_url;
    String name;
    String id;

    public Character(String photo_url, String name, String id) {
        this.photo_url = photo_url;
        this.name = name;
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPhoto_url() {
        return photo_url;
    }

    public void setPhoto_url(String photo_url) {
        this.photo_url = photo_url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
