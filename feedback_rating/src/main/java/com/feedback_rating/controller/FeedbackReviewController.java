/**
 * 
 */
package com.feedback_rating.controller;

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

import com.feedback_rating.feedback_service.api.FeedbackServiceApi;
import com.feedback_rating.models.FeedbackResponse;
import com.feedback_rating.models.api.OrderResponseApi;


@RestController
@RequestMapping("/feedbackReview")
@Transactional
public class FeedbackReviewController {

	private static final Logger log = LoggerFactory.getLogger(FeedbackReviewController.class);

	@Autowired
	private FeedbackServiceApi feedbackServiceObj;


	/**
	 * This method is used to get the order details.
	 * @param orderId
	 * @param restId
	 * @return
	 */
	@RequestMapping(value="/getOrderDetail",
			produces="application/json",
			method=RequestMethod.GET)
	@ResponseBody
	public OrderResponseApi getOrderDetail(@QueryParam("orderId") int orderId,@QueryParam("restId") int restId)
	{
		OrderResponseApi response=null;
		try
		{
			log.info("Hitting get order detail api.Order id is "+orderId+",Rest Id is "+restId);
			response=feedbackServiceObj.getOrderDetails(orderId, restId);
		}
		catch(Exception ex)
		{
			log.error("Error occured");
		}
		return response;
	}


	/**
	 * This method is used to process and save the feedback into the db.
	 * @param postPayload
	 * @return
	 */
	@RequestMapping(value="/postFeedback",
			produces="application/json",
			consumes="application/json",
			method=RequestMethod.POST	)
	@ResponseBody
	public FeedbackResponse postFeedback(@RequestBody String postPayload)
	{
		log.info("Hitting post feedback api.Payload is "+postPayload);
		return feedbackServiceObj.postFeedback(postPayload);
	}

}
