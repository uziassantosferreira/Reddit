package com.uziassantosferreira.presentation.model

import java.util.Date

data class Post(val title: String = "", val author: Author = Author(),
                val imagePreview: List<Image> = listOf(), val date: Date = Date())