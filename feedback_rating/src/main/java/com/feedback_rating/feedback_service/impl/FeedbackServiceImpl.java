/**
 * 
 */
package com.feedback_rating.feedback_service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.feedback_rating.dao.api.FeedbackRatingDaoAPI;
import com.feedback_rating.domain.FeedbackObjectModel;
import com.feedback_rating.domain.FeedbackResponseModel;
import com.feedback_rating.entity.utils.CommonUtils;
import com.feedback_rating.feedback_service.api.FeedbackServiceApi;
import com.feedback_rating.models.Order;
import com.feedback_rating.models.keys.EmailNotifyKey;
import com.feedback_rating.models.keys.OrderKey;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

/**
 * @author gurushant.j
 *
 */
@Service
public class FeedbackServiceImpl implements FeedbackServiceApi {
	private static final Logger log = LoggerFactory.getLogger(FeedbackServiceImpl.class);


	@Autowired
	CommonUtils utils;

	@Autowired
	FeedbackRatingDaoAPI feedbackRatingDaoObj;

	public FeedbackResponseModel postFeedback(String postPayload)
	{
		FeedbackResponseModel respModel=new FeedbackResponseModel();
		try
		{
			log.debug("Received payload is => "+postPayload);

			Gson gson=new GsonBuilder().create();
			FeedbackObjectModel emailObj=gson.fromJson(postPayload, FeedbackObjectModel.class);
			EmailNotifyKey key=new EmailNotifyKey(emailObj.getOrderId(),emailObj.getRestId());
			if(isFeedbackExists(key))
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
				if(feedbackRatingDaoObj.updateEmailNotification(key, true))
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

				if(feedbackRatingDaoObj.updateOrderData(emailObj.getFeedbackTxt(), 
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

	public boolean isFeedbackExists(EmailNotifyKey key)
	{
		return feedbackRatingDaoObj.checkIsFeedbackReceived(key);
	}

	//Order related service methods
	@Override
	public Object getOrderDetails(int orderId,int restId)
	{
		Object response=null;
		try
		{
			EmailNotifyKey emailKey=new EmailNotifyKey(orderId, restId);
			boolean isFeedbackExist=isFeedbackExists(emailKey);
			log.debug("Feedback for this order=>"+orderId+" is "+isFeedbackExist);
			if(!isFeedbackExist)
			{
				OrderKey key=new OrderKey(orderId,restId);
				log.debug("Request received to get order details.=>"+key);
				Order orderObj=feedbackRatingDaoObj.getOrderDetail(key);
				orderObj.setMessage("Order is successfully retrieved");
				orderObj.setStatus("SUCCESS");
				response= orderObj;
				log.debug("Get Order details api response is => "+response);
			}
			else
			{
				FeedbackResponseModel responseModel=new FeedbackResponseModel();
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
		return feedbackRatingDaoObj.updateOrderData(feedback, overallOrderRating, overallRecipeRating, jsonRatingData, key);
	}

	
}
