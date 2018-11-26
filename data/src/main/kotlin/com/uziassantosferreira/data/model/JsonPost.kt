package com.uziassantosferreira.data.model

import com.google.gson.annotations.SerializedName

data class JsonPost(val title: String? = "",
                    @SerializedName("selftext") val text: String? = "",
                    val preview: JsonPreview? = JsonPreview(),
                    @SerializedName("author") val authorName: String? = "",
                    @SerializedName("created_utc") val createdUtc: Long? = 0L,
                    val id: String? = "")