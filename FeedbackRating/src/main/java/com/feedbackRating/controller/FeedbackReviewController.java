/**
 * 
 */
package com.feedbackRating.controller;

import javax.websocket.server.PathParam;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.feedbackRating.domain.FeedbackResponse;
import com.feedbackRating.persistence.models.api.OrderResponseApi;
import com.feedbackRating.service.api.FeedbackServiceApi;


@RestController
@RequestMapping("/feedbackReview")
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
	public OrderResponseApi getOrderDetail(@PathParam("orderId") int orderId,@PathParam("restId") int restId)
	{
		OrderResponseApi response=null;
		log.info("Hitting get order detail api.Order id is "+orderId+",Rest Id is "+restId);
		response=feedbackServiceObj.getOrderDetails(orderId, restId);
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
