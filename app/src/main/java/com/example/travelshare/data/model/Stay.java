package com.example.travelshare.data.model;

import com.google.firebase.storage.StorageReference;

import java.util.List;

public class Stay {

    private String id;
    private String name;
    private String location;
    private double priceNight;
    private String  extraInfo;
    private List<String> imagesLocal;

    public Stay(){

    }

    public Stay(String name, String location, double priceNight, String extraInfo){
        this.setName(name);
        this.setLocation(location);
        this.setPriceNight(priceNight);
        this.setExtraInfo(extraInfo);
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


    public List<String> getImagesLocal() {
        return imagesLocal;
    }

    public void setImagesLocal(List<String> imagesLocal) {
        this.imagesLocal = imagesLocal;
    }

    public void addImageLocal(String image){
        this.imagesLocal.add(image);
    }
    public void deleteImageLocal(String image){
        this.imagesLocal.remove(image);
    }

}
