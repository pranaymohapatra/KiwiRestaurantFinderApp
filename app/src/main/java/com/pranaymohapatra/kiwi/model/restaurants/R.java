package com.pranaymohapatra.kiwi.model.restaurants;

import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("com.robohorse.robopojogenerator")
public class R{

	@SerializedName("res_id")
	private int resId;

	public void setResId(int resId){
		this.resId = resId;
	}

	public int getResId(){
		return resId;
	}

	@Override
 	public String toString(){
		return 
			"R{" + 
			"res_id = '" + resId + '\'' + 
			"}";
		}
}