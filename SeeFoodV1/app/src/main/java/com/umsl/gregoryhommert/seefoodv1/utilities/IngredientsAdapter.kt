package com.umsl.gregoryhommert.seefoodv1.utilities

import android.support.v7.widget.RecyclerView
import android.text.SpannableString
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.umsl.gregoryhommert.seefoodv1.R

class IngredientsAdapter(private val ingredients: ArrayList<String>) : RecyclerView.Adapter<IngredientsAdapter.ViewHolder>() {

    class ViewHolder(val textView: TextView) : RecyclerView.ViewHolder(textView)

    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): IngredientsAdapter.ViewHolder {
        val textView = LayoutInflater.from(parent.context)
                .inflate(R.layout.ingredients_list_simple, parent, false) as TextView
        return ViewHolder(textView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.textView.text = ingredients[position]
    }

    override fun getItemCount() = ingredients.size

//    fun removeIngredientAt(position: Int) {
//        ingredients.removeAt(position)
//        notifyItemRemoved(position)
//    }
}