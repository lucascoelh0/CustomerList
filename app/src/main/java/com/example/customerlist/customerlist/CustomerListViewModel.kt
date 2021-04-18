package com.example.customerlist.customerlist

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.customerlist.database.CustomerDatabaseDao

class CustomerListViewModel(
    dataSource: CustomerDatabaseDao
) : ViewModel() {

    val database = dataSource

    private val _navigateToCustomerRegistration = MutableLiveData<Boolean?>()

    val navigateToCustomerRegistration: LiveData<Boolean?>
        get() = _navigateToCustomerRegistration

    val customers = database.getAllCustomers()

    val noCustomersTextVisible = Transformations.map(customers) {
        if (it.isEmpty()) View.VISIBLE else View.GONE
    }

    val customerListVisible = Transformations.map(customers) {
        if (it.isEmpty()) View.GONE else View.VISIBLE
    }

    var showSnackbarSaved = MutableLiveData<Boolean?>()

    fun doneShowingSnackbarSaved() {
        showSnackbarSaved.value = null
    }

    fun doneNavigatingToRegistration() {
        _navigateToCustomerRegistration.value = null
    }

    fun onAdd() {
        _navigateToCustomerRegistration.value = true
    }

}