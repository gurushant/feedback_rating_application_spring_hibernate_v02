/**
 * 
 */
package com.feedbackRating.feedbackService.impl;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.ObjectNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateObjectRetrievalFailureException;
import org.springframework.stereotype.Service;

import com.feedbackRating.domain.FeedbackDomainObject;
import com.feedbackRating.domain.FeedbackResponse;
import com.feedbackRating.domain.OrderStatus;
import com.feedbackRating.persistence.dao.api.FeedbackRatingDaoAPI;
import com.feedbackRating.persistence.models.Order;
import com.feedbackRating.persistence.models.api.OrderResponseApi;
import com.feedbackRating.service.api.FeedbackServiceApi;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

/**
 * @author gurushant.j
 *
 */
@Service
@Transactional
public class FeedbackServiceImpl implements FeedbackServiceApi {
	private static final Logger log = LoggerFactory.getLogger(FeedbackServiceImpl.class);


	@Autowired
	private FeedbackRatingDaoAPI feedbackRatingDaoObj;

	/**
	 * This method is used to update the order's status into the email_notificats table.
	 */
	public FeedbackResponse postFeedback(String postPayload)
	{
		FeedbackResponse respModel=new FeedbackResponse();
		int orderLineId=0;
		try
		{
			log.debug("Received payload is => "+postPayload);

			Gson gson=new GsonBuilder().create();
			FeedbackDomainObject feedbackObj=gson.fromJson(postPayload, FeedbackDomainObject.class);
			int orderId=feedbackObj.getOrderId();
			int restId=feedbackObj.getRestId();
			//check whether order exist in system.
			if(!feedbackRatingDaoObj.isOrderExists(orderId, restId))
			{
				respModel=getSucessResponse("Order does not exist in system.Please check combination of order id and restaruent id");
			}
			else
			{
				if(isFeedbackExists(orderId,restId))
				{
					respModel=getSucessResponse("Feedback already exists for this order");

				}
				else
				{
					orderLineId=feedbackRatingDaoObj.getOrderLineId(orderId,restId);
					respModel=updateFeedbackInDb(feedbackObj,orderLineId);
				}
			}
		}
		catch(HibernateObjectRetrievalFailureException ex)
		{
			log.error("Error occured.Stacktrace is => "+getStackTrace(ex));
			FeedbackResponse responseModel=new FeedbackResponse();
			responseModel.setMessage("No matching records found in db.");
			responseModel.setStatus("SUCCESS");
			respModel=responseModel;

		}
		catch(Exception ex)
		{
			log.error("Error occured in postfeedback method.Stacktrace is => "+getStackTrace(ex));
			respModel=getErrorResponse("Error occured while posting feedback");
		}

		return respModel;

	}




	public FeedbackResponse updateFeedbackInDb(FeedbackDomainObject feedbackObj,int orderLineId)
	{
		FeedbackResponse respModel=null;
		List<Object> recipeList=feedbackObj.getRecipeList();
		log.debug("Recipe list is =>"+recipeList.toString());
		float recipeSumRating=extractRating(feedbackObj.getRecipeList());
		log.debug("Recipe rating sum is =>"+recipeSumRating);
		float overallRecipeRating=roundUpRating(recipeSumRating/recipeList.size());
		log.debug("Overall recipe rating is=> "+overallRecipeRating);
		log.debug("Economy rating is =>"+feedbackObj.getEconomyRate());
		log.debug("Ambience rating is =>"+feedbackObj.getAmbienceRate());
		log.debug("Qos rating is =>"+feedbackObj.getQosRate());

		float overallOrderRating=(overallRecipeRating+feedbackObj.getEconomyRate()+
				feedbackObj.getAmbienceRate()+feedbackObj.getQosRate())/4;
		overallOrderRating=roundUpRating(overallOrderRating);
		log.debug("Overall order rating is=> "+overallOrderRating);

		log.debug("Key in email_noficiation before update  is => "+orderLineId);

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

		if(feedbackRatingDaoObj.updateOrderData(feedbackObj.getFeedbackTxt(), 
				overallOrderRating, overallRecipeRating, recipeArr.toString(),orderLineId))
		{
			respModel=getSucessResponse("Successfully posted feedback");
			log.debug("Succesfully updated orders table");
			if(feedbackRatingDaoObj.updateEmailNotification(orderLineId, true))
			{
				log.debug("Succesfully updated email_notification table");

			}
			else
			{
				log.debug("Error while updating email_notification table");
				respModel=getErrorResponse("Error occured.Please check whether required fields are present");

			}
		}
		else
		{
			respModel=getErrorResponse("Error occured.Please check whether required fields are present");
			log.debug("Error while updating orders table");

		}

		return respModel;

	}


	//	public FeedbackResponse updateFeedbackInDb(FeedbackDomainObject feedbackObj,EmailNotifyKey key)
	//	{
	//		FeedbackResponse respModel=null;
	//		List<Object> recipeList=feedbackObj.getRecipeList();
	//		log.debug("Recipe list is =>"+recipeList.toString());
	//		float recipeSumRating=extractRating(feedbackObj.getRecipeList());
	//		log.debug("Recipe rating sum is =>"+recipeSumRating);
	//		float overallRecipeRating=roundUpRating(recipeSumRating/recipeList.size());
	//		log.debug("Overall recipe rating is=> "+overallRecipeRating);
	//		log.debug("Economy rating is =>"+feedbackObj.getEconomyRate());
	//		log.debug("Ambience rating is =>"+feedbackObj.getAmbienceRate());
	//		log.debug("Qos rating is =>"+feedbackObj.getQosRate());
	//
	//		float overallOrderRating=(overallRecipeRating+feedbackObj.getEconomyRate()+
	//				feedbackObj.getAmbienceRate()+feedbackObj.getQosRate())/4;
	//		overallOrderRating=roundUpRating(overallOrderRating);
	//		log.debug("Overall order rating is=> "+overallOrderRating);
	//
	//		log.debug("Key in email_noficiation before update  is => "+key);
	//		if(feedbackRatingDaoObj.updateEmailNotification(key, true))
	//		{
	//			log.debug("Succesfully updated email_notification table");
	//			//Updating orders table
	//			JsonArray recipeArr=new JsonArray();
	//			for(Object token:recipeList)
	//			{
	//				JsonObject element=new JsonObject();
	//				String tokenTempArr[]=token.toString().split(":");
	//				element.addProperty(tokenTempArr[0], tokenTempArr[1]);
	//				recipeArr.add(element);
	//			}
	//
	//			log.debug("JSON Recipe string is =>"+recipeArr.toString());
	//			log.debug("Updating orders table");
	//			OrderKey orderKey=new OrderKey(feedbackObj.getOrderId(),feedbackObj.getRestId());
	//
	//			if(feedbackRatingDaoObj.updateOrderData(feedbackObj.getFeedbackTxt(), 
	//					overallOrderRating, overallRecipeRating, recipeArr.toString(), orderKey))
	//			{
	//				respModel=getSucessResponse("Successfully posted feedback");
	//				log.debug("Succesfully updated orders table");
	//			}
	//			else
	//			{
	//				respModel=getErrorResponse("Error occured.Please check whether required fields are present");
	//				log.debug("Error while updating orders table");
	//			}
	//		}
	//		else
	//		{
	//			log.debug("Error while updating email_notification table");
	//			respModel=getErrorResponse("Error occured.Please check whether required fields are present");
	//		}
	//
	//		return respModel;
	//	
	//	}

	/**
	 * This method checks whether feedback exists into the database.
	 */
	public boolean isFeedbackExists(int orderId,int restId )
	{
		return feedbackRatingDaoObj.checkIsFeedbackReceived( orderId, restId);
	}

	/**
	 * Returns order details response.
	 */
	@Override
	public OrderResponseApi getOrderDetails(int orderId,int restId)
	{
		OrderResponseApi response=null;
		try
		{
			OrderStatus responseModel=new OrderStatus();
			if(!feedbackRatingDaoObj.isOrderExists(orderId, restId))
			{
				responseModel.setMessage("Order does not exist in system.Please check combination of order id and restaruent id");
				responseModel.setStatus("SUCCESS");
				response=responseModel;
			}
			else
			{
				boolean isFeedbackExist=isFeedbackExists(orderId,restId);
				log.debug("Feedback for this order=>"+orderId+" is "+isFeedbackExist);
				if(!isFeedbackExist)
				{
					int orderLineId=feedbackRatingDaoObj.getOrderLineId(orderId,restId);
					log.debug("Request received to get order details.=>"+orderId+","+restId);
					Order orderObj=feedbackRatingDaoObj.getOrderDetail(orderLineId);

					orderObj.setMessage("Order is successfully retrieved");
					orderObj.setStatus("SUCCESS");
					response= orderObj;
					log.debug("Get Order details api response is => "+response);
				}
				else
				{
					responseModel.setMessage("Feedback already received for this order");
					responseModel.setStatus("SUCCESS");
					response=responseModel;
				}
			}
		}
		catch(ObjectNotFoundException ex)
		{
			log.error("Error occured.Stacktrace is => "+getStackTrace(ex));
			OrderStatus responseModel=new OrderStatus();
			responseModel.setMessage("No matching records found in db.");
			responseModel.setStatus("SUCCESS");
			response=responseModel;
		}
		catch(Exception ex)
		{
			log.error("Error occured.Stacktrace is => "+getStackTrace(ex));
			OrderStatus responseModel=new OrderStatus();
			responseModel.setMessage("Error occured.Please try again latter.");
			responseModel.setStatus("ERROR");
			response=responseModel;
		}
		return response;

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
	public FeedbackResponse getErrorResponse(String message)
	{
		FeedbackResponse respModel=new FeedbackResponse();
		respModel.setMessage(message);
		respModel.setStatus("ERROR");
		return respModel;
	}

	/**
	 * Success response of the feedback and rating api.
	 * @param message
	 * @return
	 */
	public FeedbackResponse getSucessResponse(String message)
	{
		FeedbackResponse respModel=new FeedbackResponse();
		respModel.setMessage(message);
		respModel.setStatus("SUCCESS");
		return respModel;
	}



}
