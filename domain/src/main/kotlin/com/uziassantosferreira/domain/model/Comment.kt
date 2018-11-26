package com.uziassantosferreira.domain.model

data class Comment(val text: String = "", val remoteId: String = "", val author: Author = Author())