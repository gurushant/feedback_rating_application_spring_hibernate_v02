/**
 * 
 */
package com.feedbackRating.service.api;

import com.feedbackRating.domain.FeedbackResponse;
import com.feedbackRating.persistence.models.api.OrderResponseApi;
import com.feedbackRating.persistence.models.keys.EmailNotifyKey;
import com.feedbackRating.persistence.models.keys.OrderKey;

/**
 * @author gurushant.j
 *
 */
public interface FeedbackServiceApi {
	public FeedbackResponse postFeedback(String postPayload);
	public boolean isFeedbackExists(EmailNotifyKey emailKey);
	public OrderResponseApi getOrderDetails(int orderId,int restId);
	public boolean updateOrderData(String feedback,float overallOrderRating,float overallRecipeRating,
			String jsonRatingData,OrderKey key);

}
