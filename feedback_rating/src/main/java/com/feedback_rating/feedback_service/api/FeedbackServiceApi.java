/**
 * 
 */
package com.feedback_rating.feedback_service.api;

import com.feedback_rating.domain.FeedbackResponseModel;
import com.feedback_rating.models.keys.EmailNotifyKey;
import com.feedback_rating.models.keys.OrderKey;

/**
 * @author gurushant.j
 *
 */
public interface FeedbackServiceApi {
	public FeedbackResponseModel postFeedback(String postPayload);
	public boolean isFeedbackExists(EmailNotifyKey emailKey);
	public Object getOrderDetails(int orderId,int restId);
	public boolean updateOrderData(String feedback,float overallOrderRating,float overallRecipeRating,
			String jsonRatingData,OrderKey key);

}
