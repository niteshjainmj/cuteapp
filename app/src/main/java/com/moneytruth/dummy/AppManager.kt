package com.moneytruth.dummy

import android.content.Context
import android.graphics.Color
import androidx.core.content.ContextCompat
import com.moneytruth.R


class AppManager private constructor()  {


    companion object {
        const val SETTING_GOAL_INDEX = 1
        const val SETTING_UI_INDEX = 2
        const val SETTING_HISTORY_INDEX = 3

        const val SHARED_PREF_BG_COLOR_KEY = "app_bg_color"
        const val SHARED_PREF_BTN_COLOR_KEY = "app_btn_color"
        const val SHARED_PREF_PIGGI_COLOR_KEY = "app_piggi_color"
        const val SHARED_PREF_SAVING_COLOR_KEY = "app_saving_color"

        private var smManager: AppManager? = null

        val manager: AppManager
            get() {
                if (smManager == null) {
                    smManager = AppManager()
                }
                return smManager!!
            }
    }


    fun setBackgroundColor(aContext : Context, aColorStr : String?){
        if(aColorStr != null){
            StorageUtils.putPref(aContext, SHARED_PREF_BG_COLOR_KEY, aColorStr)
        }
    }

    fun setBtnColor(aContext : Context, aColorStr : String?){
        if(aColorStr != null){
            StorageUtils.putPref(aContext, SHARED_PREF_BTN_COLOR_KEY, aColorStr)
        }
    }

    fun setPiggiBankColor(aContext : Context, aColorStr : String?){
        if(aColorStr != null){
            StorageUtils.putPref(aContext, SHARED_PREF_PIGGI_COLOR_KEY, aColorStr)
        }
    }

    fun setSavingColor(aContext : Context, aColorStr : String?){
        if(aColorStr != null){
            StorageUtils.putPref(aContext, SHARED_PREF_SAVING_COLOR_KEY, aColorStr)
        }
    }

    fun getBgColor(aContext: Context): Int{
        var color : Int = ContextCompat.getColor(aContext, R.color.colorDefaultBg)
        val colorStr = StorageUtils.getPrefStr(aContext, SHARED_PREF_BG_COLOR_KEY)
        if(colorStr != null){
          color = try {
                Color.parseColor(colorStr)
            } catch (e: Exception) {
               ContextCompat.getColor(aContext, R.color.colorDefaultBg)
            }
        }else{
            color = ContextCompat.getColor(aContext, R.color.colorDefaultBg)
        }
       return color
    }


    fun getBtnBgColor(aContext: Context): Int{
        var color : Int = ContextCompat.getColor(aContext, R.color.colorDefaultBtnBg)
        val colorStr = StorageUtils.getPrefStr(aContext, SHARED_PREF_BTN_COLOR_KEY)
        if(colorStr != null){
            color = try {
                Color.parseColor(colorStr)
            } catch (e: Exception) {
                ContextCompat.getColor(aContext, R.color.colorDefaultBtnBg)
            }
        }else{
            color = ContextCompat.getColor(aContext, R.color.colorDefaultBtnBg)
        }
        return color
    }

    fun getPiggiBgColor(aContext: Context): Int{
        var color : Int = ContextCompat.getColor(aContext, R.color.colorDefaultPiggiBg)
        val colorStr = StorageUtils.getPrefStr(aContext, SHARED_PREF_PIGGI_COLOR_KEY)
        if(colorStr != null){
            color = try {
                Color.parseColor(colorStr)
            } catch (e: Exception) {
                ContextCompat.getColor(aContext, R.color.colorDefaultPiggiBg)
            }
        }else{
            color = ContextCompat.getColor(aContext, R.color.colorDefaultPiggiBg)
        }
        return color
    }

    fun getSavingBgColor(aContext: Context): Int{
        var color : Int = ContextCompat.getColor(aContext, R.color.colorDefaultSavingBg)
        val colorStr = StorageUtils.getPrefStr(aContext, SHARED_PREF_SAVING_COLOR_KEY)
        if(colorStr != null){
            color = try {
                Color.parseColor(colorStr)
            } catch (e: Exception) {
                ContextCompat.getColor(aContext, R.color.colorDefaultSavingBg)
            }
        }else{
            color = ContextCompat.getColor(aContext, R.color.colorDefaultSavingBg)
        }
        return color
    }


    fun getAllSettingMenuList(aContext : Context): ArrayList<SettingMenuItem>{
        val list = ArrayList<SettingMenuItem>()
        list.add(SettingMenuItem(aContext.getString(R.string.setting_goal_title), SETTING_GOAL_INDEX))
        list.add(SettingMenuItem(aContext.getString(R.string.setting_ui_title), SETTING_UI_INDEX))
        list.add(SettingMenuItem(aContext.getString(R.string.setting_history_title), SETTING_HISTORY_INDEX))
        return list
    }


//    init {
//    }
//
//    companion object {
//        lateinit var INSTANCE: AppManager
//
//        fun getInstance() : AppManager {
//            if(initialized.getAndSet(true)) {
//                INSTANCE = AppManager()
//            }
//            return INSTANCE
//        }
//    }
}