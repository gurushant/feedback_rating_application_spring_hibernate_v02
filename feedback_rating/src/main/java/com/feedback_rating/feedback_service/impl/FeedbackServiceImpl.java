/**
 * 
 */
package com.feedback_rating.feedback_service.impl;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

import org.hibernate.ObjectNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.feedback_rating.dao.api.FeedbackRatingDaoAPI;
import com.feedback_rating.domain.FeedbackDomainObject;
import com.feedback_rating.domain.FeedbackResponseModel;
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
	FeedbackRatingDaoAPI feedbackRatingDaoObj;

	/**
	 * This method is used to update the order's status into the email_notificats table.
	 */
	public FeedbackResponseModel postFeedback(String postPayload)
	{
		FeedbackResponseModel respModel=new FeedbackResponseModel();
		try
		{
			log.debug("Received payload is => "+postPayload);

			Gson gson=new GsonBuilder().create();
			FeedbackDomainObject emailObj=gson.fromJson(postPayload, FeedbackDomainObject.class);
			EmailNotifyKey key=new EmailNotifyKey(emailObj.getOrderId(),emailObj.getRestId());
			if(isFeedbackExists(key))
			{
				respModel=getSucessResponse("Feedback already exists for this order");

			}
			else
			{
				List<Object> recipeList=emailObj.getRecipeList();
				log.debug("Recipe list is =>"+recipeList.toString());
				float recipeSumRating=extractRating(emailObj.getRecipeList());
				log.debug("Recipe rating sum is =>"+recipeSumRating);
				float overallRecipeRating=roundUpRating(recipeSumRating/recipeList.size());
				log.debug("Overall recipe rating is=> "+overallRecipeRating);
				log.debug("Economy rating is =>"+emailObj.getEconomyRate());
				log.debug("Ambience rating is =>"+emailObj.getAmbienceRate());
				log.debug("Qos rating is =>"+emailObj.getQosRate());

				float overallOrderRating=(overallRecipeRating+emailObj.getEconomyRate()+
						emailObj.getAmbienceRate()+emailObj.getQosRate())/4;
				overallOrderRating=roundUpRating(overallOrderRating);
				log.debug("Overall order rating is=> "+overallOrderRating);

				log.debug("Key in email_noficiation before update  is => "+key);
				if(feedbackRatingDaoObj.updateEmailNotification(key, true))
				{
					log.debug("Succesfully updated email_notification table");
					//Updating orders table
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
						respModel=getSucessResponse("Successfully posted feedback");
						log.debug("Succesfully updated orders table");
					}
					else
					{
						respModel=getErrorResponse("Error occured.Please check whether required fields are present");
						log.debug("Error while updating orders table");
					}
				}
				else
				{
					log.debug("Error while updating email_notification table");
					respModel=getErrorResponse("Error occured.Please check whether required fields are present");
				}


			}
		}
		catch(Exception ex)
		{
			log.error("Error occured in postback method.Stacktrace is => "+getStackTrace(ex));
			respModel=getErrorResponse("Error occured while posting feedback");
		}

		return respModel;

	}

	/**
	 * This method checks whether feedback exists into the database.
	 */
	public boolean isFeedbackExists(EmailNotifyKey key)
	{
		return feedbackRatingDaoObj.checkIsFeedbackReceived(key);
	}

	/**
	 * Returns order details response.
	 */
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
			}
		}
		catch(ObjectNotFoundException ex)
		{
			log.error("Error occured.Stacktrace is => "+getStackTrace(ex));
			response= getSucessResponse("No rows found in db.Please check the correct order Id or restaurent Id .");
			response=null;
		}
		catch(Exception ex)
		{
			log.error("Error occured.Stacktrace is => "+getStackTrace(ex));
			response= getErrorResponse("Error ocuured .Please try again latter.");
		}
		return response;

	}

	/**
	 * This method updates order status about feedback and rating into the db.
	 */
	public boolean updateOrderData(String feedback,float overallOrderRating,float overallRecipeRating,
			String jsonRatingData,OrderKey key)
	{
		return feedbackRatingDaoObj.updateOrderData(feedback, overallOrderRating, overallRecipeRating, jsonRatingData, key);
	}

	/**
	 * This method is used to extract rating related data from the request of postfeedback api.
	 * @param recipeList
	 * @return
	 */
	public float extractRating(List<Object> recipeList)
	{
		float recipeRating=0;
		for(Object recipe:recipeList)
		{
			String tempRecipe=recipe.toString();
			recipeRating+=Float.parseFloat(tempRecipe.split(":")[1]);
		}
		return recipeRating;
	}


	/**
	 * This method is used to round up the rating.Like 2.3 to 2.5,2.1 to 2.0 so on...
	 * @param rating
	 * @return
	 */
	public float roundUpRating(float rating)
	{
		rating=(float) (rating/0.5);
		rating=Math.round(rating);
		return rating/2;

	}

	/**
	 * Converts exception stacktrace into the string object.
	 * @param ex
	 * @return
	 */
	public String getStackTrace(Exception ex)
	{
		StringWriter wr=new  StringWriter();
		ex.printStackTrace(new PrintWriter(wr));
		return wr.toString();
	}

	/**
	 * Error response of feedback and rating api.
	 * @param message
	 * @return
	 */
	public FeedbackResponseModel getErrorResponse(String message)
	{
		FeedbackResponseModel respModel=new FeedbackResponseModel();
		respModel.setMessage(message);
		respModel.setStatus("ERROR");
		return respModel;
	}

	/**
	 * Success response of the feedback and rating api.
	 * @param message
	 * @return
	 */
	public FeedbackResponseModel getSucessResponse(String message)
	{
		FeedbackResponseModel respModel=new FeedbackResponseModel();
		respModel.setMessage(message);
		respModel.setStatus("SUCCESS");
		return respModel;
	}



}