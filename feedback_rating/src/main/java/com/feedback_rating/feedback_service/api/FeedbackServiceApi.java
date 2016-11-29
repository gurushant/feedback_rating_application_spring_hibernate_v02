/**
 * 
 */
package com.feedback_rating.feedback_service.api;

import com.feedback_rating.models.FeedbackResponse;
import com.feedback_rating.models.api.OrderResponseApi;
import com.feedback_rating.models.keys.EmailNotifyKey;
import com.feedback_rating.models.keys.OrderKey;

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
