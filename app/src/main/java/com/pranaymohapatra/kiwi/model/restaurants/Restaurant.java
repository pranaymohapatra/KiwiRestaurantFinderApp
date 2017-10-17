package com.pranaymohapatra.kiwi.model.restaurants;

import com.google.gson.annotations.SerializedName;
import com.pranaymohapatra.kiwi.model.location.Location;

import java.util.List;

import javax.annotation.Generated;

@Generated("com.robohorse.robopojogenerator")
public class Restaurant{

	@SerializedName("offers")
	private List<Object> offers;

	@SerializedName("has_online_delivery")
	private int hasOnlineDelivery;

	@SerializedName("establishment_types")
	private List<Object> establishmentTypes;

	@SerializedName("apikey")
	private String apikey;

	@SerializedName("has_table_booking")
	private int hasTableBooking;

	@SerializedName("thumb")
	private String thumb;

	@SerializedName("average_cost_for_two")
	private int averageCostForTwo;

	@SerializedName("menu_url")
	private String menuUrl;

	@SerializedName("is_delivering_now")
	private int isDeliveringNow;

	@SerializedName("deeplink")
	private String deeplink;

	@SerializedName("price_range")
	private int priceRange;

	@SerializedName("switch_to_order_menu")
	private int switchToOrderMenu;

	@SerializedName("featured_image")
	private String featuredImage;

	@SerializedName("url")
	private String url;

	@SerializedName("cuisines")
	private String cuisines;

	@SerializedName("R")
	private R R;

	@SerializedName("events_url")
	private String eventsUrl;

	@SerializedName("name")
	private String name;

	@SerializedName("location")
	private Location location;

	@SerializedName("currency")
	private String currency;

	@SerializedName("id")
	private String id;

	@SerializedName("photos_url")
	private String photosUrl;

	@SerializedName("user_rating")
	private UserRating userRating;

	public void setOffers(List<Object> offers){
		this.offers = offers;
	}

	public List<Object> getOffers(){
		return offers;
	}

	public void setHasOnlineDelivery(int hasOnlineDelivery){
		this.hasOnlineDelivery = hasOnlineDelivery;
	}

	public int getHasOnlineDelivery(){
		return hasOnlineDelivery;
	}

	public void setEstablishmentTypes(List<Object> establishmentTypes){
		this.establishmentTypes = establishmentTypes;
	}

	public List<Object> getEstablishmentTypes(){
		return establishmentTypes;
	}

	public void setApikey(String apikey){
		this.apikey = apikey;
	}

	public String getApikey(){
		return apikey;
	}

	public void setHasTableBooking(int hasTableBooking){
		this.hasTableBooking = hasTableBooking;
	}

	public int getHasTableBooking(){
		return hasTableBooking;
	}

	public void setThumb(String thumb){
		this.thumb = thumb;
	}

	public String getThumb(){
		return thumb;
	}

	public void setAverageCostForTwo(int averageCostForTwo){
		this.averageCostForTwo = averageCostForTwo;
	}

	public int getAverageCostForTwo(){
		return averageCostForTwo;
	}

	public void setMenuUrl(String menuUrl){
		this.menuUrl = menuUrl;
	}

	public String getMenuUrl(){
		return menuUrl;
	}

	public void setIsDeliveringNow(int isDeliveringNow){
		this.isDeliveringNow = isDeliveringNow;
	}

	public int getIsDeliveringNow(){
		return isDeliveringNow;
	}

	public void setDeeplink(String deeplink){
		this.deeplink = deeplink;
	}

	public String getDeeplink(){
		return deeplink;
	}

	public void setPriceRange(int priceRange){
		this.priceRange = priceRange;
	}

	public int getPriceRange(){
		return priceRange;
	}

	public void setSwitchToOrderMenu(int switchToOrderMenu){
		this.switchToOrderMenu = switchToOrderMenu;
	}

	public int getSwitchToOrderMenu(){
		return switchToOrderMenu;
	}

	public void setFeaturedImage(String featuredImage){
		this.featuredImage = featuredImage;
	}

	public String getFeaturedImage(){
		return featuredImage;
	}

	public void setUrl(String url){
		this.url = url;
	}

	public String getUrl(){
		return url;
	}

	public void setCuisines(String cuisines){
		this.cuisines = cuisines;
	}

	public String getCuisines(){
		return cuisines;
	}

	public void setR(R R){
		this.R = R;
	}

	public R getR(){
		return R;
	}

	public void setEventsUrl(String eventsUrl){
		this.eventsUrl = eventsUrl;
	}

	public String getEventsUrl(){
		return eventsUrl;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setLocation(Location location){
		this.location = location;
	}

	public Location getLocation(){
		return location;
	}

	public void setCurrency(String currency){
		this.currency = currency;
	}

	public String getCurrency(){
		return currency;
	}

	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return id;
	}

	public void setPhotosUrl(String photosUrl){
		this.photosUrl = photosUrl;
	}

	public String getPhotosUrl(){
		return photosUrl;
	}

	public void setUserRating(UserRating userRating){
		this.userRating = userRating;
	}

	public UserRating getUserRating(){
		return userRating;
	}

	@Override
 	public String toString(){
		return 
			"Restaurant{" + 
			"offers = '" + offers + '\'' + 
			",has_online_delivery = '" + hasOnlineDelivery + '\'' + 
			",establishment_types = '" + establishmentTypes + '\'' + 
			",apikey = '" + apikey + '\'' + 
			",has_table_booking = '" + hasTableBooking + '\'' + 
			",thumb = '" + thumb + '\'' + 
			",average_cost_for_two = '" + averageCostForTwo + '\'' + 
			",menu_url = '" + menuUrl + '\'' + 
			",is_delivering_now = '" + isDeliveringNow + '\'' + 
			",deeplink = '" + deeplink + '\'' + 
			",price_range = '" + priceRange + '\'' + 
			",switch_to_order_menu = '" + switchToOrderMenu + '\'' + 
			",featured_image = '" + featuredImage + '\'' + 
			",url = '" + url + '\'' + 
			",cuisines = '" + cuisines + '\'' + 
			",R = '" + R + '\'' + 
			",events_url = '" + eventsUrl + '\'' + 
			",name = '" + name + '\'' + 
			",location = '" + location + '\'' + 
			",currency = '" + currency + '\'' + 
			",id = '" + id + '\'' + 
			",photos_url = '" + photosUrl + '\'' + 
			",user_rating = '" + userRating + '\'' + 
			"}";
		}
}