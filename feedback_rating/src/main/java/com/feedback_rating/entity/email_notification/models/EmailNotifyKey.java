/**
 * 
 */
package com.feedback_rating.entity.email_notification.models;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * @author gurushant.j
 *
 */
@Embeddable
public class EmailNotifyKey implements Serializable{
    private static final long serialVersionUID = 1L;

	@Column(name="order_id")
	private Integer id;
	
	@Column(name="restaruent_id")
	private Integer restId;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getRestId() {
		return restId;
	}
	public void setRestId(Integer restId) {
		this.restId = restId;
	}
	public EmailNotifyKey(){
		
	}
	public EmailNotifyKey(Integer id,Integer restId)
	{
		this.id=id;
		this.restId=restId;
	}
	
	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		EmailNotifyKey orderKey=(EmailNotifyKey)obj;
		if(getId()==orderKey.getId() && getRestId()==orderKey.getRestId())
		{
			return true;
		}
		else
			return false;
	}
	
	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return getId().hashCode()+getRestId().hashCode();
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return getId()+","+getRestId();
	}
	
}
