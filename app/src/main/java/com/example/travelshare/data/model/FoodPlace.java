package com.example.travelshare.data.model;

import com.google.firebase.storage.StorageReference;
import com.google.type.DateTime;

import org.jetbrains.annotations.NotNull;

import java.net.URL;
import java.util.List;
import java.util.function.DoubleBinaryOperator;

public class FoodPlace {

    private String name;
    private String location;
    private String type;
    private String category;
    private double averagePrice;
    private String  extraInfo;
    private List<String> imagesLocal;
    private List<StorageReference> images;



    public FoodPlace(){

    }

    public FoodPlace(String name, String location, String type, String  extraInfo, String category, double averagePrice){
        this.setName(name);
        this.setLocation(location);
        this.setType(type);
        this.setExtraInfo(extraInfo);
        this.setAveragePrice(averagePrice);
        this.setCategory(category);
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getAveragePrice() {
        return averagePrice;
    }

    public void setAveragePrice(double averagePrice) {
        this.averagePrice = averagePrice;
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

    @NotNull
    @Override
    public String toString() {
        return name;
    }
}
