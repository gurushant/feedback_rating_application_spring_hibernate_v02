/**
 * 
 */
package com.feedback_rating.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author gurushant.j
 *
 */
@Entity
@Table(name="orders")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Order {
	@EmbeddedId
	private OrderKey key;
	@Column(name="served_date_time")
	private Date servedDateTime;
	@Column(name="order_amount")	
	private int orderAmount;
	@Column(name="customer_email")	
	private String custEmail;
	@Column(name="customer_mobile")	
	private String custMobile;
	@Column(name="recipes_in_orders")	
	private String recipeJson;
	@Column(name="rating_feedback_data")	
	private String ratingFeedback;
	@Column(name="created_on")	
	private Date createdOn;
	@Column(name="overall_recipe_rating")	
	private int recipeRating;
	@Column(name="overall_order_rating")	
	private int orderRating;
	@Column(name="feedback")	
	private String feedback;
	public Order()
	{
		
	}
	public Order(OrderKey key)
	{
		this.key=key;
	}
	public OrderKey getKey() {
		return key;
	}
	public void setKey(OrderKey key) {
		this.key = key;
	}
	public Date getServedDateTime() {
		return servedDateTime;
	}
	public void setServedDateTime(Date servedDateTime) {
		this.servedDateTime = servedDateTime;
	}
	public int getOrderAmount() {
		return orderAmount;
	}
	public void setOrderAmount(int orderAmount) {
		this.orderAmount = orderAmount;
	}
	public String getCustEmail() {
		return custEmail;
	}
	public void setCustEmail(String custEmail) {
		this.custEmail = custEmail;
	}
	public String getCustMobile() {
		return custMobile;
	}
	public void setCustMobile(String custMobile) {
		this.custMobile = custMobile;
	}
	public String getRecipeJson() {
		return recipeJson;
	}
	public void setRecipeJson(String recipeJson) {
		this.recipeJson = recipeJson;
	}
	public String getRatingFeedback() {
		return ratingFeedback;
	}
	public void setRatingFeedback(String ratingFeedback) {
		this.ratingFeedback = ratingFeedback;
	}
	public Date getCreatedOn() {
		return createdOn;
	}
	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}
	public int getRecipeRating() {
		return recipeRating;
	}
	public void setRecipeRating(int recipeRating) {
		this.recipeRating = recipeRating;
	}
	public int getOrderRating() {
		return orderRating;
	}
	public void setOrderRating(int orderRating) {
		this.orderRating = orderRating;
	}
	public String getFeedback() {
		return feedback;
	}
	public void setFeedback(String feedback) {
		this.feedback = feedback;
	}
	
	
}
