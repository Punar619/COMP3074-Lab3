package ca.gbc.comp3074.sharifli_punar.comp3074_lab3.data

import kotlinx.coroutines.flow.Flow

class CustomerRepository(private val customerDao: CustomerDao) {

    val allCustomers: Flow<List<Customer>> = customerDao.getAllCustomers()

    suspend fun insert(customer: Customer) {
        customerDao.insertCustomer(customer)
    }

    suspend fun delete(customer: Customer) {
        customerDao.deleteCustomer(customer)
    }
}