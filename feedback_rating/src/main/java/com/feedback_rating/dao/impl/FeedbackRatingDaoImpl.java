package com.feedback_rating.dao.impl;

import java.util.Date;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.feedback_rating.dao.api.FeedbackRatingDaoAPI;
import com.feedback_rating.entity.utils.CommonUtils;
import com.feedback_rating.models.EmailNotification;
import com.feedback_rating.models.Order;
import com.feedback_rating.models.keys.EmailNotifyKey;
import com.feedback_rating.models.keys.OrderKey;

@Repository
public class FeedbackRatingDaoImpl implements FeedbackRatingDaoAPI {
	private static final Logger log = LoggerFactory.getLogger(FeedbackRatingDaoImpl.class);

	@Autowired
	CommonUtils utils;
	@Autowired
	private SessionFactory _sessionFactory;

	public Session getSession()
	{
		return _sessionFactory.getCurrentSession();
	}

	public boolean checkIsFeedbackReceived(EmailNotifyKey key)
	{
		boolean isFeedbackExists=true;
		try
		{
			Session session=getSession();
			Date feedbackReceivedTime = (Date)session.createCriteria(EmailNotification.class)
					.add(Restrictions.eq("isFeedbackExists",true))
					.add(Restrictions.eq("key",key))
					.setProjection(Property.forName("feedbackReceivedTime"))
					.uniqueResult();
			if(feedbackReceivedTime!=null)
			{
				isFeedbackExists=true;
			}
			else
				isFeedbackExists=false;
		}
		catch(Exception ex)
		{
			log.error("Error occured.Exception stacktrace is =>"+utils.getStackTrace(ex));
		}
		return isFeedbackExists;
	}

	public EmailNotification getEmailDetail(EmailNotifyKey key)
	{
		try
		{
			return (EmailNotification) getSession().load(EmailNotification.class, key);
		}
		catch(Exception ex)
		{
			log.error("Error occured.Exception stacktrace is =>"+utils.getStackTrace(ex));
			return null;
		}
	}

	public boolean updateEmailNotification(EmailNotifyKey key,boolean isFeedbackReceived)
	{
		boolean isSuccess=false;
		try
		{
			Session session=getSession();
			EmailNotification emailObj=(EmailNotification)session.load(EmailNotification.class, key);
			emailObj.setFeedbackReceivedTime(new Date());
			emailObj.setFeedbackExists(isFeedbackReceived);
			session.saveOrUpdate(emailObj);
			isSuccess=true;
		}
		catch(Exception ex)
		{
			isSuccess=false;
			log.error("Error occured.Exception stacktrace is =>"+utils.getStackTrace(ex));
		}
		return isSuccess;
	}
	
	//Order related Dao implementation
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
