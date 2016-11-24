/**
 * 
 */
package com.feedback_rating.entity.order.models;

import java.util.Date;

import javax.transaction.Transactional;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.feedback_rating.entity.email_notification.models.EmailNotification;


/**
 * @author gurushant.j
 *
 */

@Repository
public class OrderDao {

	@Autowired
	private SessionFactory _sessionFactory;
	
	private Session getSession()
	{
		return _sessionFactory.getCurrentSession();
	}
	
	public Order getOrderDetail(OrderKey key)
	{
		return (Order) getSession().load(Order.class, key);
	}
	
	public void updateOrderData(String feedback,float overallOrderRating,float overallRecipeRating,
			String jsonRatingData,OrderKey key)
	{
		Session session=getSession();
	//	session.beginTransaction();
		Order orderObj=(Order)session.load(Order.class, key);
		orderObj.setFeedback(feedback);
		orderObj.setOrderRating(overallOrderRating);
		orderObj.setRecipeRating(overallRecipeRating);
		orderObj.setRatingFeedback(jsonRatingData);
		session.saveOrUpdate(orderObj);

	}
}
