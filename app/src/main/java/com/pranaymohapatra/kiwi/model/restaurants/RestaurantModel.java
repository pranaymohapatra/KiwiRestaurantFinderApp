package com.pranaymohapatra.kiwi.model.restaurants;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import javax.annotation.Generated;

@Generated("com.robohorse.robopojogenerator")
public class RestaurantModel{

	@SerializedName("results_found")
	private int resultsFound;

	@SerializedName("results_shown")
	private int resultsShown;

	@SerializedName("restaurants")
	private List<RestaurantsItem> restaurants;

	@SerializedName("results_start")
	private int resultsStart;

	public void setResultsFound(int resultsFound){
		this.resultsFound = resultsFound;
	}

	public int getResultsFound(){
		return resultsFound;
	}

	public void setResultsShown(int resultsShown){
		this.resultsShown = resultsShown;
	}

	public int getResultsShown(){
		return resultsShown;
	}

	public void setRestaurants(List<RestaurantsItem> restaurants){
		this.restaurants = restaurants;
	}

	public List<RestaurantsItem> getRestaurants(){
		return restaurants;
	}

	public void setResultsStart(int resultsStart){
		this.resultsStart = resultsStart;
	}

	public int getResultsStart(){
		return resultsStart;
	}

	@Override
 	public String toString(){
		return 
			"RestaurantModel{" + 
			"results_found = '" + resultsFound + '\'' + 
			",results_shown = '" + resultsShown + '\'' + 
			",restaurants = '" + restaurants + '\'' + 
			",results_start = '" + resultsStart + '\'' + 
			"}";
		}
}