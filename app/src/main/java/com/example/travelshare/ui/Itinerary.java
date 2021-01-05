package com.example.travelshare.ui;

import com.google.firebase.firestore.DocumentSnapshot;

public class Itinerary {
    private String image;
    private String tittle;
    private String location;
    private String user;
    private String topic;

    public Itinerary(){

    }

    public Itinerary(String image, String tittle, String location, String user, String topic) {
        this.image = image;
        this.tittle = tittle;
        this.location = location;
        this.user = user;
        this.topic = topic;
    }


    public String getImage() {
        return image;
    }

    public String getTittle() {
        return tittle;
    }

    public String getLocation() {
        return location;
    }

    public String getUser() {
        return user;
    }

    public String getTopic() {
        return topic;
    }

}
