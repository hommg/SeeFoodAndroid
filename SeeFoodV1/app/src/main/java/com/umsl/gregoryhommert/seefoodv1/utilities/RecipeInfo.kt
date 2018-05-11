package com.umsl.gregoryhommert.seefoodv1.utilities

import org.json.JSONObject

data class RecipeInfo(val instructions: String, val healthScore: Int, val readyInMinutes: Int) {
    constructor(recipeInfoObject: JSONObject): this(
            instructions = recipeInfoObject.getString("instructions"),
            healthScore = recipeInfoObject.getInt("healthScore"),
            readyInMinutes = recipeInfoObject.getInt("readyInMinutes")
    )
}