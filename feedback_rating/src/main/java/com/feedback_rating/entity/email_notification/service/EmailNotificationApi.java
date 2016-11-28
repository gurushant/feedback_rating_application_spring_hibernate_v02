/**
 * 
 */
package com.feedback_rating.entity.email_notification.service;

import com.feedback_rating.entity.email_notification.utils.EmailNotifyKey;
import com.feedback_rating.entity.email_notification.utils.ResponseModel;

/**
 * @author gurushant.j
 *
 */
public interface EmailNotificationApi {
	public ResponseModel postFeedback(String postPayload);
	public boolean checkIsFeedbackReceived(EmailNotifyKey key);
}
