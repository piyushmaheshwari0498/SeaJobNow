package com.example.seajobnow.model;

public class HomeNews {
    String tittle;
    int image;
    int Color;

    public HomeNews(String tittle, int image, int color) {
        this.tittle = tittle;
        this.image = image;
        Color = color;
    }

    public String getTittle() {
        return tittle;
    }

    public void setTittle(String tittle) {
        this.tittle = tittle;
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
                ", image=" + image +
                ", Color=" + Color +
                '}';
    }
}
