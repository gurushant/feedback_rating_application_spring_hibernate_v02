/**
 * 
 */
package com.feedback_rating.entity.email_notification.controller;

import javax.ws.rs.QueryParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.feedback_rating.entity.email_notification.EmailNotification;
import com.feedback_rating.entity.email_notification.EmailNotificationDao;
import com.feedback_rating.entity.email_notification.EmailNotifyKey;
import com.feedback_rating.entity.order.Order;
import com.feedback_rating.entity.order.OrderDao;
import com.feedback_rating.entity.order.OrderKey;

/**
 * @author gurushant.j
 *
 */
@RestController
@RequestMapping("/order")
public class EmailNotificationController {
	
	@Autowired
	EmailNotificationDao emailDao;
	@RequestMapping(value="/getEmailDetail",produces="application/json")
	@ResponseBody
	public EmailNotification getOrderDetail(@QueryParam("orderId") int orderId,@QueryParam("restId") int restId)
	{
		EmailNotifyKey key=new EmailNotifyKey(orderId,restId);
		return emailDao.getEmailDetail(key);
	}
}
