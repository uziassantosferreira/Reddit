package com.uziassantosferreira.presentation.model

data class Comment(val text: String = "", val remoteId: String = "",
                   val author: Author = Author())