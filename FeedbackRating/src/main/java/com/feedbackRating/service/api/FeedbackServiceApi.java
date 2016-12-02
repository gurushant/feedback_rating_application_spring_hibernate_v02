/**
 * 
 */
package com.feedbackRating.service.api;

import com.feedbackRating.domain.FeedbackResponse;
import com.feedbackRating.persistence.models.api.OrderResponseApi;

/**
 * @author gurushant.j
 *
 */
public interface FeedbackServiceApi {
	public FeedbackResponse postFeedback(String postPayload);
	public boolean isFeedbackExists(int orderId,int restId);
	public OrderResponseApi getOrderDetails(int orderId,int restId);

}
