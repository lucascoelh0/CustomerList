package com.example.customerlist.customerregistration

import android.view.View
import android.widget.AdapterView
import androidx.lifecycle.*
import com.example.customerlist.database.Customer
import com.example.customerlist.database.CustomerDatabaseDao
import kotlinx.coroutines.launch

class CustomerRegistrationViewModel(
    dataSource: CustomerDatabaseDao
) : ViewModel() {

    val database = dataSource

    private val _navigateToCustomerList = MutableLiveData<Boolean?>()

    val navigateToCustomerList: LiveData<Boolean?>
        get() = _navigateToCustomerList

    fun doneNavigating() {
        _navigateToCustomerList.value = null
    }

    val name = MutableLiveData("")
    val cpf = MutableLiveData("")
    val uf = MutableLiveData("")

    val valid = MediatorLiveData<Boolean>().apply {
        addSource(name) {
            value = isFormValid()
        }
        addSource(cpf) {
            value = isFormValid()
        }
        addSource(uf) {
            value = isFormValid()
        }
    }

    val spinnerClickListener = object : AdapterView.OnItemSelectedListener {
        override fun onNothingSelected(parent: AdapterView<*>?) {
        }

        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            uf.value = parent?.getItemAtPosition(position) as String
        }
    }

    fun isCpfRequired() = uf.value.equals("SP")

    fun isCpfValid() = cpf.value?.length == 11

    fun isFormValid(): Boolean {
        if (name.value?.isNotEmpty() == true) {
            if (isCpfRequired()) {
                return isCpfValid()
            } else {
                return true
            }
        }

        return false
    }

    fun onSave() {
        viewModelScope.launch {
            val newCustomer = Customer()
            newCustomer.name = name.value.toString()
            newCustomer.apply {
                name = this@CustomerRegistrationViewModel.name.value.toString()
                cpf = this@CustomerRegistrationViewModel.cpf.value.toString()
                uf = this@CustomerRegistrationViewModel.uf.value.toString()
            }
            database.insertCustomer(newCustomer)

            _navigateToCustomerList.value = true
        }
    }
}