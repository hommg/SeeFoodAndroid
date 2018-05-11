package com.umsl.gregoryhommert.seefoodv1.activities

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.drawable.ColorDrawable
import android.net.ConnectivityManager
import android.os.AsyncTask
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.text.InputType
import android.util.Log
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import com.ibm.watson.developer_cloud.android.library.camera.CameraHelper
import com.ibm.watson.developer_cloud.android.library.camera.GalleryHelper
import com.ibm.watson.developer_cloud.visual_recognition.v3.VisualRecognition
import com.ibm.watson.developer_cloud.visual_recognition.v3.model.ClassifyOptions
import com.umsl.gregoryhommert.seefoodv1.R
import com.umsl.gregoryhommert.seefoodv1.SeeFoodModel
import com.umsl.gregoryhommert.seefoodv1.fragments.IngredientDisplayFragment
import com.umsl.gregoryhommert.seefoodv1.utilities.FoodList
import com.umsl.gregoryhommert.seefoodv1.utilities.ModelHolder
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONArray
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity(), SeeFoodModel.SeeFoodModelListener,
        IngredientDisplayFragment.IngredientDisplayListener {

    //MARK:- Keys
    companion object {
        private const val MAINACTIVITY = "MainActivity"
        private const val INGREDIENT_DISPLAY_FRAG_TAG = "IngredientDisplayFragment"
        private const val MODEL_KEY = "Model"
    }

    //MARK:- Vars
    private var model: SeeFoodModel? = null
    private var ingredientDisplayFragment: IngredientDisplayFragment? = null
    private var cameraHelper: CameraHelper? = null
//    private var galleryHelper: GalleryHelper? = null
    private var vrClient: VisualRecognition? = null

    //MARK:- View Lifecycle
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Mark:- Inits
        setupModel()
        initHelpers()
        buildFragments()
        bindButtons()
    }

    //MARK:- Setup
    private fun setupModel() {
        model = (ModelHolder.instance.getModel(MODEL_KEY)
                as? SeeFoodModel)?.let { it } ?: SeeFoodModel()
        model?.listener = this
    }

    private fun initHelpers() {
        this.cameraHelper = CameraHelper(this)
//        this.galleryHelper = GalleryHelper(this)
        this.vrClient = VisualRecognition("2018-02-17")//2016-05-20
        this.vrClient?.setApiKey("")
    }

    private fun buildFragments() {
        //MARK:- IngredientDisplayFragment
        val manager = fragmentManager
        ingredientDisplayFragment = manager.findFragmentByTag(INGREDIENT_DISPLAY_FRAG_TAG) as? IngredientDisplayFragment
        if (ingredientDisplayFragment == null) {
            ingredientDisplayFragment = IngredientDisplayFragment()
            manager.beginTransaction()
                    .add(R.id.ingredientDisplayContainer, ingredientDisplayFragment, INGREDIENT_DISPLAY_FRAG_TAG)
                    .commit()
        }
        ingredientDisplayFragment?.listener = this
    }

    private fun bindButtons() {
        this.clearButton.setOnClickListener {
            model?.clearIngredients()
        }
        this.getButton.setOnClickListener {
            startDownloadTask()
        }
        this.addButton.setOnClickListener {
            displayAddOptions()
        }
    }

//    //MARK:- Permissions
//    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
//        if (requestCode == CameraHelper.REQUEST_PERMISSION) {
//            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                takePhoto()
//            }
//        }
//    }

    //MARK:- Results
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == CameraHelper.REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
            Log.e(MAINACTIVITY, "${MAINACTIVITY} RESULT_OK")

            this.cameraHelper?.let {
                val photo = it.getFile(resultCode)
                if (!photo.exists()) {
                    return
                }

                val alert = AlertDialog.Builder(this)
                alert.setMessage("Loading...")
                alert.setCancelable(false)
                val dialog = alert.create()
                dialog.show()
                AsyncTask.execute({
                    this.vrClient?.let {
                        val response = it.classify(ClassifyOptions.Builder().imagesFile(photo).build()).execute()
                        val classification = response.images[0]
                        val classifier = classification.classifiers[0]

                        val ingredients = ArrayList<String>()
                        for (responseClass in classifier.classes) {
//                            Log.e(MAINACTIVITY, "${MAINACTIVITY} className: ${responseClass.className.toLowerCase()}")
                            if (FoodList().items.contains(responseClass.className.toLowerCase())) {
                                ingredients.add(responseClass.className.toLowerCase())
                            }
                        }

                        this@MainActivity.runOnUiThread({
                            dialog.dismiss()
                            model?.addIngredients(ingredients)
                        })
                    }
                })
            }
        }
    }

    //MARK:- Alerts
    private fun displayAddOptions() {
        val options = arrayOf(getString(R.string.type_ingredient_text), getString(R.string.take_photo_text),
                getString(R.string.cancel_text))    //getString(R.string.select_photo_text)
        val alert = AlertDialog.Builder(this)
        alert.setTitle(getString(R.string.options_title_text))
        alert.setItems(options, { dialog, which ->
            when {
                options[which] == getString(R.string.type_ingredient_text) -> {
                    val handler = Handler()
                    handler.postDelayed({
                        enterText()
                    }, 200)
                }
                options[which] == getString(R.string.take_photo_text) -> takePhoto()
//                options[which] == getString(R.string.select_photo_text) -> selectPhoto()
                options[which] == getString(R.string.cancel_text) -> dialog.cancel()
            }
        })

        alert.setCancelable(false)
        val dialog = alert.create()
        val optionsList = dialog.listView
        optionsList.divider = ColorDrawable(getColor(R.color.lightGray))
        optionsList.dividerHeight = 2
        dialog.show()
    }

    private fun enterText() {
        val alert = AlertDialog.Builder(this)
        alert.setTitle(getString(R.string.enter_ingredient_text))
        val input = EditText(this)
        input.inputType = InputType.TYPE_CLASS_TEXT
        alert.setView(input)
        alert.setNegativeButton(getString(R.string.cancel_text)) { dialog, which ->
            dialog.cancel()
        }
        alert.setCancelable(false)

        val dialog = alert.create()
        dialog.show()
        input.setOnEditorActionListener { v, actionId, event ->
            when(actionId) {
                EditorInfo.IME_ACTION_DONE -> {
                    val handler = Handler()
                    handler.postDelayed({
                        dialog.dismiss()
                        model?.takeIf { it.isValidInput(input.text.toString()) }?.apply {
                            this.addIngredient(input.text.toString())
                        } ?: Toast.makeText(this, getString(R.string.invalid_input_text), Toast.LENGTH_SHORT).show()
                    }, 400)
                    val methodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    methodManager.hideSoftInputFromWindow(input.windowToken, 0)
                    true
                }
                else -> false
            }
        }
    }

    //MARK:- Helpers
    private fun takePhoto() {
        this.cameraHelper?.dispatchTakePictureIntent()
    }

//    private fun selectPhoto() {
//        this.galleryHelper?.dispatchGalleryIntent()
//    }

    //MARK:- SeeFoodModelListener
    override fun dataUpdated() {
        ingredientDisplayFragment?.dataSetChanged()
    }

    override fun recipesReceived() {
        ModelHolder.instance.saveModel(MODEL_KEY, model)
        val intent = RecipesActivity.newIntent(this@MainActivity)
        startActivity(intent)
    }

    //MARK:- IngredientDisplayListener
    override fun getIngredients(): ArrayList<String>? {
        return model?.ingredients
    }


    override fun removeIngredientAt(position: Int) {
        model?.removeIngredientAt(position)
    }

    //MARK:- Downloads
    private fun startDownloadTask() {
        val alert = AlertDialog.Builder(this)
        alert.setMessage("Loading...")
        alert.setCancelable(false)
        val dialog = alert.create()
        dialog.show()

        val manager = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        manager.activeNetworkInfo?.let {networkInfo ->
            if (networkInfo.isConnected) {
                thread {
                    model?.getPathString()?.let {
                        val task = DownloadTask()
                        val resultData = task.execute(it).get()
                        Log.e("RESULTS", "What is my data in JSON: $resultData")
                        runOnUiThread {
                            resultData?.let {
                                model?.setRecipes(resultData)
                            } ?: Log.e("TAG", "RESULT DATA empty")

                            dialog.dismiss()
                        }
                    }
                }
            }
        }
    }

    //MARK:- Async Tasks
    class DownloadTask: AsyncTask<String, Unit, JSONArray?>() {
        override fun doInBackground(vararg params: String?): JSONArray? {
            Log.e("TAG", "START DOWNLOAD TASK")
            if (params.isEmpty()) {
                return null
            }
            val resultString = downloadDataWith(params[0]!!)
            if (resultString.isEmpty()) {
                return null
            } else {
                Log.e("RESULTS", "What is my data: $resultString")
                return JSONArray(resultString)
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
