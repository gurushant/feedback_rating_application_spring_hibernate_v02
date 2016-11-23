/**
 * 
 */
package com.feedback_rating.entity;

import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


/**
 * @author gurushant.j
 *
 */

@Repository
@Transactional
public class OrderDao {

	@Autowired
	private SessionFactory _sessionFactory;
	
	private Session getSession()
	{
		return _sessionFactory.openSession();
	}
	
	public Order getOrderDetail(OrderKey key)
	{
		return (Order) getSession().load(Order.class, key);
	}
}
