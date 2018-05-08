package com.umsl.gregoryhommert.seefoodv1

import com.umsl.gregoryhommert.seefoodv1.utilities.Recipe

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
        return input.filter { !it.isLetter() }.isEmpty()
    }

    //MARK:- Mutations
    fun addIngredient(ingredient: String) {
        this.ingredients.add(ingredient)
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

//    fun removeIngredientAt(index: Int) {
//        this.ingredients.removeAt(index)
//        listener?.dataUpdated()
//    }

    fun getRecipes() {
        this.recipes.clear()

        //TODO --> api GET query

        listener?.recipesReceived()
    }

    fun recipeAt(index: Int): Recipe {
        return this.recipes[index]
    }
}