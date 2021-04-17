package com.example.customerlist.customerdetail

import androidx.lifecycle.ViewModel
import com.example.customerlist.database.CustomerDatabaseDao

class CustomerDetailViewModel(
    private val customerKey: Long = 0L,
    val database: CustomerDatabaseDao) : ViewModel() {

}