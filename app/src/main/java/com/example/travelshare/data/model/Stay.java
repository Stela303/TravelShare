package com.example.travelshare.data.model;

import java.util.List;

public class Stay {

    private String name;
    private String location;
    private double priceNight;
    private String  extraInfo;
    private List<String> images;

    public Stay(){

    }

    public Stay(String name, String location, double priceNight, String extraInfo, List<String> images){
        this.setName(name);
        this.setLocation(location);
        this.setPriceNight(priceNight);
        this.setExtraInfo(extraInfo);
        this.setImages(images);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }


    public String getExtraInfo() {
        return extraInfo;
    }

    public void setExtraInfo(String extraInfo) {
        this.extraInfo = extraInfo;
    }

    public double getPriceNight() {
        return priceNight;
    }

    public void setPriceNight(double price) {
        this.priceNight = price;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public void addImage(String image){
        this.images.add(image);
    }
    public void deleteImage(String image){
        this.images.remove(image);
    }
}
