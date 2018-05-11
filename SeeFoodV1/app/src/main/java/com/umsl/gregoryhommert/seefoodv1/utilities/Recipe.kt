package com.umsl.gregoryhommert.seefoodv1.utilities

import org.json.JSONObject

data class Recipe(val id: Int, val title: String, val image: String, val imageType: String,
                  val usedIngredientCount: Int, val missedIngredientCount: Int, val likes: Int) {
    constructor(recipeObject: JSONObject): this(
            id = recipeObject.getInt("id"),
            title = recipeObject.getString("title"),
            image = recipeObject.getString("image"),
            imageType = recipeObject.getString("imageType"),
            usedIngredientCount = recipeObject.getInt("usedIngredientCount"),
            missedIngredientCount = recipeObject.getInt("missedIngredientCount"),
            likes = recipeObject.getInt("likes")
    )
}