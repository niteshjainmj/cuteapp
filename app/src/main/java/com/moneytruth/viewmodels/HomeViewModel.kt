package com.moneytruth.viewmodels

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moneytruth.app.TransactionBean
import com.moneytruth.db.HistoryDatabase
import com.moneytruth.db.HistoryRepository
import kotlinx.coroutines.launch

class HomeViewModel (application : Application) : ViewModel(){

    private val mRepository : HistoryRepository
    val mHistoryList : LiveData<List<TransactionBean>>
//    val mTagList : LiveData<List<GroupTagBean>>

//
//    fun getContact() : LiveData<List<LocalContact>>{
//        return mContactList
//    }

    init {
        val historyDao = HistoryDatabase.getInstance(application, viewModelScope).historyDao()
        mRepository = HistoryRepository(historyDao)
        mHistoryList = mRepository.allHistory
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

}