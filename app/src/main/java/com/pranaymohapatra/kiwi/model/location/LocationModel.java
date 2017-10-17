package com.pranaymohapatra.kiwi.model.location;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import javax.annotation.Generated;

@Generated("com.robohorse.robopojogenerator")
public class LocationModel{

	@SerializedName("has_total")
	private int hasTotal;

	@SerializedName("location_suggestions")
	private List<LocationSuggestionsItem> locationSuggestions;

	@SerializedName("has_more")
	private int hasMore;

	@SerializedName("status")
	private String status;

	public void setHasTotal(int hasTotal){
		this.hasTotal = hasTotal;
	}

	public int getHasTotal(){
		return hasTotal;
	}

	public void setLocationSuggestions(List<LocationSuggestionsItem> locationSuggestions){
		this.locationSuggestions = locationSuggestions;
	}

	public List<LocationSuggestionsItem> getLocationSuggestions(){
		return locationSuggestions;
	}

	public void setHasMore(int hasMore){
		this.hasMore = hasMore;
	}

	public int getHasMore(){
		return hasMore;
	}

	public void setStatus(String status){
		this.status = status;
	}

	public String getStatus(){
		return status;
	}

	@Override
 	public String toString(){
		return 
			"LocationModel{" + 
			"has_total = '" + hasTotal + '\'' + 
			",location_suggestions = '" + locationSuggestions + '\'' + 
			",has_more = '" + hasMore + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}