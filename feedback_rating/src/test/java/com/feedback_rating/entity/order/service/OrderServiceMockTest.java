/**
 * 
 */
package com.feedback_rating.entity.order.service;

import static org.assertj.core.api.Assertions.in;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.stub;

import java.util.Date;

import javax.inject.Inject;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import com.feedback_rating.entity.email_notification.dao.api.EmailNotificationDaoAPI;
import com.feedback_rating.entity.email_notification.service.EmailNotificationApi;
import com.feedback_rating.entity.email_notification.service.EmailNotificationService;
import com.feedback_rating.entity.email_notification.utils.CommonUtils;
import com.feedback_rating.entity.email_notification.utils.EmailNotifyKey;
import com.feedback_rating.entity.order.dao.api.OrderDaoAPI;
import com.feedback_rating.entity.order.dao.impl.OrderDao;
import com.feedback_rating.entity.order.models.Order;
import com.feedback_rating.entity.order.models.OrderKey;

/**
 * @author gurushant.j
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class OrderServiceMockTest {
	
	@Mock
	CommonUtils utils;
	
	@Mock
	OrderDaoAPI orderDaoApi;
	
	@Mock 
	EmailNotificationApi emailDaoObj;
	
	@InjectMocks
	OrderService orderServiceObj;
	
	public OrderServiceMockTest()
	{
		MockitoAnnotations.initMocks(this);
	}
	int orderId=2000;
	int restId=1;

	private OrderKey orderKey=null;
	private Order orderObj=null;
	private EmailNotifyKey emailKey=null;
	@Before
	public void setUp()
	{
		orderKey=new OrderKey(orderId, restId);
		emailKey=new EmailNotifyKey(orderId, restId);
		orderObj=new Order();
		orderObj.setKey(orderKey);
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

		stub(orderDaoApi.getOrderDetail(any())).toReturn(orderObj);
		stub(emailDaoObj.checkIsFeedbackReceived(any())).toReturn(false);

	}
	@Test
	public void test()
	{
		Object returnedOrder=orderServiceObj.getOrderDetails(orderId,restId);
		if(returnedOrder instanceof Order )
		{
			Order tempOrder=(Order)returnedOrder;
			assertEquals(orderObj.getKey(), tempOrder.getKey());
		}
		else
		{
			throw new Error("Error occured");
		}
	}
}

