/**
 * 
 */
package com.feedback_rating.entity.order.dao.api;

import com.feedback_rating.entity.order.models.Order;
import com.feedback_rating.entity.order.models.OrderKey;

/**
 * @author gurushant.j
 *
 */
public interface OrderDaoAPI {
	public Order getOrderDetail(OrderKey key);
	public boolean updateOrderData(String feedback,float overallOrderRating,float overallRecipeRating,
			String jsonRatingData,OrderKey key);
	

	
}
