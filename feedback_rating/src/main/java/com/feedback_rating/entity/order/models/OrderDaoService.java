/**
 * 
 */
package com.feedback_rating.entity.order.models;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.feedback_rating.entity.email_notification.models.EmailNotificationDao;
import com.feedback_rating.entity.email_notification.models.EmailNotificationDaoService;
import com.feedback_rating.entity.email_notification.models.EmailNotifyKey;
import com.feedback_rating.entity.email_notification.models.ResponseModel;
import com.feedback_rating.entity.email_notification.utils.CommonUtils;

/**
 * @author gurushant.j
 *
 */
@Service
public class OrderDaoService {
	private static final Logger log = LoggerFactory.getLogger(OrderDaoService.class);

	@Autowired
	CommonUtils utils;

	@Autowired
	OrderDao orderDaoObj;

	@Autowired
	EmailNotificationDaoService emailDaoService;

	public Object getOrderDetails(int orderId,int restId)
	{
		Object response=null;
		try
		{
			EmailNotifyKey emailKey=new EmailNotifyKey(orderId, restId);
			boolean isFeedbackExist=emailDaoService.checkIsFeedbackReceived(emailKey);
			log.debug("Feedback for this order=>"+orderId+" is "+isFeedbackExist);
			if(!isFeedbackExist)
			{
				OrderKey key=new OrderKey(orderId,restId);
				log.debug("Request received to get order details.=>"+key);
				Order orderObj=orderDaoObj.getOrderDetail(key);
				orderObj.setMessage("Order is successfully retrieved");
				orderObj.setStatus("SUCCESS");
				response= orderObj;
				log.debug("Get Order details api response is => "+response);
			}
			else
			{
				ResponseModel responseModel=new ResponseModel();
				responseModel.setMessage("Feedback already received for this order");
				responseModel.setStatus("SUCCESS");
				response=responseModel;
			}
		}
		catch(Exception ex)
		{
			log.error("Error occured.Stacktrace is => "+utils.getStackTrace(ex));
			response= utils.getErrorResponse("Please check the correct order Id or try again later");
		}
		return response;

	}
}
