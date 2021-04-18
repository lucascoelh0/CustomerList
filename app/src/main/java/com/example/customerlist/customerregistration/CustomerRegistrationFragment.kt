package com.example.customerlist.customerregistration

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.customerlist.R
import com.example.customerlist.database.CustomerDatabase
import com.example.customerlist.databinding.FragmentCustomerRegistrationBinding

class CustomerRegistrationFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding: FragmentCustomerRegistrationBinding = DataBindingUtil.inflate(
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
                        .actionCustomerRegistrationFragmentToCustomerListFragment(true)
                )
                customerRegistrationViewModel.doneNavigating()
            }
        })

        //Apertando o botão de voltar
        requireActivity().onBackPressedDispatcher
            .addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    findNavController().navigate(
                        CustomerRegistrationFragmentDirections
                            .actionCustomerRegistrationFragmentToCustomerListFragment(false)
                    )
                }
            })

        return binding.root
    }
}