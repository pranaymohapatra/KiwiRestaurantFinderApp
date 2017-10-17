package com.pranaymohapatra.kiwi.model;


public class FavouriteRestaurantModel {

    private Integer ID;
    private String rName;
    private String rCuisines;
    private String averageCost;
    private String averageRating;
    private String ratingColor;
    private String rURL;
    private String imageURL;
    private String locality;


    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }


    public String getCuisines() {
        return rCuisines;
    }

    public void setCuisines(String rCuisines) {
        this.rCuisines = rCuisines;
    }

    public String getAverageCost() {
        return averageCost;
    }

    public void setAverageCost(String averageCost) {
        this.averageCost = averageCost;
    }

    public String getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(String averageRating) {
        this.averageRating = averageRating;
    }

    public String getRatingColor() {
        return ratingColor;
    }

    public void setRatingColor(String ratingColor) {
        this.ratingColor = ratingColor;
    }

    public String getURL() {
        return rURL;
    }

    public void setURL(String rURL) {
        this.rURL = rURL;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getLocality() {
        return locality;
    }

    public void setLocality(String locality) {
        this.locality = locality;
    }

    public String getName() {
        return rName;
    }

    public void setName(String rName) {
        this.rName = rName;
    }
}
