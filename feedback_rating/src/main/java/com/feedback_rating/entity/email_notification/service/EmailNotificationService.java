/**
 * 
 */
package com.feedback_rating.entity.email_notification.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.feedback_rating.entity.email_notification.dao.impl.EmailNotificationDaoImpl;
import com.feedback_rating.entity.email_notification.domain.EmailNotificationObjectModel;
import com.feedback_rating.entity.email_notification.utils.CommonUtils;
import com.feedback_rating.entity.email_notification.utils.EmailNotifyKey;
import com.feedback_rating.entity.email_notification.utils.ResponseModel;
import com.feedback_rating.entity.order.dao.service.OrderDaoService;
import com.feedback_rating.entity.order.models.OrderKey;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

/**
 * @author gurushant.j
 *
 */
@Service
public class EmailNotificationService {

	private static final Logger log = LoggerFactory.getLogger(EmailNotificationService.class);


	@Autowired
	CommonUtils utils;

	@Autowired
	OrderDaoService orderDaoService;

	@Autowired
	EmailNotificationDaoImpl emailDao;

	public ResponseModel postFeedback(String postPayload)
	{
		ResponseModel respModel=new ResponseModel();
		try
		{
			log.debug("Received payload is => "+postPayload);

			Gson gson=new GsonBuilder().create();
			EmailNotificationObjectModel emailObj=gson.fromJson(postPayload, EmailNotificationObjectModel.class);
			EmailNotifyKey key=new EmailNotifyKey(emailObj.getOrderId(),emailObj.getRestId());
			if(checkIsFeedbackReceived(key))
			{
				respModel=utils.getSucessResponse("Feedback already exists for this order");

			}
			else
			{
				List<Object> recipeList=emailObj.getRecipeList();
				log.debug("Recipe list is =>"+recipeList.toString());
				float recipeSumRating=utils.extractRating(emailObj.getRecipeList());
				log.debug("Recipe rating sum is =>"+recipeSumRating);
				float overallRecipeRating=utils.roundUpRating(recipeSumRating/recipeList.size());
				log.debug("Overall recipe rating is=> "+overallRecipeRating);
				log.debug("Economy rating is =>"+emailObj.getEconomyRate());
				log.debug("Ambience rating is =>"+emailObj.getAmbienceRate());
				log.debug("Qos rating is =>"+emailObj.getQosRate());
				float overallOrderRating=(overallRecipeRating+emailObj.getEconomyRate()+
						emailObj.getAmbienceRate()+emailObj.getQosRate())/4;
				overallOrderRating=utils.roundUpRating(overallOrderRating);
				log.debug("Overall order rating is=> "+overallOrderRating);

				log.debug("Key in email_noficiation before update  is => "+key);
				if(emailDao.updateEmailNotification(key, true))
				{
					log.debug("Succesfully updated email_notification table");
				}
				else
				{
					log.debug("Error while updating email_notification table");
				}
				JsonArray recipeArr=new JsonArray();
				for(Object token:recipeList)
				{
					JsonObject element=new JsonObject();
					String tokenTempArr[]=token.toString().split(":");
					element.addProperty(tokenTempArr[0], tokenTempArr[1]);
					recipeArr.add(element);
				}

				log.debug("JSON Recipe string is =>"+recipeArr.toString());
				log.debug("Updating orders table");
				OrderKey orderKey=new OrderKey(emailObj.getOrderId(),emailObj.getRestId());

				if(orderDaoService.updateOrderData(emailObj.getFeedbackTxt(), 
						overallOrderRating, overallRecipeRating, recipeArr.toString(), orderKey))
				{
					log.debug("Succesfully updated orders table");
				}
				else
				{
					log.debug("Error while updating orders table");

				}
				respModel=utils.getSucessResponse("Successfully posted feedback");
			}
		}
		catch(Exception ex)
		{
			log.error("Error occured in postback method.Stacktrace is => "+utils.getStackTrace(ex));
			respModel=utils.getErrorResponse("Error occured while posting feedback");
		}

		return respModel;

	}

	public boolean checkIsFeedbackReceived(EmailNotifyKey key)
	{
		return emailDao.checkIsFeedbackReceived(key);
	}
}
