package com.example.customerlist.customerdetail

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.customerlist.database.Customer
import com.example.customerlist.database.CustomerDatabaseDao

class CustomerDetailViewModel(
    private val customerKey: Long = 0L,
    val dataSource: CustomerDatabaseDao) : ViewModel() {

        val database = dataSource

    private val customer: LiveData<Customer>

    fun getCustomer() = customer

    init {
        customer = database.getCustomerWithId(customerKey)
    }

    val noCpfTextVisible = Transformations.map(getCustomer()) {
        if (it.cpf.isEmpty()) View.VISIBLE else View.GONE
    }

    val noDateOfBirthTextVisible = Transformations.map(getCustomer()) {
        if (it.dateOfBirth.isEmpty()) View.VISIBLE else View.GONE
    }

    val phones = database.getCustomerPhones(customerKey)

    val noPhonesTextVisible = Transformations.map(phones) {
        if (it.isEmpty()) View.VISIBLE else View.GONE
    }


}