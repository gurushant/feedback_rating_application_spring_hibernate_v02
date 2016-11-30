delete from orders;
delete from email_notification;
insert into orders (id,restaruent_id,served_date_time,order_amount,customer_email,customer_mobile,overall_order_rating,recipes_in_orders,rating_feedback_data,created_on,overall_recipe_rating)values(2000,1,now(),690,'jeevan@gmail.com','9527121842','0','["chicken handi","butter naan","milk shake"]',null,now(),0);      
insert into email_notification(order_id,restaruent_id,email_body,email_sent_time,feedback_received_time,is_email_sent,is_feedback_rating_received)values(2000,1,null,now(),null,true,false); 