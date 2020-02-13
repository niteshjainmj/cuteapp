package com.moneytruth.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.moneytruth.app.TransactionBean
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = [TransactionBean::class], version = 1)
@TypeConverters(Converters::class)
abstract class HistoryDatabase : RoomDatabase() {

    abstract fun historyDao(): HistoryDao
    companion object {
        @Volatile
        private var db: HistoryDatabase? = null

        fun getInstance(context: Context, scope : CoroutineScope): HistoryDatabase {
            if (db == null) {
                synchronized(HistoryDatabase::class) {
                    if (db == null) {
                        db = Room.databaseBuilder(context.applicationContext, HistoryDatabase::class.java, "history_money.db")
                            .addCallback(object : Callback() {
                                override fun onCreate(db: SupportSQLiteDatabase) {
                                    super.onCreate(db)
                                    //increaseAutoIncrementIds()

                                }
                            })
                            .build()



                    }
                }
            }
            return db!!
        }


        private class WordDatabaseCallback(
            private val scope: CoroutineScope
        ) : RoomDatabase.Callback() {
            /**
             * Override the onOpen method to populate the database.
             * For this sample, we clear the database every time it is created or opened.
             */
            override fun onOpen(db: SupportSQLiteDatabase) {
                super.onOpen(db)
                // If you want to keep the data through app restarts,
                // comment out the following line.
                db?.let { database ->
                    scope.launch(Dispatchers.IO) {
                        //populateDatabase(database.wordDao())
                    }
                }
            }
        }

        fun destroyInstance() {
            db = null
        }
    }
}
