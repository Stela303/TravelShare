package com.example.travelshare.ui;

public class Itinerary {
    private String image;
    private String tittle;
    private String location;
    private String user;
    private Double rating;
    private String thematic;

    public Itinerary(){

    }

    public Itinerary(String image, String tittle, String location, String user, Double rating, String thematic) {
        this.image = image;
        this.tittle = tittle;
        this.location = location;
        this.user = user;
        this.rating = rating;
        this.thematic = thematic;
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

    public Double getRating() {
        return rating;
    }

    public String getThematic() {
        return thematic;
    }
}
