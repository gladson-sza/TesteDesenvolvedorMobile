package br.com.cybersociety.testedesenvolvedormobile.model.entities;

public class User {

    private String name;
    private byte[] photo;

    public User() {
    }

    public User(String name, byte[] photo) {
        this.name = name;
        this.photo = photo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }
}
