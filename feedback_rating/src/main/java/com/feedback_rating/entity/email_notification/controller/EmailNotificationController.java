/**
 * 
 */
package com.feedback_rating.entity.email_notification.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.feedback_rating.entity.email_notification.models.EmailNotificationDao;
import com.feedback_rating.entity.email_notification.models.EmailNotificationObjectModel;
import com.feedback_rating.entity.email_notification.models.ResponseModel;
import com.feedback_rating.entity.email_notification.utils.CommonUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * @author gurushant.j
 *
 */
@RestController
@RequestMapping("/review")
public class EmailNotificationController {

	private static final Logger log = LoggerFactory.getLogger(EmailNotificationController.class);

	@Autowired
	EmailNotificationDao emailDao;

	@Autowired
	CommonUtils utils;

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
			float recipeSumRating=utils.extractRating(emailObj.getRecipeList());
			float overallRecipeRating=utils.roundUpRating(recipeSumRating/recipeList.size());
			log.debug("Overall recipe rating is=> "+overallRecipeRating);
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
