package com.feedback_rating.entity.order.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.feedback_rating.controller.OrderController;


@RunWith(SpringJUnit4ClassRunner.class)
@WebMvcTest(OrderController.class)
public class OrderControllerTest {

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

	
    
	 @Test
	    public void testSayHelloWorld() throws Exception {
		  MvcResult mvc=mockMvc.perform(MockMvcRequestBuilders
		         .get("/orders/getOrderDetaill?orderId=2000&restId=2").accept(MediaType.APPLICATION_JSON))
				  .andReturn();
		  		
		  System.out.println("============>"+mvc.getResponse().getContentAsString());
		
	    }


}
