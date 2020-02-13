package com.moneytruth.db


import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.moneytruth.app.TransactionBean

@Dao
interface HistoryDao {

    @Query("SELECT * FROM history")
    fun getHistoryList() : LiveData<List<TransactionBean>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdate(aTransactionBean : TransactionBean): Long

    @Insert()
    fun insertHistoryList(aList : List<TransactionBean>)

}