/**
 * 
 */
package com.example.hibernate_example;

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
public class UserDao {

	@Autowired
	private SessionFactory _sessionFactory;
	
	private Session getSession()
	{
		return _sessionFactory.openSession();
	}
	
	public void saveUser(User user)
	{
		getSession().save(user);
		return;
	}
	
	 public User getById(int id) 
	 {
		    return (User) getSession().load(User.class, id);
	 }
	
	
}
