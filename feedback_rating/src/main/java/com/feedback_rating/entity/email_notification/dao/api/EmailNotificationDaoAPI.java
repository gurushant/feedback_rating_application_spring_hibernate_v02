package com.feedback_rating.entity.email_notification.dao.api;

import com.feedback_rating.entity.email_notification.model.EmailNotification;
import com.feedback_rating.entity.email_notification.utils.EmailNotifyKey;

public interface EmailNotificationDaoAPI {
	public boolean checkIsFeedbackReceived(EmailNotifyKey key);
	public EmailNotification getEmailDetail(EmailNotifyKey key);
	public boolean updateEmailNotification(EmailNotifyKey key,boolean isFeedbackReceived);

}
