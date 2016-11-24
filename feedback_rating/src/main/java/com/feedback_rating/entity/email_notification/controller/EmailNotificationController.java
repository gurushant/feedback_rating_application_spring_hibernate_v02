/**
 * 
 */
package com.feedback_rating.entity.email_notification.controller;

import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.feedback_rating.entity.email_notification.models.EmailNotificationDao;
import com.feedback_rating.entity.email_notification.models.EmailNotificationObjectModel;
import com.feedback_rating.entity.email_notification.models.EmailNotifyKey;
import com.feedback_rating.entity.email_notification.models.ResponseModel;
import com.feedback_rating.entity.email_notification.utils.CommonUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * @author gurushant.j
 *
 */
@RestController
@RequestMapping("/review")
@Transactional
public class EmailNotificationController {

	private static final Logger log = LoggerFactory.getLogger(EmailNotificationController.class);

	

	@Autowired
	CommonUtils utils;

		
	
}
