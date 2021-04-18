package com.example.customerlist.customerregistration

import android.view.View
import android.widget.AdapterView
import android.widget.EditText
import androidx.lifecycle.*
import com.example.customerlist.database.Customer
import com.example.customerlist.database.CustomerDatabaseDao
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.Period
import java.time.format.DateTimeFormatter
import java.util.*

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

    private val _clickDateOfBirth = MutableLiveData<Boolean?>()

    val clickDateOfBirth: LiveData<Boolean?>
        get() = _clickDateOfBirth

    private val _clickRegistrationDate = MutableLiveData<Boolean?>()

    val clickRegistrationDate: LiveData<Boolean?>
        get() = _clickRegistrationDate

    private val _clickHourOfRegistration = MutableLiveData<Boolean?>()

    val clickHourOfRegistration: LiveData<Boolean?>
        get() = _clickHourOfRegistration

    private val dateFormat = "dd/MM/yyyy"
    val name = MutableLiveData("")
    val cpf = MutableLiveData("")
    val uf = MutableLiveData("")
    val dateOfRegistration = MutableLiveData("")
    val hourOfRegistration = MutableLiveData("")
    val dateOfBirth = MutableLiveData("")

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
        addSource(dateOfRegistration) {
            value = isFormValid()
        }
        addSource(hourOfRegistration) {
            value = isFormValid()
        }
        addSource(dateOfBirth) {
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

    fun updateDateOfBirth(calendar: Calendar) {
        val simpleDateFormat = SimpleDateFormat(dateFormat, Locale.US)
        dateOfBirth.value = simpleDateFormat.format(calendar.time)
    }

    fun updateDateOfRegistration(calendar: Calendar) {
        val simpleDateFormat = SimpleDateFormat(dateFormat, Locale.US)
        dateOfRegistration.value = simpleDateFormat.format(calendar.time)
    }

    fun updateHourOfRegistration(hour: Int, minute: Int) {
        hourOfRegistration.value = "%02d:%02d".format(hour, minute)
    }

    fun isCpfRequired() = uf.value.equals("SP")

    fun isCpfValid(): Boolean {
        if (isCpfRequired()) {
            return cpf.value?.length == 14
        }
        return cpf.value?.length == 14 || cpf.value?.isEmpty() == true
    }

    fun isOlderThan18Required() = uf.value.equals("MG")

    fun isOlderThan18(): Boolean {
        if (dateOfBirth.value?.isNotEmpty() == true) {
            val formatter = DateTimeFormatter.ofPattern(dateFormat)
            val date: LocalDate = LocalDate.parse(dateOfBirth.value, formatter)
            val diff = Period.between(date, LocalDate.now())
            return diff.years >= 18
        }

        return false
    }

    fun isFormValid(): Boolean {
        if (name.value?.isNotEmpty() == true) {
            if (isCpfRequired()) {
                return isCpfValid()
            } else if (isOlderThan18Required()) {
                return isOlderThan18()
            }
            return true
        }

        return false
    }

    fun onClickDateOfBirth() {
        _clickDateOfBirth.value = true
    }

    fun onClickRegistrationDate() {
        _clickRegistrationDate.value = true
    }

    fun onClickHourOfRegistration() {
        _clickHourOfRegistration.value = true
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