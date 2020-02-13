package com.moneytruth.db

import androidx.lifecycle.LiveData
import com.moneytruth.app.TransactionBean

class HistoryRepository(private val mHistoryDao : HistoryDao) {

    suspend fun insertOrUpdate(aTransactionBean : TransactionBean){
        mHistoryDao.insertOrUpdate(aTransactionBean)
    }


    suspend fun insertOrUpdateList(aTransactionList : List<TransactionBean>){
        mHistoryDao.insertHistoryList(aTransactionList)
    }

    val allHistory : LiveData<List<TransactionBean>> = mHistoryDao.getHistoryList()

}

