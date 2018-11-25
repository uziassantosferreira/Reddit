package com.uziassantosferreira.domain.model

import java.util.Date

data class Post(val title: String = "", val author: Author = Author(), val imagePreview: List<Image> = listOf(),
                val totalLikes: Int = 0, val totalComments: Int = 0, val date: Date = Date(),
                val text: String = "")