package com.example.travelshare.data.model;

import com.google.firebase.storage.StorageReference;

import java.util.List;

public class InterestingPlace {

    private String id;
    private String name;
    private String location;
    private String topic;
    private String extraInfo;
    private double price;
    private List<String> imagesLocal;
    private List<StorageReference> images;

    public InterestingPlace(){

    }

    public InterestingPlace(String name, String location, double price, String extraInfo, List<String> imagesLocal){
        this.setName(name);
        this.setLocation(location);
        this.setPrice(price);
        this.setExtraInfo(extraInfo);
        this.setImagesLocal(imagesLocal);
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

    public List<StorageReference> getImages() {
        return images;
    }

    public void setImages(List<StorageReference> images) {
        this.images = images;
    }

    public void addImage(StorageReference image){
        this.images.add(image);
    }
    public void deleteImage(StorageReference image){
        this.images.remove(image);
    }
}
