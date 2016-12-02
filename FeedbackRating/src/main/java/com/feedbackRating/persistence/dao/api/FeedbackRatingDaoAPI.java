/**
 * 
 */
package com.feedbackRating.persistence.dao.api;

import org.hibernate.Session;

import com.feedbackRating.persistence.models.EmailNotification;
import com.feedbackRating.persistence.models.Order;
import com.feedbackRating.persistence.models.keys.OrderKey;

/**
 * @author gurushant.j
 *
 */
public interface FeedbackRatingDaoAPI {
	public boolean checkIsFeedbackReceived(OrderKey key);
	public boolean updateEmailNotification(int orderLineId ,boolean isFeedbackReceived);
	public Order getOrderDetail(int key);
	public boolean updateOrderData(String feedback,float overallOrderRating,float overallRecipeRating,
			String jsonRatingData,int orderLineId);
	public Session getSession();
	public int getOrderLineId(OrderKey key);

}
