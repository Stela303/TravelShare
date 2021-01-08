package com.example.travelshare.data.model;

import com.google.firebase.storage.StorageReference;

import java.util.List;

public class InterestingPlace {

    private String name;
    private String location;
    private String topic;
    private String extraInfo;
    private double price;
    private List<String> images;

    public InterestingPlace(){

    }

    public InterestingPlace(String name, String location, double price, String extraInfo, List<String> images){
        this.setName(name);
        this.setLocation(location);
        this.setPrice(price);
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

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getExtraInfo() {
        return extraInfo;
    }

    public void setExtraInfo(String extraInfo) {
        this.extraInfo = extraInfo;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
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
