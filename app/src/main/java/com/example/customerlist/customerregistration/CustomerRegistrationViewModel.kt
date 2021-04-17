package com.example.customerlist.customerregistration

import androidx.lifecycle.ViewModel
import com.example.customerlist.database.CustomerDatabaseDao

class CustomerRegistrationViewModel(
    val database: CustomerDatabaseDao
) : ViewModel() {
}