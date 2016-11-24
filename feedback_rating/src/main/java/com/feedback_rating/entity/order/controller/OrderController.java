/**
 * 
 */
package com.feedback_rating.entity.order.controller;

import javax.ws.rs.QueryParam;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.feedback_rating.entity.email_notification.controller.EmailNotificationController;
import com.feedback_rating.entity.email_notification.utils.CommonUtils;
import com.feedback_rating.entity.order.models.Order;
import com.feedback_rating.entity.order.models.OrderDao;
import com.feedback_rating.entity.order.models.OrderKey;

/**
 * @author gurushant.j
 *
 */
@RestController
@RequestMapping("/orders")
public class OrderController {

	private static final Logger log = LoggerFactory.getLogger(OrderController.class);

	@Autowired
	CommonUtils utils;

	@Autowired
	OrderDao orderDao;
	@RequestMapping(value="/getOrderDetail",produces="application/json")
	@ResponseBody
	public Object getOrderDetail(@QueryParam("orderId") int orderId,@QueryParam("restId") int restId)
	{
		Object response=null;
		try
		{
			OrderKey key=new OrderKey(orderId,restId);
			Order orderObj=orderDao.getOrderDetail(key);
			orderObj.setMessage("Order is successfully retrieved");
			orderObj.setStatus("SUCCESS");
			response= orderObj;
		}
		catch(Exception ex)
		{
			log.error("Error occured.Stacktrace is => "+utils.getStackTrace(ex));
			response= utils.getErrorResponse("Please check the correct order Id or try again later");
		}
		return response;
	}
}
