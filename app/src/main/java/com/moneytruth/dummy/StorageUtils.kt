package com.moneytruth.dummy

import android.content.Context
import android.content.SharedPreferences

import androidx.preference.PreferenceManager

object StorageUtils {

    fun putPref(context: Context, key: String, value: String) : Boolean{
        val prefs = PreferenceManager.getDefaultSharedPreferences(context)
        val editor = prefs.edit()
        editor.putString(key, value)
        return editor.commit()
    }

    fun putPref(context: Context, key: String, value: Float) : Boolean {
        val prefs = PreferenceManager.getDefaultSharedPreferences(context)
        val editor = prefs.edit()
        editor.putFloat(key, value)
        return editor.commit()
    }

    fun putPref(context: Context, key: String, value: Int): Boolean {
        val prefs = PreferenceManager.getDefaultSharedPreferences(context)
        val editor = prefs.edit()
        editor.putInt(key, value)
        return editor.commit()
    }

    fun putPref(context: Context, key: String, value: Boolean) : Boolean {
        val prefs = PreferenceManager.getDefaultSharedPreferences(context)
        val editor = prefs.edit()
        editor.putBoolean(key, value)
        return editor.commit()
    }

    fun getPrefStr(context: Context, key: String): String? {
        val preferences = PreferenceManager.getDefaultSharedPreferences(context)
        return preferences.getString(key, null)
    }

    fun getPrefForInt(context: Context, key: String): Int {
        val preferences = PreferenceManager.getDefaultSharedPreferences(context)
        return preferences.getInt(key, -1)
    }

    fun getPrefForBool(context: Context, key: String): Boolean {
        val preferences = PreferenceManager.getDefaultSharedPreferences(context)
        return preferences.getBoolean(key, false)
    }


    fun getPrefForFloat(context: Context, key: String): Float {
        val preferences = PreferenceManager.getDefaultSharedPreferences(context)
        return preferences.getFloat(key, 0f)
    }

    fun clearPref(aContext: Context, aKey: String) : Boolean {
        val preferences = PreferenceManager.getDefaultSharedPreferences(aContext)
        return preferences.edit().remove(aKey).commit()
    }


}
