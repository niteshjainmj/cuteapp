package com.moneytruth.viewmodels

import android.app.Application
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moneytruth.R
import com.moneytruth.app.AppManager
import com.moneytruth.app.Constant
import com.moneytruth.app.GoalDetails
import com.moneytruth.app.TransactionBean
import com.moneytruth.db.HistoryDatabase
import com.moneytruth.db.HistoryRepository
import kotlinx.coroutines.launch
import java.math.BigDecimal
import java.util.*

class HomeViewModel (application : Application) : ViewModel(){

    private val mRepository : HistoryRepository
    val mHistoryList : LiveData<List<TransactionBean>>

    var mGoalDetails : MutableLiveData<GoalDetails> ? = null

    var mMsgSender : MutableLiveData<String>


    var updateFlag : MutableLiveData<Boolean>


//    val mTagList : LiveData<List<GroupTagBean>>

//
//    fun getContact() : LiveData<List<LocalContact>>{
//        return mContactList
//    }

    init {
        val historyDao = HistoryDatabase.getInstance(application, viewModelScope).historyDao()
        mRepository = HistoryRepository(historyDao)
        mHistoryList = mRepository.allHistory
        mGoalDetails = MutableLiveData()
        mMsgSender = MutableLiveData("")
        updateFlag =  MutableLiveData(false)
    }

    private fun loadContact(){
        //DO a asyncronous operation to fetch user
    }

    public fun addTransactionInDB(aTransactionBean : TransactionBean) = viewModelScope.launch {
        mRepository.insertOrUpdate(aTransactionBean)
    }

    public fun addTransactionListInDb(aTransactionList : List<TransactionBean>) = viewModelScope.launch {
        mRepository.insertOrUpdateList(aTransactionList)
    }


    public fun handleSaveDeposit(aBigDecimal : BigDecimal, aContext : Context){
        val curSavingVal = AppManager.manager.getSavingAccountBalance(aContext)
        val newVal =  curSavingVal.add(aBigDecimal)
        AppManager.manager.setSavingAccountBalance(aContext, newVal)

        val transactionBean = TransactionBean(0,
            Constant.TRANSACTION_TYPE_DEPOSIT_INDEX,
            Constant.SOURCE_TYPE_OTHER,
            Constant.SOURCE_TYPE_SAVING_ACCOUNT,
            aBigDecimal.toString(), curSavingVal.toString(), Date()
        )
        addTransactionInDB(transactionBean)
        updateFlag.postValue(true)
    }



    public fun handleSaveWithDraw(aBigDecimal : BigDecimal, aContext : Context){
        val curSavingVal = AppManager.manager.getSavingAccountBalance(aContext)
        val newVal =  curSavingVal.minus(aBigDecimal)
        if(newVal.signum() == -1){
            mMsgSender.postValue(aContext.getString(R.string.insufficient_balance_error))
        }else{
            AppManager.manager.setSavingAccountBalance(aContext, newVal)

            val transactionBean = TransactionBean(0,
                Constant.TRANSACTION_TYPE_WITHDRAW_INDEX,
                Constant.SOURCE_TYPE_SAVING_ACCOUNT,
                Constant.SOURCE_TYPE_OTHER,
                aBigDecimal.toString(), curSavingVal.toString(), Date()
            )
            addTransactionInDB(transactionBean)
            updateFlag.postValue(true)

        }
    }

}