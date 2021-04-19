package com.example.customerlist.customerregistration

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.customerlist.R
import com.example.customerlist.database.CustomerDatabase
import com.example.customerlist.databinding.FragmentCustomerRegistrationBinding
import java.util.*

class CustomerRegistrationFragment : Fragment() {

    private lateinit var binding: FragmentCustomerRegistrationBinding
    private lateinit var customerRegistrationViewModel: CustomerRegistrationViewModel
    private var cal: Calendar = Calendar.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_customer_registration, container, false
        )

        val application = requireNotNull(this.activity).application
        val dataSource = CustomerDatabase.getInstance(application).customerDatabaseDao

        val viewModelFactory = CustomerRegistrationViewModelFactory(dataSource)
        customerRegistrationViewModel =
            ViewModelProvider(this, viewModelFactory).get(CustomerRegistrationViewModel::class.java)

        customerRegistrationViewModel.navigateToCustomerList.observe(viewLifecycleOwner, {
            if (it == true) navigateToList()
        })

        val dateSetListenerBirth =
            DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, monthOfYear)
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                customerRegistrationViewModel.updateDateOfBirth(cal)
            }

        val dateSetListenerRegistration =
            DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, monthOfYear)
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                customerRegistrationViewModel.updateDateOfRegistration(cal)
            }

        customerRegistrationViewModel.clickDateOfBirth.observe(viewLifecycleOwner, {
            if (it == true) showDatePickerDialog(dateSetListenerBirth)
        })

        customerRegistrationViewModel.clickRegistrationDate.observe(viewLifecycleOwner, {
            if (it == true) showDatePickerDialog(dateSetListenerRegistration)
        })

        customerRegistrationViewModel.clickHourOfRegistration.observe(viewLifecycleOwner, {
            if (it == true) {
                val hour = cal.get(Calendar.HOUR_OF_DAY)
                val minute = cal.get(Calendar.MINUTE)

                TimePickerDialog(
                    context, { _, _hour, _minute ->
                        customerRegistrationViewModel.updateHourOfRegistration(_hour, _minute)
                    }, hour, minute, true
                ).show()
            }
        })

        customerRegistrationViewModel.phoneEditTextArray.value?.add(binding.phoneEditText)

        customerRegistrationViewModel.clickAddNumber.observe(viewLifecycleOwner, {
            createPhoneField()
        })

        binding.customerRegistrationViewModel = customerRegistrationViewModel
        binding.lifecycleOwner = this

        return binding.root
    }

    private fun navigateToList() {
        this.findNavController().navigate(
            CustomerRegistrationFragmentDirections
                .actionCustomerRegistrationFragmentToCustomerListFragment()
        )
        customerRegistrationViewModel.doneNavigating()
    }

    private fun showDatePickerDialog(dateSetListener: DatePickerDialog.OnDateSetListener) {
        DatePickerDialog(
            requireContext(),
            dateSetListener,
            cal.get(Calendar.YEAR),
            cal.get(Calendar.MONTH),
            cal.get(Calendar.DAY_OF_MONTH)
        ).show()
    }

    private fun createPhoneField() {
        val editText = EditText(context)
        editText.setCompoundDrawablesWithIntrinsicBounds(
            R.drawable.ic_outline_local_phone_24,
            0,
            0,
            0
        )
        editText.hint = getString(R.string.telefone)
        editText.compoundDrawablePadding = resources.getDimensionPixelSize(R.dimen.drawable_margin)
        editText.inputType = InputType.TYPE_CLASS_PHONE
        editText.maxLines = 1
        editText.setTextColor(ContextCompat.getColor(requireContext(), R.color.text_color))
        editText.setHintTextColor(ContextCompat.getColor(requireContext(), R.color.text_color))

        customerRegistrationViewModel.phoneEditTextArray.value?.add(editText)
        binding.editTextContainerLl.addView(editText)
    }
}