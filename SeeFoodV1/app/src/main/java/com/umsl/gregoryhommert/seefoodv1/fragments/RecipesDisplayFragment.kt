package com.umsl.gregoryhommert.seefoodv1.fragments

import android.os.Bundle
import android.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.content.Context
import android.graphics.Bitmap
import android.widget.TextView
import com.umsl.gregoryhommert.seefoodv1.R
import android.net.ConnectivityManager
import android.os.AsyncTask
import android.support.v7.widget.CardView
import android.widget.ImageView
import java.io.InputStream
import java.net.URL
import android.graphics.BitmapFactory
import com.umsl.gregoryhommert.seefoodv1.utilities.Recipe

class RecipesDisplayFragment : Fragment() {
    //MARK:- Keys
    companion object {
        private const val RECIPES_DISPLAY_FRAGMENT = "RecipesDisplayFragment"
    }

    //MARK:- Vars
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager

    //MARK:- Listener
    var listener: RecipesDisplayListener? = null
    interface RecipesDisplayListener {
        fun getRecipeDetails(index: Int)
        fun getRecipes(): ArrayList<Recipe>?
    }

    //MARK:- Fragment Lifecycle
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_recipe_display, container, false)
        setUp(view)
        return view
    }

    //MARK:- Setup
    private fun setUp(view: View) {
        listener?.getRecipes()?.let {
            this.viewAdapter = RecipesAdapter()
        }
        this.viewManager = LinearLayoutManager(context)

        this.recyclerView = view.findViewById<RecyclerView>(R.id.recipeList).apply {
            layoutManager = viewManager
            adapter = viewAdapter
        }
    }

    //MARK:- Updates
//    fun dataSetChanged() {
//        activity.runOnUiThread({
//            viewAdapter.notifyDataSetChanged()
//        })
//    }

    //MARK:- Adapter/ViewHolder
    inner class RecipesAdapter() : RecyclerView.Adapter<ViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(activity)
                    .inflate(R.layout.recipes_item_layout, parent, false) as CardView
            return ViewHolder(view)
        }

        override fun getItemCount(): Int {
            listener?.getRecipes()?.let {
                return it.size
            } ?: return 0
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            listener?.getRecipes()?.let {
                holder.setInfo(it[position])
            }
        }
    }

    inner class ViewHolder(view: View?) : RecyclerView.ViewHolder(view), View.OnClickListener {
        private val textView: TextView? = view?.findViewById(R.id.recipeCellTitle)
        private val imageView: ImageView? = view?.findViewById(R.id.recipeCellImage)

        init {
            view?.setOnClickListener(this)
        }

        fun setInfo(recipe: Recipe) {
            this.textView?.text = recipe.title
            val manager = context?.getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager
            manager?.activeNetworkInfo?.let {networkInfo ->
                if (networkInfo.isConnected) {
                    val task = DownloadImageTask()
                    val resultData = task.execute(recipe.image).get()

                    resultData?.let {
                        this.imageView?.setImageBitmap(resultData)
                    } ?: Log.e(RECIPES_DISPLAY_FRAGMENT, "Image load unsuccessful")
                }
            }
        }

        override fun onClick(v: View?) {
            Log.e(RECIPES_DISPLAY_FRAGMENT, "adapterPosition = ${adapterPosition}")
            listener?.getRecipeDetails(adapterPosition)
        }

        inner class DownloadImageTask: AsyncTask<String, Unit, Bitmap>() {
            override fun doInBackground(vararg params: String?): Bitmap? {
                if (params.isEmpty()) {
                    return null
                }

                return BitmapFactory.decodeStream(URL(params[0]!!).content as InputStream)
            }
        }
    }
}
