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

class CustomerListFragment : Fragment() {

    private lateinit var binding: FragmentCustomerListBinding
    private lateinit var customerListViewModel: CustomerListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_customer_list, container, false
        )

        val application = requireNotNull(this.activity).application
        val dataSource = CustomerDatabase.getInstance(application).customerDatabaseDao

        val viewModelFactory = CustomerListViewModelFactory(dataSource)
        customerListViewModel =
            ViewModelProvider(this, viewModelFactory).get(CustomerListViewModel::class.java)

        customerListViewModel.navigateToCustomerRegistration.observe(viewLifecycleOwner, {
            if (it == true) navigateToRegistration()
        })

        customerListViewModel.navigateToCustomerDetails.observe(viewLifecycleOwner, { customerKey ->
            customerKey?.let {
                navigateToDetail(customerKey)
            }
        })

        binding.customerListViewModel = customerListViewModel
        binding.lifecycleOwner = this

        initiateAdapter()

        return binding.root
    }

    private fun initiateAdapter() {
        val adapter = CustomerAdapter(CustomerListener { customerId ->
            customerListViewModel.onCustomerClick(customerId)
        })
        binding.customerList.adapter = adapter

        customerListViewModel.customers.observe(viewLifecycleOwner, { customers ->
            customers?.let {
                adapter.submitList(it)
            }
        })
    }

    private fun navigateToRegistration() {
        this.findNavController().navigate(
            CustomerListFragmentDirections
                .actionCustomerListFragmentToCustomerRegistrationFragment()
        )
        customerListViewModel.doneNavigatingToRegistration()
    }

    private fun navigateToDetail(customerKey: Long) {
        this.findNavController().navigate(
            CustomerListFragmentDirections
                .actionCustomerListFragmentToCustomerDetailFragment(customerKey)
        )
        customerListViewModel.doneNavigatingToDetails()
    }
}