package com.umsl.gregoryhommert.seefoodv1.utilities

data class Recipe(val id: Int, val title: String, val image: String, val imageType: String,
                  val usedIngredientCount: Int, val missedIngredientCount: Int, val likes: Int)