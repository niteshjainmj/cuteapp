package com.moneytruth.db

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.*
import kotlin.collections.ArrayList


class Converters {
    private val gson = Gson()
    private val longType = object : TypeToken<List<Long>>() {}.type
    private val stringType = object : TypeToken<List<String>>() {}.type

    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return if (value == null) null else Date(value)
    }

    @TypeConverter
    fun dateToTimestamp(date: Date): Long {
        return date.getTime()
    }



}
