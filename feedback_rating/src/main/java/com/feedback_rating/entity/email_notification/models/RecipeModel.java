/**
 * 
 */
package com.feedback_rating.entity.email_notification.models;

/**
 * @author gurushant.j
 *
 */
public class RecipeModel
{
	private String recipeName;
	private Float recipeRating;
	public String getRecipeName() {
		return recipeName;
	}
	public void setRecipeName(String recipeName) {
		this.recipeName = recipeName;
	}
	public Float getRecipeRating() {
		return recipeRating;
	}
	public void setRecipeRating(Float recipeRating) {
		this.recipeRating = recipeRating;
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return getRecipeName()+","+getRecipeRating();
	}
}
