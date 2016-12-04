package com.feedbackRating.persistence.dao.impl;

import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.feedbackRating.persistence.dao.api.FeedbackRatingDaoAPI;
import com.feedbackRating.persistence.models.EmailNotification;
import com.feedbackRating.persistence.models.Order;
import com.feedbackRating.utils.CommonUtils;

/**
 * This class implements FeedbackRatingDaoAPI interface
 * @author gurushant.j
 *
 */
@Repository
public class FeedbackRatingDaoImpl implements FeedbackRatingDaoAPI {
	private static final Logger log = LoggerFactory.getLogger(FeedbackRatingDaoImpl.class);

	@Autowired
	private SessionFactory _sessionFactory;

	public Session getSession()
	{
		return _sessionFactory.getCurrentSession();
	}

	/**
	 * Check if order exists.
	 */
	public boolean isOrderExists(int orderId,int restId)
	{
		boolean isFeedbackExists=true;
		Session session=getSession();
		Query isFeedbackExistQuery= session.createQuery("select id from Order where orderId=:order_id and  restId=:restaruent_id)");
		isFeedbackExistQuery.setInteger("order_id", orderId);
		isFeedbackExistQuery.setInteger("restaruent_id", restId);
		List<Boolean>feedbackList= isFeedbackExistQuery.list();
		if(feedbackList.size() > 0)
			isFeedbackExists= true;
		else
			isFeedbackExists=false;
		return isFeedbackExists;
	}
	
	/**
	 * Checks whether feedback is already exists in database.
	 * If exists return true else false
	 */
	public boolean checkIsFeedbackReceived(int orderId,int restId)
	{
		boolean isFeedbackExists=true;
		Session session=getSession();
		Query isFeedbackExistQuery= session.createQuery("select isFeedbackExists from EmailNotification where order_line_id="
				+ "(select id from Order where orderId=:order_id and  restId=:restaruent_id)");
		
		isFeedbackExistQuery.setInteger("order_id", orderId);
		isFeedbackExistQuery.setInteger("restaruent_id", restId);
		List<Boolean>feedbackList= isFeedbackExistQuery.list();
		if(feedbackList.get(0).equals(true))
		{
			isFeedbackExists=true;
		}
		else
		{
			isFeedbackExists=false;
		}
		return isFeedbackExists;
	}
	
	
	public int getOrderLineId(int orderId,int restId)
	{
		Session session=getSession();
		Query orderQuery= session.createQuery("select id from Order where orderId=:order_id and  restId=:restaruent_id)");
		
		orderQuery.setInteger("order_id", orderId);
		orderQuery.setInteger("restaruent_id", restId);
		List<Integer>orderIdList= orderQuery.list();
		return orderIdList.get(0);

	}

	
	

	
	/**
	 * This method is used to update the order's feedback and rating status into the db.
	 */
	public boolean updateEmailNotification(int orderLineId ,boolean isFeedbackReceived)
	{
		boolean isSuccess=false;
		Session session=getSession();
		EmailNotification emailObj=(EmailNotification)session.load(EmailNotification.class, orderLineId);
		emailObj.setFeedbackReceivedTime(new Date());
		emailObj.setFeedbackExists(isFeedbackReceived);
		session.saveOrUpdate(emailObj);
		isSuccess=true;
		return isSuccess;
	}

	/**
	 * This method is used to fetch order details from the database.
	 */
	public Order getOrderDetail(int orderLineId)
	{
		Order retOrder=null;

		Session session=getSession();
		retOrder=(Order) session.load(Order.class, orderLineId);
		return retOrder;	
	}

	/**
	 * This method is used to update the order's feedback and rating status into the order table .
	 */
	public boolean updateOrderData(String feedback,float overallOrderRating,float overallRecipeRating,
			String jsonRatingData,int orderLineId)
	{
		boolean isSuccess=false;
		Session session=getSession();
		Order orderObj=(Order)session.load(Order.class, orderLineId);
		orderObj.setFeedback(feedback);
		orderObj.setOrderRating(overallOrderRating);
		orderObj.setRecipeRating(overallRecipeRating);
		orderObj.setRatingFeedback(jsonRatingData);
		session.saveOrUpdate(orderObj);
		isSuccess=true;
		return isSuccess;
	}


}
