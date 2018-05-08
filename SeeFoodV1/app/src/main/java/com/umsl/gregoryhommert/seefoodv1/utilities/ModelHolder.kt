package com.umsl.gregoryhommert.seefoodv1.utilities

import java.lang.ref.WeakReference
import java.util.HashMap

class ModelHolder {
    companion object {
        val instance = ModelHolder()
    }

    private val modelData = HashMap<String, WeakReference<Any?>>()

    fun saveModel(modelKey: String, model: Any?) {
        modelData[modelKey] = WeakReference(model)
    }

    fun getModel(modelKey: String): Any? {
        val weakObject = modelData[modelKey]
        return weakObject?.get()
    }
}