package com.example.customerlist.customerregistration

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.customerlist.database.CustomerDatabaseDao

class CustomerRegistrationViewModelFactory(
    private val dataSource: CustomerDatabaseDao
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CustomerRegistrationViewModel::class.java)) {
            return CustomerRegistrationViewModel(dataSource) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
