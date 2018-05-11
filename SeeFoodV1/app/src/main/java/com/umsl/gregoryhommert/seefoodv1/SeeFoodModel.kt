package com.umsl.gregoryhommert.seefoodv1

import android.util.Log
import com.umsl.gregoryhommert.seefoodv1.utilities.Recipe
import org.json.JSONArray


class SeeFoodModel {
    //MARK:- Vars
    var ingredients: ArrayList<String>
        private set
    var recipes: ArrayList<Recipe>
        private set

    //MARK:- Listener
    var listener: SeeFoodModelListener? = null
    interface SeeFoodModelListener {
        fun dataUpdated()
        fun recipesReceived()
    }

    //MARK:- Setup
    init {
        this.ingredients = ArrayList<String>()
        this.recipes = ArrayList<Recipe>()
    }

    //MARK:- Validations
    fun isValidInput(input: String): Boolean {
        return input.filter { !it.isLetter() && it != ' ' }.isEmpty()
    }

    //MARK:- Getters
    fun getPathString(): String {
        //MARK:- Instantiate Path
        var path = "https://spoonacular-recipe-food-nutrition-v1.p.mashape.com/recipes/findByIngredients?number=20&ingredients="

        //MARK:- Append Path Components
        for (ingredient in ingredients) {
            if (ingredient.contains( " ")) {
                val temp = ingredient.replace(" ", "%20")
                path += temp + "%2C"
            } else {
                path += ingredient + "%2C"
            }
        }
        Log.e("MODEL", "MODEL: path = ${path}")
        return path
    }

    //MARK:- Mutations
    fun addIngredient(ingredient: String) {
        this.ingredients.add(ingredient.toLowerCase())
        listener?.dataUpdated()
    }

    fun addIngredients(ingredients: ArrayList<String>) {
        this.ingredients.addAll(ingredients)
        listener?.dataUpdated()
    }

    fun clearIngredients() {
        this.ingredients.clear()
        listener?.dataUpdated()
    }

    fun removeIngredientAt(index: Int) {
        this.ingredients.removeAt(index)
        Log.e("MODEL", "MODEL: ingredients = ${ingredients}")
        listener?.dataUpdated()
    }

    fun setRecipes(recipesData: JSONArray) {
        this.recipes.clear()
        for (i in 0 until recipesData.length()) {
            val recipe = Recipe(recipesData.getJSONObject(i))
            recipes.add(recipe)
        }
        Log.e("MODEL", "MODEL: recipes = ${recipes}")
        listener?.recipesReceived()
    }
}