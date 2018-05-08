package com.umsl.gregoryhommert.seefoodv1.fragments

import android.app.Activity
import android.os.Bundle
import android.app.Fragment
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.umsl.gregoryhommert.seefoodv1.R
import com.umsl.gregoryhommert.seefoodv1.utilities.IngredientsAdapter

class IngredientDisplayFragment : Fragment() {

    //MARK:- Keys
    companion object {
        private const val INGREDIENT_DISPLAY_FRAGMENT = "IngredientDisplayFragment"
    }

    //MARK:- Vars
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager
//    private var ingredients = ArrayList<String>()

    //MARK:- Listener
    var listener: IngredientDisplayListener? = null
    interface IngredientDisplayListener {
        fun getIngredients(): ArrayList<String>?
    }

    //MARK:- Fragment Lifecycle
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_ingredient_display, container, false)
        setUp(view)
        return view
    }

    //MARK:- Setup
    private fun setUp(view: View) {
//        this.ingredients = ArrayList<String>()
        listener?.getIngredients()?.let {
//            this.ingredients.addAll(it)
            this.viewAdapter = IngredientsAdapter(it)
        }
        this.viewManager = LinearLayoutManager(context)
//        this.viewAdapter = IngredientsAdapter(this.ingredients)
        this.recyclerView = view.findViewById<RecyclerView>(R.id.ingredientList).apply {
            layoutManager = viewManager
            adapter = viewAdapter
        }

        val divider = DividerItemDecoration(this.recyclerView.context, resources.configuration.orientation)
        this.recyclerView.addItemDecoration(divider)
    }

    //MARK:- Get/Set


    //MARK:- Updates
    fun dataSetChanged() {
        activity.runOnUiThread({
            viewAdapter.notifyDataSetChanged()
//            recyclerView.adapter.notifyDataSetChanged()
        })
    }


}
