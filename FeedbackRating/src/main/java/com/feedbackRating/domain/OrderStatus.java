package com.feedbackRating.domain;

import com.feedbackRating.persistence.models.api.OrderResponseApi;

/**
 * Order response in case of error,row now found,feedback already exists cases.
 * @author gurushant.j
 *
 */
public class OrderStatus implements OrderResponseApi {
	private String status;
	private String message;
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
}