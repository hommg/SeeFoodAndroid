package com.umsl.gregoryhommert.seefoodv1.fragments

import android.app.Activity
import android.os.Bundle
import android.app.Fragment
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
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
    private lateinit var viewAdapter: IngredientsAdapter//RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager

    //MARK:- Listener
    var listener: IngredientDisplayListener? = null
    interface IngredientDisplayListener {
        fun getIngredients(): ArrayList<String>?
        fun removeIngredientAt(position: Int)
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
        listener?.getIngredients()?.let {
            this.viewAdapter = IngredientsAdapter(it)
        }
        this.viewManager = LinearLayoutManager(context)
        this.recyclerView = view.findViewById<RecyclerView>(R.id.ingredientList).apply {
            layoutManager = viewManager
            adapter = viewAdapter
        }

        val itemTouchHelperCalback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            override fun onMove(recyclerView: RecyclerView?, viewHolder: RecyclerView.ViewHolder?, target: RecyclerView.ViewHolder?): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder?, direction: Int) {
                viewHolder?.let {
                    listener?.removeIngredientAt(it.adapterPosition)
//                    viewAdapter.removeIngredientAt(it.adapterPosition)
                }
            }
        }

        ItemTouchHelper(itemTouchHelperCalback).attachToRecyclerView(this.recyclerView)

        val divider = DividerItemDecoration(this.recyclerView.context, resources.configuration.orientation)
        this.recyclerView.addItemDecoration(divider)
    }

    //MARK:- Updates
    fun dataSetChanged() {
        activity.runOnUiThread({
            viewAdapter.notifyDataSetChanged()
        })
    }



}
