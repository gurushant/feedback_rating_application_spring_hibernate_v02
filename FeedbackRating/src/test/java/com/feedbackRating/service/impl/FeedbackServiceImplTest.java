package com.feedbackRating.service.impl;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Matchers.anyFloat;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.stub;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import com.feedbackRating.domain.FeedbackResponse;
import com.feedbackRating.feedbackService.impl.FeedbackServiceImpl;
import com.feedbackRating.persistence.dao.api.FeedbackRatingDaoAPI;

@RunWith(MockitoJUnitRunner.class)
public class FeedbackServiceImplTest {

	@Mock
	FeedbackRatingDaoAPI feedbackRatingDaoObj;
	@InjectMocks
	FeedbackServiceImpl feedbackServiceObj;


	public FeedbackServiceImplTest()
	{
		MockitoAnnotations.initMocks(this);
	}

	
	/**
	 * Following test method tests  roundUpRating method.
	 */
	@Test
	public void testRoundUp_1()
	{
		float val=feedbackServiceObj.roundUpRating(2.1F);
		float delta=0;
		System.out.println(val);
		assertEquals(2.0, val,delta);
	}
	/**
	 * Following test method tests  roundUpRating method.
	 */
	@Test
	public void testRoundUp_2()
	{
		float val=feedbackServiceObj.roundUpRating(2.8F);
		float delta=0;
		System.out.println(val);
		assertEquals(3.0, val,delta);
	}
	
	/**
	 * Unit test to check if feedback already exists in system.
	 */
	@Test
	public void testPostFeedbackAlreadyExists()
	{
		String postPayload="{"+
	"\"order_id\": \"2000\","+
	"\"restaruent_id\": \"1\","+
	"\"economy\": \"2\","+
	"\"ambience\": \"2.5\","+
	"\"qos\": \"3\","+
	"\"feedback\": \"test feedback\","+
	"\"recipe_rating\": ["+
		"\"chicken handi: 2.5\","+
		"\"butter naan: 2.5\","+
		"\"milk shake: 2.5\"]}";
		stub(feedbackRatingDaoObj.isOrderExists(anyInt(),anyInt())).toReturn(true);
		stub(feedbackRatingDaoObj.checkIsFeedbackReceived(anyInt(),anyInt())).toReturn(true);
		FeedbackResponse responseObj=feedbackServiceObj.postFeedback(postPayload);
		assertEquals("Feedback already exists for this order", responseObj.getMessage());
			
	}
	
	/**
	 * Check feedback succesfully saved in db.
	 */
	@Test
	public void testPostFeedback()
	{
		String postPayload="{"+
	"\"order_id\": \"2000\","+
	"\"restaruent_id\": \"1\","+
	"\"economy\": \"2\","+
	"\"ambience\": \"2.5\","+
	"\"qos\": \"3\","+
	"\"feedback\": \"test feedback\","+
	"\"recipe_rating\": ["+
		"\"chicken handi: 2.5\","+
		"\"butter naan: 2.5\","+
		"\"milk shake: 2.5\"]}";
		
		stub(feedbackRatingDaoObj.isOrderExists(anyInt(),anyInt())).toReturn(true);
		stub(feedbackRatingDaoObj.checkIsFeedbackReceived(anyInt(),anyInt())).toReturn(false);
		stub(feedbackRatingDaoObj.updateOrderData(any(),anyFloat(),anyFloat(),anyString(),anyInt())).toReturn(true);
		stub(feedbackRatingDaoObj.updateEmailNotification(anyInt(), anyBoolean())).toReturn(true);
		System.out.println(postPayload);
		FeedbackResponse responseObj=feedbackServiceObj.postFeedback(postPayload);
		assertEquals("Successfully posted feedback", responseObj.getMessage());
	}
	
	/**
	 * Check feedback error if updateOrderData returns false.
	 */
	@Test
	public void testPostFeedbackOrderError()
	{
		String postPayload="{"+
	"\"order_id\": \"2000\","+
	"\"restaruent_id\": \"1\","+
	"\"economy\": \"2\","+
	"\"ambience\": \"2.5\","+
	"\"qos\": \"3\","+
	"\"feedback\": \"test feedback\","+
	"\"recipe_rating\": ["+
		"\"chicken handi: 2.5\","+
		"\"butter naan: 2.5\","+
		"\"milk shake: 2.5\"]}";
		stub(feedbackRatingDaoObj.isOrderExists(anyInt(),anyInt())).toReturn(true);
		stub(feedbackRatingDaoObj.checkIsFeedbackReceived(anyInt(),anyInt())).toReturn(false);
		stub(feedbackRatingDaoObj.updateOrderData(anyString(),anyFloat(),anyFloat(),anyString(),anyInt())).toReturn(false);
		stub(feedbackRatingDaoObj.updateEmailNotification(anyInt(),anyBoolean())).toReturn(true);
		System.out.println(postPayload);
		FeedbackResponse responseObj=feedbackServiceObj.postFeedback(postPayload);
		assertEquals("Error occured.Please check whether required fields are present", responseObj.getMessage());
	}

	/**
	 * Test suppose order does not exist in system.
	 */
	@Test
	public void testPostFeedbackOrderExistance()
	{
		String postPayload="{"+
	"\"order_id\": \"2000\","+
	"\"restaruent_id\": \"1\","+
	"\"economy\": \"2\","+
	"\"ambience\": \"2.5\","+
	"\"qos\": \"3\","+
	"\"feedback\": \"test feedback\","+
	"\"recipe_rating\": ["+
		"\"chicken handi: 2.5\","+
		"\"butter naan: 2.5\","+
		"\"milk shake: 2.5\"]}";
		stub(feedbackRatingDaoObj.isOrderExists(anyInt(),anyInt())).toReturn(false);
		stub(feedbackRatingDaoObj.checkIsFeedbackReceived(anyInt(),anyInt())).toReturn(false);
		stub(feedbackRatingDaoObj.updateOrderData(anyString(),anyFloat(),anyFloat(),anyString(),anyInt())).toReturn(false);
		stub(feedbackRatingDaoObj.updateEmailNotification(anyInt(),anyBoolean())).toReturn(true);
		System.out.println(postPayload);
		FeedbackResponse responseObj=feedbackServiceObj.postFeedback(postPayload);
		assertEquals("Order does not exist in system.Please check combination of order id and restaruent id", responseObj.getMessage());
	}
	/**
	 * Check feedback error if updateEmailNotification returns false.
	 */
	@Test
	public void testPostFeedbackEmailError()
	{
		String postPayload="{"+
	"\"order_id\": \"2000\","+
	"\"restaruent_id\": \"1\","+
	"\"economy\": \"2\","+
	"\"ambience\": \"2.5\","+
	"\"qos\": \"3\","+
	"\"feedback\": \"test feedback\","+
	"\"recipe_rating\": ["+
		"\"chicken handi: 2.5\","+
		"\"butter naan: 2.5\","+
		"\"milk shake: 2.5\"]}";
		stub(feedbackRatingDaoObj.isOrderExists(anyInt(),anyInt())).toReturn(true);
		stub(feedbackRatingDaoObj.checkIsFeedbackReceived(anyInt(),anyInt())).toReturn(false);
		stub(feedbackRatingDaoObj.updateOrderData(anyString(),anyFloat(),anyFloat(),anyString(),anyInt())).toReturn(true);
		stub(feedbackRatingDaoObj.updateEmailNotification(anyInt(), anyBoolean())).toReturn(false);
		System.out.println(postPayload);
		FeedbackResponse responseObj=feedbackServiceObj.postFeedback(postPayload);
		System.out.println(">>>>>>>>>>>>>>>>>>"+responseObj.getMessage());
		assertEquals("Error occured.Please check whether required fields are present", responseObj.getMessage());
	}
	

}
