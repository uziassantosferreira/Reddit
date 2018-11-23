package com.uziassantosferreira.data.model

import com.google.gson.annotations.SerializedName

data class JsonGenericList<T>(val children: List<JsonGenericResponseWrapper<T>>? = listOf(),
                              @SerializedName("after") val after: String? = "")