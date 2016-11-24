/**
 * 
 */
package com.feedback_rating.entity.order.controller;

import java.util.List;

import javax.transaction.Transactional;
import javax.ws.rs.QueryParam;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonParser;
import com.feedback_rating.entity.email_notification.controller.EmailNotificationController;
import com.feedback_rating.entity.email_notification.models.EmailNotificationDao;
import com.feedback_rating.entity.email_notification.models.EmailNotificationObjectModel;
import com.feedback_rating.entity.email_notification.models.EmailNotifyKey;
import com.feedback_rating.entity.email_notification.models.ResponseModel;
import com.feedback_rating.entity.email_notification.utils.CommonUtils;
import com.feedback_rating.entity.order.models.Order;
import com.feedback_rating.entity.order.models.OrderDao;
import com.feedback_rating.entity.order.models.OrderKey;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

/**
 * @author gurushant.j
 *
 */
@RestController
@RequestMapping("/orders")
@Transactional
public class OrderController {

	private static final Logger log = LoggerFactory.getLogger(OrderController.class);

	@Autowired
	CommonUtils utils;

	@Autowired
	OrderDao orderDao;
	
	@Autowired
	EmailNotificationDao emailDao;
	
	@RequestMapping(value="/getOrderDetail",
					produces="application/json",
					consumes="application/json",
					method=RequestMethod.GET)
	@ResponseBody
	public Object getOrderDetail(@QueryParam("orderId") int orderId,@QueryParam("restId") int restId)
	{
		Object response=null;
		try
		{
			OrderKey key=new OrderKey(orderId,restId);
			log.debug("Request received to get order details.=>"+key);
			Order orderObj=orderDao.getOrderDetail(key);
			orderObj.setMessage("Order is successfully retrieved");
			orderObj.setStatus("SUCCESS");
			response= orderObj;
			log.debug("Get Order details api response is => "+response);
		}
		catch(Exception ex)
		{
			log.error("Error occured.Stacktrace is => "+utils.getStackTrace(ex));
			response= utils.getErrorResponse("Please check the correct order Id or try again later");
		}
		return response;
	}
	
	@RequestMapping(value="/postFeedback",
			produces="application/json",
			consumes="application/json",
			method=RequestMethod.POST	)
	@ResponseBody
	public ResponseModel postFeedback(@RequestBody String postPayload)
	{
		ResponseModel respModel=new ResponseModel();
		try
		{
			log.debug("Received payload is => "+postPayload);
			Gson gson=new GsonBuilder().create();
			EmailNotificationObjectModel emailObj=gson.fromJson(postPayload, EmailNotificationObjectModel.class);
			List<Object> recipeList=emailObj.getRecipeList();
			log.debug("Recipe list is =>"+recipeList.toString());
			float recipeSumRating=utils.extractRating(emailObj.getRecipeList());
			float overallRecipeRating=utils.roundUpRating(recipeSumRating/recipeList.size());
			log.debug("Overall recipe rating is=> "+overallRecipeRating);
			EmailNotifyKey key=new EmailNotifyKey(emailObj.getOrderId(),emailObj.getRestId());
			log.debug("Key in email_noficiation before update  is => "+key);
			emailDao.updateEmailNotification(key, true);
			com.google.gson.JsonParser parser=new com.google.gson.JsonParser();
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
			orderDao.updateOrderData(emailObj.getFeedbackTxt(), 
					overallRecipeRating, overallRecipeRating, recipeArr.toString(), orderKey);
//			log.debug("Key in email_noficiation after update  is => "+key);
			respModel=utils.getSucessResponse("Successfully posted feedback");
		}
		catch(Exception ex)
		{
			log.error("Error occured in postback method.Stacktrace is => "+utils.getStackTrace(ex));
			respModel=utils.getErrorResponse("Error occured while posting feedback");
		}
		
		return respModel;
	}

}
