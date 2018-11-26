package com.uziassantosferreira.presentation.data.repository

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.uziassantosferreira.presentation.model.Comment

interface CommentsRepository: PaginationRepository<Comment> {

    fun getList(remoteId: String): LiveData<PagedList<Comment>>
    fun resetList()
}