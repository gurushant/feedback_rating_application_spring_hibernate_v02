package com.feedback_rating.entity.order.service;

import com.feedback_rating.entity.email_notification.utils.EmailNotifyKey;
import com.feedback_rating.entity.order.models.Order;
import com.feedback_rating.entity.order.models.OrderKey;

public interface OrderServiceApi {

	public Object getOrderDetails(int orderId,int restId);
	public boolean updateOrderData(String feedback,float overallOrderRating,float overallRecipeRating,
			String jsonRatingData,OrderKey key);
	public boolean isFeedbackExists(EmailNotifyKey emailKey);
	public Order returnOrder(OrderKey key);

}
