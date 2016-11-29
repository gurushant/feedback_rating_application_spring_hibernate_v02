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

import com.feedback_rating.domain.FeedbackResponseModel;
import com.feedback_rating.feedback_service.api.FeedbackServiceApi;

/**
 * @author gurushant.j
 *
 */
@RestController
@RequestMapping("/feedbackReview")
@Transactional
public class FeedbackReviewController {

	private static final Logger log = LoggerFactory.getLogger(FeedbackReviewController.class);

	@Autowired
	private FeedbackServiceApi feedbackServiceObj;


	
	@RequestMapping(value="/getOrderDetail",
			produces="application/json",
			method=RequestMethod.GET)
	@ResponseBody
	public Object getOrderDetail(@QueryParam("orderId") int orderId,@QueryParam("restId") int restId)
	{
		return feedbackServiceObj.getOrderDetails(orderId, restId);
	}


	@RequestMapping(value="/postFeedback",
			produces="application/json",
			consumes="application/json",
			method=RequestMethod.POST	)
	@ResponseBody
	public FeedbackResponseModel postFeedback(@RequestBody String postPayload)
	{
		return feedbackServiceObj.postFeedback(postPayload);
	}

}
