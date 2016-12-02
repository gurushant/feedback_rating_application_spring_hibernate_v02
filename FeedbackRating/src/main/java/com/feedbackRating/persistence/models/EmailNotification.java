/**
 * 
 */
package com.feedbackRating.persistence.models;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


/**
 * @author gurushant.j
 *
 */
@Entity
@Table(name="email_notification")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class EmailNotification implements Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id")
	private int id;
	
	@OneToOne
	@JoinColumn(name="order_line_id")
	private Order order;
	
	@Type(type= "org.hibernate.type.NumericBooleanType")
	@Column(name="is_email_sent")
	private Boolean isEmailSent;
	@Column(name="feedback_received_time")
	private Date feedbackReceivedTime;
	@Column(name="email_body")
	private String emailBody;
	@Column(name="email_sent_time")
	private Date emailSentTime;
	
	@Type(type= "org.hibernate.type.NumericBooleanType")
	@Column(name="is_feedback_rating_received")
	private Boolean isFeedbackExists;
	
	public boolean isEmailSent() {
		return isEmailSent;
	}
	public void setEmailSent(boolean isEmailSent) {
		this.isEmailSent = isEmailSent;
	}
	public Date getFeedbackReceivedTime() {
		return feedbackReceivedTime;
	}
	public void setFeedbackReceivedTime(Date feedbackReceivedTime) {
		this.feedbackReceivedTime = feedbackReceivedTime;
	}
	public String getEmailBody() {
		return emailBody;
	}
	public void setEmailBody(String emailBody) {
		this.emailBody = emailBody;
	}
	public Date getEmailSentTime() {
		return emailSentTime;
	}
	public void setEmailSentTime(Date emailSentTime) {
		this.emailSentTime = emailSentTime;
	}
	public boolean isFeedbackExists() {
		return isFeedbackExists;
	}
	public void setFeedbackExists(boolean isFeedbackExists) {
		this.isFeedbackExists = isFeedbackExists;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Order getOrder() {
		return order;
	}
	public void setOrder(Order order) {
		this.order = order;
	}
	
}
