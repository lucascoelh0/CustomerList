package com.example.customerlist.customerlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.customerlist.R
import com.example.customerlist.database.CustomerDatabase
import com.example.customerlist.databinding.FragmentCustomerListBinding
import com.google.android.material.snackbar.Snackbar

class CustomerListFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding: FragmentCustomerListBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_customer_list, container, false
        )

        val application = requireNotNull(this.activity).application
        val dataSource = CustomerDatabase.getInstance(application).customerDatabaseDao

        val viewModelFactory = CustomerListViewModelFactory(dataSource)
        val customerListViewModel =
            ViewModelProvider(this, viewModelFactory).get(CustomerListViewModel::class.java)

        binding.customerListViewModel = customerListViewModel
        binding.lifecycleOwner = this

        customerListViewModel.navigateToCustomerRegistration.observe(viewLifecycleOwner, {
            if (it == true) {
                this.findNavController().navigate(
                    CustomerListFragmentDirections
                        .actionCustomerListFragmentToCustomerRegistrationFragment()
                )
                customerListViewModel.doneNavigatingToRegistration()
            }
        })

        val adapter = CustomerAdapter(CustomerListener { customerId ->
            customerListViewModel.onCustomerClick(customerId)
        })
        binding.customerList.adapter = adapter

        customerListViewModel.customers.observe(viewLifecycleOwner, { customers ->
            customers?.let {
                adapter.submitList(it)
            }
        })

        customerListViewModel.navigateToCustomerDetails.observe(viewLifecycleOwner, { customer ->
            customer?.let {
                this.findNavController().navigate(
                    CustomerListFragmentDirections
                        .actionCustomerListFragmentToCustomerDetailFragment(customer)
                )
                customerListViewModel.doneNavigatingToDetails()
            }
        })

        return binding.root
    }
}