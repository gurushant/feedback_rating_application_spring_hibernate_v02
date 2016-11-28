/**
 * 
 */
package com.feedback_rating.entity.order.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.feedback_rating.entity.email_notification.service.EmailNotificationService;
import com.feedback_rating.entity.email_notification.utils.CommonUtils;
import com.feedback_rating.entity.email_notification.utils.EmailNotifyKey;
import com.feedback_rating.entity.email_notification.utils.ResponseModel;
import com.feedback_rating.entity.order.dao.api.OrderDaoAPI;
import com.feedback_rating.entity.order.dao.impl.OrderDao;
import com.feedback_rating.entity.order.models.Order;
import com.feedback_rating.entity.order.models.OrderKey;

/**
 * @author gurushant.j
 *
 */
@Service
public class OrderService implements OrderServiceApi {
	private static final Logger log = LoggerFactory.getLogger(OrderService.class);

	@Autowired
	private CommonUtils utils;

	@Autowired
	private OrderDaoAPI orderDaoObj;

	@Autowired
	private EmailNotificationService emailDaoService;

	@Override
	public boolean isFeedbackExists(EmailNotifyKey emailKey)
	{
		return emailDaoService.checkIsFeedbackReceived(emailKey);
	}
	
	public Order returnOrder(OrderKey key)
	{
		EmailNotifyKey email=new EmailNotifyKey(key.getId(),key.getRestId());
		if(isFeedbackExists(email)==false)
		{
			return 	orderDaoObj.getOrderDetail(key);

		}
		return null;
	}
	
	public Object getOrderDetails(int orderId,int restId)
	{
		Object response=null;
		try
		{
			EmailNotifyKey emailKey=new EmailNotifyKey(orderId, restId);
			System.out.println("feedback is not received...");
			boolean isFeedbackExist=isFeedbackExists(emailKey);
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
	
	public boolean updateOrderData(String feedback,float overallOrderRating,float overallRecipeRating,
			String jsonRatingData,OrderKey key)
	{
		return orderDaoObj.updateOrderData(feedback, overallOrderRating, overallRecipeRating, jsonRatingData, key);
	}
}
