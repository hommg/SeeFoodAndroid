package com.umsl.gregoryhommert.seefoodv1.activities

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.umsl.gregoryhommert.seefoodv1.R
import com.umsl.gregoryhommert.seefoodv1.SeeFoodModel
import com.umsl.gregoryhommert.seefoodv1.fragments.RecipesDisplayFragment
import com.umsl.gregoryhommert.seefoodv1.utilities.ModelHolder
import com.umsl.gregoryhommert.seefoodv1.utilities.Recipe

class RecipesActivity : Activity(), RecipesDisplayFragment.RecipesDisplayListener {

    //MARK:- Keys
    companion object {
        private const val RECIPESACTIVITY = "RecipesActivity"
        private const val RECIPES_DISPLAY_FRAG_TAG = "RecipesDisplayFragment"
        private const val MODEL_KEY = "Model"

        //Intents
        fun newIntent(context: Context): Intent {
            val recipesIntent = Intent(context, RecipesActivity::class.java)
            return recipesIntent
        }
    }

    //MARK:- Vars
    private var model: SeeFoodModel? = null
    private var recipesDisplayFragment: RecipesDisplayFragment? = null

    //MARK:- Acticity Lifecycle
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipes)

        //Mark:- Inits
        setupModel()
        buildFragments()
    }

    override fun onBackPressed() {
        finish()
//        super.onBackPressed()
    }

    //MARK:- Setup
    private fun setupModel() {
        model = (ModelHolder.instance.getModel(MODEL_KEY)
                as? SeeFoodModel)?.let { it } ?: SeeFoodModel()
        Log.e(RECIPESACTIVITY, "recipes received are: ${model?.recipes}")
    }


    private fun buildFragments() {
        val manager = fragmentManager
        recipesDisplayFragment = manager.findFragmentByTag(RECIPES_DISPLAY_FRAG_TAG) as? RecipesDisplayFragment
        if (recipesDisplayFragment == null) {
            recipesDisplayFragment = RecipesDisplayFragment()
            manager.beginTransaction()
                    .add(R.id.recipeDisplayContainer, recipesDisplayFragment, RECIPES_DISPLAY_FRAG_TAG)
                    .commit()
        }
        recipesDisplayFragment?.listener = this
    }

    //MARK:- RecipesDisplayListener
    override fun getRecipeDetails(index: Int) {
        val recipe = model?.recipes?.elementAt(index)
        recipe?.let {
            val intent = RecipesDetailActivity.newIntent(this@RecipesActivity, it.id, it.title, it.image)
            Log.e(RECIPESACTIVITY, "starting RecipiesDetailActivity")
            startActivity(intent)
        }
    }

    override fun getRecipes(): ArrayList<Recipe>? {
        return model?.recipes
    }
}
