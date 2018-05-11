package com.umsl.gregoryhommert.seefoodv1.utilities

object RecipeSchema {
    const val NAME = "recipe"
    object Cols {
        const val ID = "id"
        const val TITLE = "title"
        const val IMAGE = "image"
        const val IMAGETYPE = "imageType"
        const val USEDINGREDIENTCOUNT = "usedIngredientCount"
        const val MISSEDINGREDIENTCOUNT = "missedIngredientCount"
        const val LIKES = "likes"
    }

}