package com.example.customerlist.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface CustomerDatabaseDao {

    //Customer
    @Insert
    suspend fun insertCustomer(customer: Customer)

    @Update
    suspend fun updateCustomer(customer: Customer)

    @Query("DELETE FROM customer_table WHERE customerId = :key")
    suspend fun deleteCustomer(key: Long)

    @Query("SELECT * FROM customer_table")
    fun getAllCustomers(): LiveData<List<Customer>>

    //Phone
    @Insert
    suspend fun insertPhone(phone: Phone)

    @Update
    suspend fun updatePhone(phone: Phone)

    @Query("SELECT * FROM phone_table WHERE customer_id = :key")
    fun getCustomerPhones(key: Long): LiveData<List<Phone>>

    @Query("DELETE FROM phone_table WHERE phoneId = :key")
    suspend fun deletePhone(key: Long)

    @Query("DELETE FROM phone_table WHERE customer_id = :key")
    suspend fun deleteAllCustomerPhones(key: Long)
}