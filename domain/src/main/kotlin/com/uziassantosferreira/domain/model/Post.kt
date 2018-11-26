package com.uziassantosferreira.domain.model

import java.util.Date

data class Post(val title: String = "", val author: Author = Author(),
                val imagePreview: List<Image> = listOf(),
                val date: Date = Date(), val text: String = "", val remoteId: String = "")