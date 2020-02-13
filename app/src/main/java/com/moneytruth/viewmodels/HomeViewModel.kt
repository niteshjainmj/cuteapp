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



    //Saving Actions

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


    public fun handleTransferSavingToPiggyBank(aBigDecimal : BigDecimal, aContext : Context){
        val curSavingVal = AppManager.manager.getSavingAccountBalance(aContext)
        val newSavingVal =  curSavingVal.minus(aBigDecimal)
        if(newSavingVal.signum() == -1){
            mMsgSender.postValue(aContext.getString(R.string.insufficient_balance_error))
        }else {
            //Update in piggy bank
            val currentPiggyVal = AppManager.manager.getPiggyBankBalance(aContext)
            val newPiggyValue = currentPiggyVal.add(aBigDecimal)
            AppManager.manager.setPiggyBankBalance(aContext, newPiggyValue)

            //update in saving
            AppManager.manager.setSavingAccountBalance(aContext, newSavingVal)

            val transactionBean = TransactionBean(0,
                Constant.TRANSACTION_TYPE_TRANSFER_INDEX,
                Constant.SOURCE_TYPE_SAVING_ACCOUNT,
                Constant.SOURCE_TYPE_PIGGI_BANK,
                aBigDecimal.toString(), currentPiggyVal.toString(), Date()
            )
            addTransactionInDB(transactionBean)
            updateFlag.postValue(true)
        }

        }

    //Piggy Actions
    public fun handlePiggyDeposit(aBigDecimal : BigDecimal, aContext : Context){
        val curPiggyBalance = AppManager.manager.getPiggyBankBalance(aContext)
        val newVal =  curPiggyBalance.add(aBigDecimal)
        AppManager.manager.setPiggyBankBalance(aContext, newVal)

        val transactionBean = TransactionBean(0,
            Constant.TRANSACTION_TYPE_DEPOSIT_INDEX,
            Constant.SOURCE_TYPE_OTHER,
            Constant.SOURCE_TYPE_PIGGI_BANK,
            aBigDecimal.toString(), curPiggyBalance.toString(), Date()
        )
        addTransactionInDB(transactionBean)
        updateFlag.postValue(true)
    }


    public fun handlePiggyWithDraw(aBigDecimal : BigDecimal, aContext : Context){
        val curPiggyBal = AppManager.manager.getPiggyBankBalance(aContext)
        val newVal =  curPiggyBal.minus(aBigDecimal)
        if(newVal.signum() == -1){
            mMsgSender.postValue(aContext.getString(R.string.insufficient_balance_error))
        }else{
            AppManager.manager.setPiggyBankBalance(aContext, newVal)

            val transactionBean = TransactionBean(0,
                Constant.TRANSACTION_TYPE_WITHDRAW_INDEX,
                Constant.SOURCE_TYPE_PIGGI_BANK,
                Constant.SOURCE_TYPE_OTHER,
                aBigDecimal.toString(), curPiggyBal.toString(), Date()
            )
            addTransactionInDB(transactionBean)
            updateFlag.postValue(true)

        }
    }


    public fun handleTransferPiggyBankToSaving(aBigDecimal : BigDecimal, aContext : Context){
        val curPiggyVal = AppManager.manager.getPiggyBankBalance(aContext)
        val newPiggyVal =  curPiggyVal.minus(aBigDecimal)
        if(newPiggyVal.signum() == -1){
            mMsgSender.postValue(aContext.getString(R.string.insufficient_balance_error))
        }else {

            //Update Saving Account
            val curSavingVal = AppManager.manager.getSavingAccountBalance(aContext)
            val newSavingVal = curSavingVal.add(aBigDecimal)
            AppManager.manager.setSavingAccountBalance(aContext, newSavingVal)

            //update in saving
            AppManager.manager.setPiggyBankBalance(aContext, newPiggyVal)

            val transactionBean = TransactionBean(0,
                Constant.TRANSACTION_TYPE_TRANSFER_INDEX,
                Constant.SOURCE_TYPE_PIGGI_BANK,
                Constant.SOURCE_TYPE_SAVING_ACCOUNT,
                aBigDecimal.toString(), curSavingVal.toString(), Date()
            )
            addTransactionInDB(transactionBean)
            updateFlag.postValue(true)
        }

    }



}