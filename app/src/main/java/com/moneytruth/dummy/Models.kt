package com.moneytruth.dummy

import android.text.InputFilter
import android.text.Spanned
import java.util.regex.Matcher
import java.util.regex.Pattern


data class SettingMenuItem(var mTitle : String, var mIndex : Int)

data class GoalItem(var mTitle : String, var mIcon :Int, var mIndex : Int, var mSelected : Boolean)


data class GoalDetails(var mGoalIndex : Int, var mGoalName : String, var mYears : Int, var mAmount : String)


class DecimalDigitsInputFilter(digitsBeforeZero: Int, digitsAfterZero: Int) :
    InputFilter {
    var mPattern: Pattern
    override fun filter(
        source: CharSequence,
        start: Int,
        end: Int,
        dest: Spanned,
        dstart: Int,
        dend: Int
    ): CharSequence? {
        val matcher: Matcher = mPattern.matcher(dest)
        return if (!matcher.matches()) "" else null
    }

    init {
        mPattern =
            Pattern.compile("[0-9]{0," + (digitsBeforeZero - 1) + "}+((\\.[0-9]{0," + (digitsAfterZero - 1) + "})?)||(\\.)?")
    }
}