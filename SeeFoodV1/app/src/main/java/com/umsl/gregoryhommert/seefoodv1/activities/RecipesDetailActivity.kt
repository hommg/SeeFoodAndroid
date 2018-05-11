package com.umsl.gregoryhommert.seefoodv1.activities

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.ConnectivityManager
import android.os.AsyncTask
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.util.Log
import com.umsl.gregoryhommert.seefoodv1.R
import com.umsl.gregoryhommert.seefoodv1.utilities.RecipeInfo
import kotlinx.android.synthetic.main.activity_recipes_detail.*
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import kotlin.concurrent.thread

class RecipesDetailActivity : Activity() {

    //MARK:- Keys
    companion object {
        private const val RECIPESDETAILACTIVITY = "RecipesDetailActivity"
        private const val EXTRA_ID = "com.umsl.gregoryhommert.seefoodv1.recipe_id"
        private const val EXTRA_TITLE_STRING = "com.umsl.gregoryhommert.seefoodv1.recipe_title"
        private const val EXTRA_URL_STRING = "com.umsl.gregoryhommert.seefoodv1.recipe_image_url"

        //Intents
        fun newIntent(context: Context, id: Int, title: String, imageUrlString: String): Intent {
            val recipesDetailIntent = Intent(context, RecipesDetailActivity::class.java)
            recipesDetailIntent.putExtra(EXTRA_ID, id)
            recipesDetailIntent.putExtra(EXTRA_TITLE_STRING, title)
            recipesDetailIntent.putExtra(EXTRA_URL_STRING, imageUrlString)
            return recipesDetailIntent
        }
    }

    //MARK:- View Lifecycle
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipes_detail)
        Log.e(RECIPESDETAILACTIVITY, "onCreate")
        downloadContent()
    }

    override fun onBackPressed() {
        finish()
//        super.onBackPressed()
    }

    //MARK:- Setup
    fun downloadContent() {
        val id = intent.getIntExtra(EXTRA_ID, 0)
        val title = intent.getStringExtra(EXTRA_TITLE_STRING)
        val urlString = intent.getStringExtra(EXTRA_URL_STRING)

        this.recipeTitle.text = title
//        this.recipeInstructions.movementMethod = ScrollingMovementMethod()

        val manager = this.getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager
        manager?.activeNetworkInfo?.let {networkInfo ->
            if (networkInfo.isConnected) {

                thread {
                    val task = DownloadImageTask()
                    val resultData = task.execute(urlString).get()
                    resultData?.let {
                        runOnUiThread {
                            this.recipeImage.setImageBitmap(resultData)
                        }
                    } ?: Log.e(RECIPESDETAILACTIVITY, "Image load unsuccessful")
                }

                thread {
                    val task = DownloadInfoTask()
                    val resultData = task.execute(id).get()
                    resultData?.let {
                        val info = RecipeInfo(it)
                        runOnUiThread {
                            this.recipeInstructions.text = info.instructions
                        }
                    } ?: Log.e(RECIPESDETAILACTIVITY, "Instruction load unsuccessful")
                }
            }
        }
    }

    //MARK:- Async Tasks
    inner class DownloadImageTask: AsyncTask<String, Unit, Bitmap?>() {
        override fun doInBackground(vararg params: String?): Bitmap? {
            if (params.isEmpty()) {
                return null
            }

            return BitmapFactory.decodeStream(URL(params[0]!!).content as InputStream)
        }
    }

    //MARK:- Async Tasks
    class DownloadInfoTask: AsyncTask<Int, Unit, JSONObject?>() {
        override fun doInBackground(vararg params: Int?): JSONObject? {
            Log.e("TAG", "START DOWNLOAD TASK")
            if (params.isEmpty()) {
                return null
            }

            val urlString = "https://spoonacular-recipe-food-nutrition-v1.p.mashape.com/recipes/${params[0]!!.toString()}/information"
            val resultString = downloadDataWith(urlString)
            if (resultString.isEmpty()) {
                return null
            } else {
                Log.e("RESULTS", "What is my data: $resultString")
                return JSONObject(resultString)
            }
        }

        fun downloadDataWith(urlString: String): String {
            val url = URL(urlString)
            val httpClient = url.openConnection() as HttpURLConnection
            httpClient.setRequestProperty("X-Mashape-Key", "")
            httpClient.setRequestProperty("X-Mashape-Host", "spoonacular-recipe-food-nutrition-v1.p.mashape.com")
            httpClient.setRequestProperty("content-type", "application/json")
//            httpClient.setRequestProperty("accept", "application/json")
            httpClient.requestMethod = "GET"
            httpClient.connect()

            var resultString = ""

            when (httpClient.responseCode) {
                200, 201 -> {
                    val inStream = httpClient.inputStream
                    val reader = BufferedReader(InputStreamReader(inStream))

                    var dataString = reader.readLine()
                    while (dataString != null) {
                        resultString += dataString
                        dataString = reader.readLine()
                    }
                    reader.close()
                    Log.e("TAG", "Parsed Data download: ${httpClient.responseCode}")
                    httpClient.disconnect()
                }
                else -> {
                    Log.e("TAG", "DISCONNECT IN ERROR: ${httpClient.responseCode}")
                    httpClient.disconnect()
                }
            }
            return resultString
        }
    }
}
