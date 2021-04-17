package com.example.customerlist.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "phone_table")
data class Phone(
    @PrimaryKey(autoGenerate = true)
    var phoneId: Long = 0L,

    @ColumnInfo(name = "customer_id")
    var customerId: Long = 0L,

    @ColumnInfo(name = "phone")
    var phone: String = ""
)



