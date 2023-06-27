package ru.sber.model;
/**
 * Торт
 */
public class Cake {
    private String cake_name;
    private String info;
    private String image;
    private String tag;
    public Cake(String cake_name, String info, String image, String tag) {
        this.cake_name = cake_name;
        this.info = info;
        this.image = image;
        this.tag = tag;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCake_name() {
        return cake_name;
    }

    public void setCake_name(String cake_name) {
        this.cake_name = cake_name;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}
