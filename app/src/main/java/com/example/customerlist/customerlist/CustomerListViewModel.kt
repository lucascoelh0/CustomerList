package com.example.customerlist.customerlist

import androidx.lifecycle.ViewModel
import com.example.customerlist.database.CustomerDatabaseDao

class CustomerListViewModel(
    val database: CustomerDatabaseDao
) : ViewModel() {
}