package com.example.customerlist.customerdetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.customerlist.R
import com.example.customerlist.database.CustomerDatabase
import com.example.customerlist.databinding.FragmentCustomerDetailBinding

class CustomerDetailFragment : Fragment() {

    private lateinit var binding: FragmentCustomerDetailBinding
    private lateinit var customerDetailViewModel: CustomerDetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_customer_detail, container, false
        )

        val application = requireNotNull(this.activity).application
        val arguments = CustomerDetailFragmentArgs.fromBundle(requireArguments())

        val dataSource = CustomerDatabase.getInstance(application).customerDatabaseDao
        val viewModelFactory = CustomerDetailViewModelFactory(arguments.customerKey, dataSource)

        customerDetailViewModel =
            ViewModelProvider(this, viewModelFactory).get(CustomerDetailViewModel::class.java)

        customerDetailViewModel.getCustomer().observe(viewLifecycleOwner, {
            if (it !== null) {
                binding.customer = it
                binding.customerAvatarview.text = it.name
            }
        })

        binding.customerDetailViewModel = customerDetailViewModel
        binding.lifecycleOwner = this

        initializeAdapter()

        return binding.root
    }

    private fun initializeAdapter() {
        val adapter = PhoneAdapter()
        binding.phoneList.adapter = adapter

        customerDetailViewModel.phones.observe(viewLifecycleOwner, { customers ->
            customers?.let {
                adapter.submitList(it)
            }
        })
    }
}