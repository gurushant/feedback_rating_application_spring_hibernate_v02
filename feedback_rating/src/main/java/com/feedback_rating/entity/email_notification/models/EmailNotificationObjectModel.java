/**
 * 
 */
package com.feedback_rating.entity.email_notification.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.annotations.SerializedName;

/**
 * @author gurushant.j
 *
 */
public class EmailNotificationObjectModel {
	@SerializedName("order_id")
	private Integer orderId;
	@SerializedName("restaruent_id")
	private Integer restId;
	@SerializedName("economy")
	private Float economyRate;
	@SerializedName("ambience")
	private Float ambienceRate;
	@SerializedName("qos")
	private Float qosRate;
	@SerializedName("feedback")
	private String feedbackTxt;
	@SerializedName("recipe_rating")
	private List<Object> recipeList=new ArrayList<Object>();
	public Integer getOrderId() {
		return orderId;
	}
	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}
	public Integer getRestId() {
		return restId;
	}
	public void setRestId(Integer restId) {
		this.restId = restId;
	}
	public Float getEconomyRate() {
		return economyRate;
	}
	public void setEconomyRate(Float economyRate) {
		this.economyRate = economyRate;
	}
	public Float getAmbienceRate() {
		return ambienceRate;
	}
	public void setAmbienceRate(Float ambienceRate) {
		this.ambienceRate = ambienceRate;
	}
	public Float getQosRate() {
		return qosRate;
	}
	public void setQosRate(Float qosRate) {
		this.qosRate = qosRate;
	}
	public String getFeedbackTxt() {
		return feedbackTxt;
	}
	public void setFeedbackTxt(String feedbackTxt) {
		this.feedbackTxt = feedbackTxt;
	}
	public List<Object> getRecipeList() {
		return recipeList;
	}
	public void setRecipeList(List<Object> recipeList) {
		this.recipeList = recipeList;
	}	
	
}

