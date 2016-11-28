package com.feedback_rating.test;

import javax.ws.rs.Produces;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class TestService {

	@RequestMapping(value="/test",
					method=RequestMethod.GET,
					produces="application/json")
	@ResponseBody
	public String test()
	{
		return "[\"gurushant\"]";
	}
}
