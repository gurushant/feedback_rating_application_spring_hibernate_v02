/**
 * 
 */
package com.feedback_rating.entity.order.controller;

import javax.ws.rs.QueryParam;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.feedback_rating.entity.order.Order;
import com.feedback_rating.entity.order.OrderDao;
import com.feedback_rating.entity.order.OrderKey;

/**
 * @author gurushant.j
 *
 */
@RestController
@RequestMapping("/orders")
public class OrderController {
	
	@Autowired
	OrderDao orderDao;
	@RequestMapping(value="/getOrderDetail",produces="application/json")
	@ResponseBody
	public Order getOrderDetail(@QueryParam("orderId") int orderId,@QueryParam("restId") int restId)
	{
		OrderKey key=new OrderKey(orderId,restId);
		return orderDao.getOrderDetail(key);
	}
}
