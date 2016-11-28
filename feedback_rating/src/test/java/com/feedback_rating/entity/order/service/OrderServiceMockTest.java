/**
 * 
 */
package com.feedback_rating.entity.order.service;

import static org.assertj.core.api.Assertions.in;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.stub;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import com.feedback_rating.entity.email_notification.dao.api.EmailNotificationDaoAPI;
import com.feedback_rating.entity.email_notification.service.EmailNotificationService;
import com.feedback_rating.entity.email_notification.utils.CommonUtils;
import com.feedback_rating.entity.email_notification.utils.EmailNotifyKey;
import com.feedback_rating.entity.order.dao.api.OrderDaoAPI;
import com.feedback_rating.entity.order.dao.impl.OrderDao;
import com.feedback_rating.entity.order.models.OrderKey;

/**
 * @author gurushant.j
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class OrderServiceMockTest {
	@Mock
	OrderServiceApi orderService;
	@InjectMocks
	CommonUtils utils;
	@InjectMocks
	OrderDao orderApi;
	@InjectMocks
	EmailNotificationService emailService;
	
	public OrderServiceMockTest()
	{
		MockitoAnnotations.initMocks(this);
	}
	@Test
	public void test()
	{
		int orderId=2000;
		int restId=1;
		EmailNotifyKey emailKey=new EmailNotifyKey(orderId, restId);
		stub(orderService.isFeedbackExists(emailKey)).toReturn(false);
		Object resp=orderService.getOrderDetails(orderId, restId);
		System.out.println(resp);
		
		OrderKey key=new OrderKey(orderId,restId);
		
	}
}
