/**
 * 
 */
package com.feedback_rating.entity.email_notification.utils;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.DecimalFormat;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.feedback_rating.entity.email_notification.models.ResponseModel;

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
	    DecimalFormat df = new DecimalFormat("0.0");
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

	public ResponseModel getErrorResponse(String message)
	{
		ResponseModel respModel=new ResponseModel();
		respModel.setMessage(message);
		respModel.setStatus("ERROR");
		return respModel;
	}
	
	public ResponseModel getSucessResponse(String message)
	{
		ResponseModel respModel=new ResponseModel();
		respModel.setMessage(message);
		respModel.setStatus("SUCCESS");
		return respModel;
	}
}
