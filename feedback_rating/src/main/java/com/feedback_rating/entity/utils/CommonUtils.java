/**
 * 
 */
package com.feedback_rating.entity.utils;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

import org.springframework.stereotype.Service;

import com.feedback_rating.domain.FeedbackResponseModel;

/**
 * @author gurushant.j
 *
 */
@Service
public class CommonUtils {
	
	
	public float extractRating(List<Object> recipeList)
	{
		float recipeRating=0;
		for(Object recipe:recipeList)
		{
			String tempRecipe=recipe.toString();
			recipeRating+=Float.parseFloat(tempRecipe.split(":")[1]);
		}
		return recipeRating;
	}
	
	
	public float roundUpRating(float rating)
	{
	    rating=(float) (rating/0.5);
	    rating=Math.round(rating);
	    return rating/2;

	}
	
	public String getStackTrace(Exception ex)
	{
		StringWriter wr=new  StringWriter();
		ex.printStackTrace(new PrintWriter(wr));
		return wr.toString();
	}

	public FeedbackResponseModel getErrorResponse(String message)
	{
		FeedbackResponseModel respModel=new FeedbackResponseModel();
		respModel.setMessage(message);
		respModel.setStatus("ERROR");
		return respModel;
	}
	
	public FeedbackResponseModel getSucessResponse(String message)
	{
		FeedbackResponseModel respModel=new FeedbackResponseModel();
		respModel.setMessage(message);
		respModel.setStatus("SUCCESS");
		return respModel;
	}
}
