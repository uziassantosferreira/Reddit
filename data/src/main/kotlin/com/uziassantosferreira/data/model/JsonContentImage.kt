package com.uziassantosferreira.data.model

import com.google.gson.annotations.SerializedName

data class JsonContentImage(@SerializedName("source ") val image: JsonImage? = JsonImage(),
                            val resolutions: List<JsonImage>? = listOf())