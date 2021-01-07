package com.example.travelshare.data.model;

import com.google.firebase.storage.StorageReference;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Itinerary {

    private String id;
    private String name;
    private String location;
    private String topic;
    private String extraInfo;
    private Date date_created;
    private Date date_publishier;
    private String userName;
    private StorageReference coverPhoto;
    private List<FoodPlace> foodPlaces = new ArrayList<>();
    private List<InterestingPlace> interestingPlaces = new ArrayList<>();
    private List<Stay> stays = new ArrayList<>();


    public Itinerary() {

    }

    public Itinerary(String name, String location, String topic, String extraInfo) {
        this.setName(name);
        this.setLocation(location);
        this.setTopic(topic);
        this.setExtraInfo(extraInfo);
    }

    public String getId() {
        return id;
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

    public Date getDate_created() {
        return date_created;
    }

    public void setDate_created(Date date_created) {
        this.date_created = date_created;
    }


    public Date getDate_publishier() {
        return date_publishier;
    }

    public void setDate_publishier(Date date_publishier) {
        this.date_publishier = date_publishier;
    }

    public List<InterestingPlace> getInterestingPlaces() {
        return interestingPlaces;
    }

    public void setInterestingPlaces(List<InterestingPlace> interestingPlaces) {
        this.interestingPlaces = interestingPlaces;
    }

    public void addInterestingPlace(InterestingPlace interestingPlace) {
        this.interestingPlaces.add(interestingPlace);
    }

    public void removeInterestingPlace(InterestingPlace interestingPlace) {
        this.interestingPlaces.remove(interestingPlace);
    }


    public List<Stay> getStays() {
        return stays;
    }

    public void setStays(List<Stay> stays) {
        this.stays = stays;
    }

    public void addStay(Stay stay) {
        this.stays.add(stay);
    }

    public void removeStay(Stay stay) {
        this.stays.remove(stay);
    }

    public List<FoodPlace> getFoodPlaces() {
        return foodPlaces;
    }

    public void setFoodPlaces(List<FoodPlace> foodPlaces) {
        this.foodPlaces = foodPlaces;
    }

    public void addFoodPlace(FoodPlace foodPlace) {
        this.foodPlaces.add(foodPlace);
    }

    public void removeFoodPlace(FoodPlace foodPlace) {
        this.foodPlaces.remove(foodPlace);
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public StorageReference getCoverPhoto() {
        return coverPhoto;
    }

    public void setCoverPhoto(StorageReference coverPhoto) {
        this.coverPhoto = coverPhoto;
    }

    @NotNull
    @Override
    public String toString() {
        return name;
    }


}
