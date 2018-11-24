package com.uziassantosferreira.data.mapper

abstract class Mapper<T, K> {

    abstract fun transformFrom(source: K): T

    abstract fun transformTo(source: T): K

    fun transformFromList(source: List<K>): List<T> {
        return source.map { src -> transformFrom(src) }
    }

    fun transformToList(source: List<T>): List<K> {
        return source.map { src -> transformTo(src) }
    }
}