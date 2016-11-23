package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.hibernate_example.User;
import com.example.hibernate_example.UserDao;

@RestController
@RequestMapping("/test")
public class HiberRestController {

	@Autowired
	private UserDao userDao;
	
	@RequestMapping("/user")
	public String getUser()
	{
		return userDao.getById(1).getName();
	}
}
