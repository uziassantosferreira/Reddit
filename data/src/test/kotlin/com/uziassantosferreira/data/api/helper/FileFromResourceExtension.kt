package com.uziassantosferreira.data.api.helper

fun fileFromResource(resource: String, javaClazz: Class<Any>) : String =
    javaClazz.classLoader?.getResourceAsStream(resource)?.bufferedReader()
        .use { it?.readText() ?: "" }