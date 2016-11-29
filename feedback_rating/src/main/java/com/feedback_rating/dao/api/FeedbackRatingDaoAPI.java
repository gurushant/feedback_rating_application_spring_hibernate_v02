/**
 * 
 */
package com.feedback_rating.dao.api;

import org.hibernate.Session;

import com.feedback_rating.models.EmailNotification;
import com.feedback_rating.models.Order;
import com.feedback_rating.models.keys.EmailNotifyKey;
import com.feedback_rating.models.keys.OrderKey;

/**
 * @author gurushant.j
 *
 */
public interface FeedbackRatingDaoAPI {
	public boolean checkIsFeedbackReceived(EmailNotifyKey key);
	public EmailNotification getEmailDetail(EmailNotifyKey key);
	public boolean updateEmailNotification(EmailNotifyKey key,boolean isFeedbackReceived);
	public Order getOrderDetail(OrderKey key);
	public boolean updateOrderData(String feedback,float overallOrderRating,float overallRecipeRating,
			String jsonRatingData,OrderKey key);
	public Session getSession();

}
