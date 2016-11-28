/**
 * 
 */
package com.feedback_rating.entity.order.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author gurushant.j
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=OrderServiceContext.class)
public class OrderServiceTest {

	@Autowired
	OrderService orderService;
	@Test
	public void fun()
	{
		System.out.println("in fun");
	}
}


@Configuration
@ComponentScan(basePackages={"com.feedback_rating",
							 "com.feedback_rating.entity.email_notification.dao.api",
							 "com.feedback_rating.entity.email_notification.dao.impl",
							 "com.feedback_rating.entity.email_notification.domain",
							 "com.feedback_rating.entity.email_notification.model",
							 "com.feedback_rating.entity.email_notification.service",
							 "com.feedback_rating.entity.email_notification.utils",
							 "com.feedback_rating.entity.order.dao.api",
							 "com.feedback_rating.entity.order.dao.impl",
							 "com.feedback_rating.entity.order.models",
							 "com.feedback_rating.entity.order.service"
							 })
class OrderServiceContext
{
	
}

