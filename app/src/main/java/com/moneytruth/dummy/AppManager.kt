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


        const val GOAL_HOUSE_INDEX = 1
        const val GOAL_COLLAGE_INDEX = 2
        const val GOAL_CAR_INDEX = 3
        const val GOAL_VACATION_INDEX = 4
        const val GOAL_RETIRE_INDEX = 5
        const val GOAL_SHOPPING_INDEX = 6

        const val SHARED_PREF_BG_COLOR_KEY = "app_bg_color"
        const val SHARED_PREF_BTN_COLOR_KEY = "app_btn_color"
        const val SHARED_PREF_PIGGI_COLOR_KEY = "app_piggi_color"
        const val SHARED_PREF_SAVING_COLOR_KEY = "app_saving_color"


        const val SHARED_PREF_GOAL_INDEX_KEY = "goal_index"
        const val SHARED_PREF_GOAL_TITLE_KEY = "goal_title"
        const val SHARED_PREF_GOAL_YEAR_KEY = "goal_year"
        const val SHARED_PREF_GOAL_AMOUNT_KEY = "goal_amount"

        private var smManager: AppManager? = null

        val manager: AppManager
            get() {
                if (smManager == null) {
                    smManager = AppManager()
                }
                return smManager!!
            }
    }

    fun saveGoalDetails(aContext : Context, aGaolItem : GoalDetails){
        if(aGaolItem.mGoalName.isNotEmpty()){
            StorageUtils.putPref(aContext, SHARED_PREF_GOAL_INDEX_KEY, aGaolItem.mGoalIndex)
            StorageUtils.putPref(aContext, SHARED_PREF_GOAL_TITLE_KEY, aGaolItem.mGoalName)
            StorageUtils.putPref(aContext, SHARED_PREF_GOAL_AMOUNT_KEY, aGaolItem.mAmount)
            StorageUtils.putPref(aContext, SHARED_PREF_GOAL_YEAR_KEY, aGaolItem.mYears)
        }
    }


    fun getSavedGoalDetails(aContext: Context): GoalDetails?{
        val goalName = StorageUtils.getPrefStr(aContext, SHARED_PREF_GOAL_TITLE_KEY)
        if (goalName != null && goalName.isNotEmpty()){
            val index = StorageUtils.getPrefForInt(aContext, SHARED_PREF_GOAL_INDEX_KEY)
            val amount = StorageUtils.getPrefStr(aContext, SHARED_PREF_GOAL_AMOUNT_KEY)
            val years = StorageUtils.getPrefForInt(aContext, SHARED_PREF_GOAL_YEAR_KEY)

            return GoalDetails(index, goalName, years, amount!!)
        }
        return null
    }


    fun setBackgroundColor(aContext : Context, aColorStr : String?){
        if(aColorStr != null){
            StorageUtils.putPref(aContext, SHARED_PREF_BG_COLOR_KEY, "#"+ aColorStr)
        }
    }

    fun setBtnColor(aContext : Context, aColorStr : String?){
        if(aColorStr != null){
            StorageUtils.putPref(aContext, SHARED_PREF_BTN_COLOR_KEY, "#"+ aColorStr)
        }
    }

    fun setPiggiBankColor(aContext : Context, aColorStr : String?){
        if(aColorStr != null){
            StorageUtils.putPref(aContext, SHARED_PREF_PIGGI_COLOR_KEY, "#"+ aColorStr)
        }
    }

    fun setSavingColor(aContext : Context, aColorStr : String?){
        if(aColorStr != null){
            StorageUtils.putPref(aContext, SHARED_PREF_SAVING_COLOR_KEY, "#"+ aColorStr)
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
        //list.add(SettingMenuItem(aContext.getString(R.string.setting_history_title), SETTING_HISTORY_INDEX))
        return list
    }


    fun getAllGoalList(aContext : Context): ArrayList<GoalItem>{
        val list = ArrayList<GoalItem>()
        list.add(GoalItem(aContext.getString(R.string.goal_one), R.drawable.goal_image, GOAL_HOUSE_INDEX, false))
        list.add(GoalItem(aContext.getString(R.string.goal_two), R.drawable.goal_image, GOAL_COLLAGE_INDEX, false))
        list.add(GoalItem(aContext.getString(R.string.goal_three), R.drawable.goal_image, GOAL_CAR_INDEX, false))
        list.add(GoalItem(aContext.getString(R.string.goal_four), R.drawable.goal_image, GOAL_VACATION_INDEX, false))
        list.add(GoalItem(aContext.getString(R.string.goal_five), R.drawable.goal_image, GOAL_RETIRE_INDEX, false))
        list.add(GoalItem(aContext.getString(R.string.goal_six), R.drawable.goal_image, GOAL_SHOPPING_INDEX, false))
        return list

    }


    fun getIconForGOalIndex(aIndex : Int) : Int{
        when(aIndex){
            GOAL_HOUSE_INDEX-> return R.drawable.goal_image
            GOAL_COLLAGE_INDEX-> return R.drawable.goal_image
            GOAL_CAR_INDEX-> return R.drawable.goal_image
            GOAL_VACATION_INDEX-> return R.drawable.goal_image
            GOAL_RETIRE_INDEX-> return R.drawable.goal_image
            GOAL_SHOPPING_INDEX-> return R.drawable.goal_image
        }
        return  R.drawable.goal_image
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