/**
 * 
 */
package com.feedbackRating.feedback_service.api;

import com.feedbackRating.models.FeedbackResponse;
import com.feedbackRating.models.api.OrderResponseApi;
import com.feedbackRating.models.keys.EmailNotifyKey;
import com.feedbackRating.models.keys.OrderKey;

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
