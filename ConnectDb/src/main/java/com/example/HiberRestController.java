package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.hibernate_example.Key;
import com.example.hibernate_example.User;
import com.example.hibernate_example.UserDao;

@RestController
@RequestMapping("/test")
public class HiberRestController {

	@Autowired
	private UserDao userDao;
	
	@RequestMapping(value="/user",produces="application/json")
	@ResponseBody
	public User getUser()
	{
		Key k=new Key(2,2);
		return userDao.getById(k);
	}
}
