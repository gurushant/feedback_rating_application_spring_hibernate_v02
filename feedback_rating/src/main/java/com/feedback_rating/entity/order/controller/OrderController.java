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
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.feedback_rating.entity.email_notification.models.EmailNotificationDao;
import com.feedback_rating.entity.email_notification.models.EmailNotificationDaoService;
import com.feedback_rating.entity.email_notification.models.EmailNotificationObjectModel;
import com.feedback_rating.entity.email_notification.models.EmailNotifyKey;
import com.feedback_rating.entity.email_notification.models.ResponseModel;
import com.feedback_rating.entity.email_notification.utils.CommonUtils;
import com.feedback_rating.entity.order.models.Order;
import com.feedback_rating.entity.order.models.OrderDao;
import com.feedback_rating.entity.order.models.OrderDaoService;
import com.feedback_rating.entity.order.models.OrderKey;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

/**
 * @author gurushant.j
 *
 */
@Controller
@RequestMapping("/orders")
@Transactional
public class OrderController {

	private static final Logger log = LoggerFactory.getLogger(OrderController.class);

	@Autowired
	CommonUtils utils;

	@Autowired
	OrderDaoService orderDaoService;

	@Autowired
	EmailNotificationDaoService emailDaoService;

	@RequestMapping(value="/getOrderDetail",
			produces="application/json",
			method=RequestMethod.GET)
	@ResponseBody
	public Object getOrderDetail(@QueryParam("orderId") int orderId,@QueryParam("restId") int restId)
	{
		return orderDaoService.getOrderDetails(orderId, restId);
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
