/**
 * 
 */
package com.feedback_rating.entity.email_notification;

import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.feedback_rating.entity.order.Order;

/**
 * @author gurushant.j
 *
 */
@Repository
@Transactional
public class EmailNotificationDao {
	@Autowired
	private SessionFactory _sessionFactory;
	
	private Session getSession()
	{
		return _sessionFactory.openSession();
	}
	
	public EmailNotification getEmailDetail(EmailNotifyKey key)
	{
		return (EmailNotification) getSession().load(EmailNotification.class, key);
	}

}
