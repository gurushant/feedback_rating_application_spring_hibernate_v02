package com.feedback_rating.feedback_service.impl;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import com.feedbackRating.feedback_service.impl.FeedbackServiceImpl;
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
}
