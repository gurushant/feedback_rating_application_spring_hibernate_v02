/**
 * 
 */
package com.feedback_rating.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.feedback_rating.entity.Order;
import com.feedback_rating.entity.OrderDao;
import com.feedback_rating.entity.OrderKey;

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
	public Order getOrderDetail()
	{
		OrderKey key=new OrderKey(2000,1);
		return orderDao.getOrderDetail(key);
	}
}
