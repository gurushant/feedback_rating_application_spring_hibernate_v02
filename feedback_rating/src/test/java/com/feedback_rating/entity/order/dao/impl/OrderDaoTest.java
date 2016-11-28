package com.feedback_rating.entity.order.dao.impl;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.stub;

import java.util.Date;

import javax.inject.Inject;

import org.hibernate.SessionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.feedback_rating.entity.email_notification.service.EmailNotificationApi;
import com.feedback_rating.entity.email_notification.utils.CommonUtils;
import com.feedback_rating.entity.email_notification.utils.EmailNotifyKey;
import com.feedback_rating.entity.order.dao.api.OrderDaoAPI;
import com.feedback_rating.entity.order.models.Order;
import com.feedback_rating.entity.order.models.OrderKey;
import com.feedback_rating.entity.order.service.OrderServiceApi;

@RunWith(MockitoJUnitRunner.class)
public class OrderDaoTest {
	
	@Mock
	OrderDaoAPI orderDaoApi;

	@Mock
	OrderServiceApi orderServiceApi;
	
	@InjectMocks
	CommonUtils utils;
	
	@Mock
	EmailNotificationApi emailNotificationApi;
	
	@Inject
	SessionFactory _sessionFactory;
	
	
	@Test
	public void test()
	{
		OrderKey key=new OrderKey(2000, 1);
		Order orderObj=new Order();
		orderObj.setKey(key);
		orderObj.setServedDateTime(new Date());
		orderObj.setOrderAmount(200);
		orderObj.setCustEmail("gurushant033@gmail.com");
		orderObj.setCustMobile("9767976808");
		orderObj.setRecipeJson("[\"chicken handi\",\"butter naan\",\"milk shake\"]");
		orderObj.setRatingFeedback(null);
		orderObj.setCreatedOn(new Date());
		orderObj.setRecipeRating(0F);
		orderObj.setOrderRating(0F);
		orderObj.setFeedback(null);
		
		EmailNotifyKey emailKey=new EmailNotifyKey(2000, 1);
		stub(orderDaoApi.getOrderDetail(key)).toReturn(orderObj);
		stub(orderServiceApi.isFeedbackExists(emailKey)).toReturn(false);

		Order order=orderServiceApi.returnOrder(key);
		
	}
}
