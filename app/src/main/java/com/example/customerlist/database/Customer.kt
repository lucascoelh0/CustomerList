package com.example.customerlist.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.text.SimpleDateFormat
import java.util.*

@Entity(tableName = "customer_table")
data class Customer(
    @PrimaryKey(autoGenerate = true)
    var customerId: Long = 0L,

    @ColumnInfo(name = "name")
    var name: String = "",

    @ColumnInfo(name = "cpf")
    var cpf: String = "N/A",

    @ColumnInfo(name = "registration_date_hour")
    var registrationDateHour: String = SimpleDateFormat(
        "dd/MM/yyyy",
        Locale.US
    ).format(Calendar.getInstance().time).toString(),

    @ColumnInfo(name = "date_of_birth")
    var dateOfBirth: String = "N/A",

    @ColumnInfo(name = "ufId")
    var uf: String = ""
)