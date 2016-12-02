/**
 * 
 */
package com.feedbackRating.persistence.dao.api;

import org.hibernate.Session;

import com.feedbackRating.persistence.models.Order;


/**
 * @author gurushant.j
 *
 */
public interface FeedbackRatingDaoAPI {
	public boolean isOrderExists(int orderId,int restId);
	public boolean checkIsFeedbackReceived(int orderId,int restId);
	public boolean updateEmailNotification(int orderLineId ,boolean isFeedbackReceived);
	public Order getOrderDetail(int key);
	public boolean updateOrderData(String feedback,float overallOrderRating,float overallRecipeRating,
			String jsonRatingData,int orderLineId);
	public Session getSession();
	public int getOrderLineId(int orderId,int restId);

}
