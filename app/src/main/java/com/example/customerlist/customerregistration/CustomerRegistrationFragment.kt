package com.example.customerlist.customerregistration

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
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

    var cal = Calendar.getInstance()
    private lateinit var binding: FragmentCustomerRegistrationBinding

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
        val customerRegistrationViewModel =
            ViewModelProvider(this, viewModelFactory).get(CustomerRegistrationViewModel::class.java)

        binding.customerRegistrationViewModel = customerRegistrationViewModel
        binding.lifecycleOwner = this

        //Observar a livedata para navegar
        customerRegistrationViewModel.navigateToCustomerList.observe(viewLifecycleOwner, {
            if (it == true) {
                this.findNavController().navigate(
                    CustomerRegistrationFragmentDirections
                        .actionCustomerRegistrationFragmentToCustomerListFragment()
                )
                customerRegistrationViewModel.doneNavigating()
            }
        })

        val dateSetListenerBirth = object : DatePickerDialog.OnDateSetListener {
            override fun onDateSet(
                view: DatePicker, year: Int, monthOfYear: Int,
                dayOfMonth: Int
            ) {
                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, monthOfYear)
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                customerRegistrationViewModel.updateDateOfBirth(cal)
            }
        }

        val dateSetListenerRegistration = object : DatePickerDialog.OnDateSetListener {
            override fun onDateSet(
                view: DatePicker, year: Int, monthOfYear: Int,
                dayOfMonth: Int
            ) {
                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, monthOfYear)
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                customerRegistrationViewModel.updateDateOfRegistration(cal)
            }
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
            val editText = createPhoneField()
            customerRegistrationViewModel.phoneEditTextArray.value?.add(editText)
            binding.editTextContainerLl.addView(editText)
        })

        return binding.root
    }

    fun showDatePickerDialog(dateSetListener: DatePickerDialog.OnDateSetListener) {
        DatePickerDialog(
            requireContext(),
            dateSetListener,
            cal.get(Calendar.YEAR),
            cal.get(Calendar.MONTH),
            cal.get(Calendar.DAY_OF_MONTH)
        ).show()
    }

    fun createPhoneField(): EditText {
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

        return editText
    }
}