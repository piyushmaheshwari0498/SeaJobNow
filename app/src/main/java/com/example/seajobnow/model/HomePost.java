package com.example.seajobnow.model;

public class HomePost {
    String tittle;
    String Count;
    int image;
    int Color;

    public HomePost(String tittle, String count, int image, int color) {
        this.tittle = tittle;
        Count = count;
        this.image = image;
        Color = color;
    }

    public String getTittle() {
        return tittle;
    }

    public void setTittle(String tittle) {
        this.tittle = tittle;
    }

    public String getCount() {
        return Count;
    }

    public void setCount(String count) {
        Count = count;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public int getColor() {
        return Color;
    }

    public void setColor(int color) {
        Color = color;
    }

    @Override
    public String toString() {
        return "HomePost{" +
                "tittle='" + tittle + '\'' +
                ", Count='" + Count + '\'' +
                ", image=" + image +
                ", Color=" + Color +
                '}';
    }
}
