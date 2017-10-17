package com.pranaymohapatra.kiwi.model.location;

import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("com.robohorse.robopojogenerator")
public class LocationSuggestionsItem{

	@SerializedName("should_experiment_with")
	private int shouldExperimentWith;

	@SerializedName("has_new_ad_format")
	private int hasNewAdFormat;

	@SerializedName("is_state")
	private int isState;

	@SerializedName("state_name")
	private String stateName;

	@SerializedName("name")
	private String name;

	@SerializedName("country_name")
	private String countryName;

	@SerializedName("id")
	private int id;

	@SerializedName("state_id")
	private int stateId;

	@SerializedName("state_code")
	private String stateCode;

	@SerializedName("country_id")
	private int countryId;

	@SerializedName("discovery_enabled")
	private int discoveryEnabled;

	public void setShouldExperimentWith(int shouldExperimentWith){
		this.shouldExperimentWith = shouldExperimentWith;
	}

	public int getShouldExperimentWith(){
		return shouldExperimentWith;
	}

	public void setHasNewAdFormat(int hasNewAdFormat){
		this.hasNewAdFormat = hasNewAdFormat;
	}

	public int getHasNewAdFormat(){
		return hasNewAdFormat;
	}

	public void setIsState(int isState){
		this.isState = isState;
	}

	public int getIsState(){
		return isState;
	}

	public void setStateName(String stateName){
		this.stateName = stateName;
	}

	public String getStateName(){
		return stateName;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setCountryName(String countryName){
		this.countryName = countryName;
	}

	public String getCountryName(){
		return countryName;
	}

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	public void setStateId(int stateId){
		this.stateId = stateId;
	}

	public int getStateId(){
		return stateId;
	}

	public void setStateCode(String stateCode){
		this.stateCode = stateCode;
	}

	public String getStateCode(){
		return stateCode;
	}

	public void setCountryId(int countryId){
		this.countryId = countryId;
	}

	public int getCountryId(){
		return countryId;
	}

	public void setDiscoveryEnabled(int discoveryEnabled){
		this.discoveryEnabled = discoveryEnabled;
	}

	public int getDiscoveryEnabled(){
		return discoveryEnabled;
	}

	@Override
 	public String toString(){
		return 
			"LocationSuggestionsItem{" + 
			"should_experiment_with = '" + shouldExperimentWith + '\'' + 
			",has_new_ad_format = '" + hasNewAdFormat + '\'' + 
			",is_state = '" + isState + '\'' + 
			",state_name = '" + stateName + '\'' + 
			",name = '" + name + '\'' + 
			",country_name = '" + countryName + '\'' + 
			",id = '" + id + '\'' + 
			",state_id = '" + stateId + '\'' + 
			",state_code = '" + stateCode + '\'' + 
			",country_id = '" + countryId + '\'' + 
			",discovery_enabled = '" + discoveryEnabled + '\'' + 
			"}";
		}
}