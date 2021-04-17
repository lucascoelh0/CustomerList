package com.example.customerlist.customerdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.customerlist.database.CustomerDatabaseDao
import java.lang.IllegalArgumentException

class CustomerDetailViewModelFactory(
    private val customerKey: Long,
    private val dataSource: CustomerDatabaseDao) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CustomerDetailViewModel::class.java)) {
            return CustomerDetailViewModel(customerKey, dataSource) as T
        }
        throw IllegalArgumentException("Unknown ViewModelClass")
    }
}