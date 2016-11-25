/**
 * 
 */
package com.feedback_rating.entity.order.models;

import java.util.Date;

import javax.transaction.Transactional;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.annotations.Proxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.feedback_rating.entity.email_notification.models.EmailNotification;
import com.feedback_rating.entity.email_notification.models.EmailNotificationDao;
import com.feedback_rating.entity.email_notification.utils.CommonUtils;


/**
 * @author gurushant.j
 *
 */

@Repository
public class OrderDao {

	private static final Logger log = LoggerFactory.getLogger(OrderDao.class);

	@Autowired
	CommonUtils utils;

	@Autowired
	private SessionFactory _sessionFactory;

	private Session getSession()
	{
		return _sessionFactory.getCurrentSession();
	}

	public Order getOrderDetail(OrderKey key)
	{
		Order retOrder=null;
		try
		{
			Session session=getSession();
			retOrder=(Order) session.load(Order.class, key);
		}
		catch(Exception ex)
		{
			log.error("Error while getting order detail. Exception is => "+utils.getStackTrace(ex));
		}
		return retOrder;
	}

	public boolean updateOrderData(String feedback,float overallOrderRating,float overallRecipeRating,
			String jsonRatingData,OrderKey key)
	{
		boolean isSuccess=false;
		try
		{
		Session session=getSession();
		Order orderObj=(Order)session.load(Order.class, key);
		orderObj.setFeedback(feedback);
		orderObj.setOrderRating(overallOrderRating);
		orderObj.setRecipeRating(overallRecipeRating);
		orderObj.setRatingFeedback(jsonRatingData);
		session.saveOrUpdate(orderObj);
		isSuccess=true;
		}
		catch(Exception ex)
		{
			isSuccess=false;
			log.error("Error while updating orders table. Exception is => "+utils.getStackTrace(ex));
			
		}
		return isSuccess;
	}
}
