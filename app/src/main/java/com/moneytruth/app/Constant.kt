package com.moneytruth.app

import java.math.BigDecimal
import java.text.NumberFormat

object Constant {

    val TRANSACTION_TYPE_WITHDRAW_INDEX = 1
    val TRANSACTION_TYPE_DEPOSIT_INDEX = 2
    val TRANSACTION_TYPE_TRANSFER_INDEX = 3


    val SOURCE_TYPE_SAVING_ACCOUNT = 1
    val SOURCE_TYPE_PIGGI_BANK = 2
    val SOURCE_TYPE_OTHER = 3


}


fun getDoubleToString(aDouble : Double, aFractionDigit : Int = 2) : String{
    val nf: NumberFormat = NumberFormat.getNumberInstance()
    nf.maximumFractionDigits = aFractionDigit
    return nf.format(aDouble)
}


fun getBigDecimalToString(aBigDec : BigDecimal, aFraction :Int = 2): String{
    val bigData = BigDecimal(aBigDec.toString())
    return bigData.setScale(aFraction,  BigDecimal.ROUND_UP).toString()
}


fun getCompoundInterestString(aBasicValue : BigDecimal, aRate : Double, aYears : Int ) : String{
    var data  = aBasicValue *  BigDecimal(Math.pow((1 + aRate / 100), aYears.toDouble()))
    return getBigDecimalToString(data)
}


fun getCompoundInterestOneDay(aBasicValue : BigDecimal, aRate : Double, aYears : Int = 1  ) : BigDecimal{
    var data  = aBasicValue *  BigDecimal(Math.pow((1 + aRate / 100), (aYears.toDouble() /  365)))
    return data
}

fun getInflationBasedValue( aAmount : BigDecimal, aRate : Double,  aYear : Int ) : String{
    var cost = aAmount
    val inflation: Double = (aRate * .01) /// 12

    for (i in 1..aYear){
        println("print$i")
        cost = (inflation.toBigDecimal() * cost ) + cost;
    }
    val  reduceAmount : BigDecimal = cost.minus(aAmount)
    var remainingAmount = aAmount.minus(reduceAmount)
    if(remainingAmount.signum() == -1){
        return getBigDecimalToString(BigDecimal("0"))
    }else{
        return getBigDecimalToString(remainingAmount)

    }
}


fun getInflationBasedValueForOneDay( aAmount : BigDecimal, aRate : Double,  aYear : Int = 1) : BigDecimal{
    var cost = aAmount
    val inflation: Double = (aRate * .01) / 365
    for (i in 1..aYear){
        println("print$i")
        cost = (inflation.toBigDecimal() * cost ) + cost;
    }

    val  reduceAmount : BigDecimal = cost.minus(aAmount)
    var remainingAmount = aAmount.minus(reduceAmount)
    return remainingAmount
}