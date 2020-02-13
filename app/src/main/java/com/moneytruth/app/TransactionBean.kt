package com.moneytruth.app

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import java.math.BigDecimal
import java.util.*


@Entity(tableName = "history", indices = [(Index(value = ["id"], unique = true))])
data class TransactionBean(
    @PrimaryKey(autoGenerate = true) var id: Int?,
    @ColumnInfo(name = "action_index") var actionIndex: Int,
    @ColumnInfo(name = "form_source") var fromSource: Int,
    @ColumnInfo(name = "to_source") var toSource: Int,
    @ColumnInfo(name = "amount") var amount: String,
    @ColumnInfo(name = "last_balance") var lastBalance: String,
    @ColumnInfo(name = "date") var time: Date
) {
    override fun equals(other: Any?) = id == (other as? TransactionBean?)?.id
    override fun hashCode() = id ?: 0
}
