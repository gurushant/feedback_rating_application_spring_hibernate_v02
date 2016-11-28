/**
 * 
 */
package com.feedback_rating.entity.email_notification.dao.impl;

import java.util.Date;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.feedback_rating.entity.email_notification.dao.api.EmailNotificationDaoAPI;
import com.feedback_rating.entity.email_notification.model.EmailNotification;
import com.feedback_rating.entity.email_notification.utils.CommonUtils;
import com.feedback_rating.entity.email_notification.utils.EmailNotifyKey;


/**
 * @author gurushant.j
 *
 */
@Repository
public class EmailNotificationDaoImpl implements EmailNotificationDaoAPI
{
	private static final Logger log = LoggerFactory.getLogger(EmailNotificationDaoImpl.class);

	@Autowired
	CommonUtils utils;
	@Autowired
	private SessionFactory _sessionFactory;

	private Session getSession()
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


}
