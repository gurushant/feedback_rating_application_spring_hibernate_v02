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

	/**
	 * This method is used to convert exception stacktrace into the string object.
	 * @param ex
	 * @return
	 */

	public String getStackTrace(Exception ex)
	{
		StringWriter wr=new  StringWriter();
		ex.printStackTrace(new PrintWriter(wr));
		return wr.toString();
	}

}
