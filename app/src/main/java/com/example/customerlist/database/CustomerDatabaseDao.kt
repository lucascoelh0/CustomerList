package com.example.customerlist.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface CustomerDatabaseDao {

    //Customer
    @Insert
    suspend fun insertCustomer(customer: Customer)

    @Query("SELECT * FROM customer_table ORDER BY name COLLATE NOCASE")
    fun getAllCustomers(): LiveData<List<Customer>>

    @Query("SELECT * FROM customer_table WHERE customerId = :key")
    fun getCustomerWithId(key: Long): LiveData<Customer>

    @Query("SELECT * FROM customer_table ORDER BY customerId DESC LIMIT 1")
    suspend fun getLastInsertedCustomer(): Customer?

    //Phone
    @Insert
    suspend fun insertPhone(phone: Phone)

    @Query("SELECT * FROM phone_table WHERE customer_id = :key")
    fun getCustomerPhones(key: Long): LiveData<List<Phone>>
}