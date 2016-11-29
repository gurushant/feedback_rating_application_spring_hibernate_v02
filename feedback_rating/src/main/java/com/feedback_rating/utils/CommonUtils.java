/**
 * 
 */
package com.feedback_rating.utils;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.springframework.stereotype.Service;

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
