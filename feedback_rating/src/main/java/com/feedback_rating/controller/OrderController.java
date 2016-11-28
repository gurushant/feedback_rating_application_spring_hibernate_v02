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

import com.feedback_rating.entity.email_notification.service.EmailNotificationService;
import com.feedback_rating.entity.email_notification.utils.ResponseModel;
import com.feedback_rating.entity.order.service.OrderService;

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
	private OrderService orderDaoService;

	@Autowired
	private EmailNotificationService emailDaoService;

	
	@RequestMapping(value="/getOrderDetail",
			produces="application/json",
			method=RequestMethod.GET)
	@ResponseBody
	public Object getOrderDetail(@QueryParam("orderId") int orderId,@QueryParam("restId") int restId)
	{
		return orderDaoService.getOrderDetails(orderId, restId);
	}

	 @RequestMapping("/")
	 public String gethelloWorld() {
	        return "Hello World!";
	    }

	@RequestMapping(value="/postFeedback",
			produces="application/json",
			consumes="application/json",
			method=RequestMethod.POST	)
	@ResponseBody
	public ResponseModel postFeedback(@RequestBody String postPayload)
	{
		return emailDaoService.postFeedback(postPayload);
	}

}
