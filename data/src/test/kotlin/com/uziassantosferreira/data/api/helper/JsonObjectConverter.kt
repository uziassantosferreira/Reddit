package com.uziassantosferreira.data.api.helper

import com.google.gson.GsonBuilder
import java.lang.reflect.Type

object JsonObjectConverter {

    private val gson = GsonBuilder()
        .create()

    fun <T> convertFromJson(json: String, jsonObjectClass: Class<T>): T {
        return gson.fromJson(json, jsonObjectClass)
    }
    fun <T> convertFromJson(json: String, type: Type): T {
        return gson.fromJson(json, type)
    }

}
