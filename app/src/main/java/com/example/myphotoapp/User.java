package com.example.myphotoapp;

import java.util.Arrays;

public class User {
    String title;
    String artist;
    byte[] img;

    public User(String tt, String art, byte[] mimg){
        this.title = tt;
        this.artist = art;
        this.img = mimg;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }


    public byte[] getImg() {
        return img;
    }

    public void setImg(byte[] img) {
        this.img = img;
    }


    @Override
    public String toString() {
        return "User{" +
                "title='" + title + '\'' +
                ", artist='" + artist + '\'' +
                ", img=" + Arrays.toString(img) +
                '}';
    }
}
