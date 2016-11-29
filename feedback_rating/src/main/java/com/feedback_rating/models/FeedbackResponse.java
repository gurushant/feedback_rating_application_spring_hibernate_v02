/**
 * 
 */
package com.feedback_rating.models;

/**
 * @author gurushant.j
 * This is the response object of the post feedback and rating response.
 */
public class FeedbackResponse {
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
