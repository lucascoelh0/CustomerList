package com.example.customerlist.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.time.LocalDateTime
import java.util.*

@Entity(tableName = "customer_table")
data class Customer(
    @PrimaryKey(autoGenerate = true)
    var customerId: Long = 0L,

    @ColumnInfo(name = "name")
    var name: String = "",

    @ColumnInfo(name = "cpf")
    var cpf: String = "",

    @ColumnInfo(name = "registration_date_hour")
    var registrationDateHour: String = Date().toString(),

    @ColumnInfo(name = "date_of_birth")
    var dateOfBirth: String = "",

    @ColumnInfo(name = "ufId")
    var uf: String = ""
)