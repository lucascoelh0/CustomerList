package com.example.customerlist.customerdetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.Phonelist.Phonedetail.PhoneAdapter
import com.example.customerlist.R
import com.example.customerlist.customerlist.CustomerAdapter
import com.example.customerlist.customerlist.CustomerListener
import com.example.customerlist.database.CustomerDatabase
import com.example.customerlist.databinding.FragmentCustomerDetailBinding

class CustomerDetailFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val binding: FragmentCustomerDetailBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_customer_detail, container, false
        )

        val application = requireNotNull(this.activity).application
        val arguments = CustomerDetailFragmentArgs.fromBundle(requireArguments())

        val dataSource = CustomerDatabase.getInstance(application).customerDatabaseDao
        val viewModelFactory = CustomerDetailViewModelFactory(arguments.customerKey, dataSource)

        val customerDetailViewModel =
            ViewModelProvider(this, viewModelFactory).get(CustomerDetailViewModel::class.java)

        binding.customerDetailViewModel = customerDetailViewModel
        binding.lifecycleOwner = this

        val adapter = PhoneAdapter()
        binding.phoneList.adapter = adapter

        customerDetailViewModel.phones.observe(viewLifecycleOwner, { customers ->
            customers?.let {
                adapter.submitList(it)
            }
        })

        customerDetailViewModel.getCustomer().observe(viewLifecycleOwner, {
            if (it !== null) {
                binding.customer = it
                binding.customerAvatarview.text = it.name
            }
        })

        return binding.root
    }
}