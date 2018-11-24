package com.uziassantosferreira.data.model

data class JsonGenericResponseWrapper<T>(private val kind: String? = "", val data: T? = null)