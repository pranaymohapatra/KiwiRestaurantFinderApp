package com.pranaymohapatra.kiwi.model.restaurants;

import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("com.robohorse.robopojogenerator")
public class RestaurantsItem{

	@SerializedName("restaurant")
	private Restaurant restaurant;

	private int isFavourite;

	public void setRestaurant(Restaurant restaurant){
		this.restaurant = restaurant;
	}

	public Restaurant getRestaurant(){
		return restaurant;
	}

	public void setIsFavourite(int i){
		isFavourite = i;
	}

	public int getIsFavourite(){
		return isFavourite;
	}

	@Override
 	public String toString(){
		return 
			"RestaurantsItem{" + 
			"restaurant = '" + restaurant + '\'' + 
			"}";
		}
}