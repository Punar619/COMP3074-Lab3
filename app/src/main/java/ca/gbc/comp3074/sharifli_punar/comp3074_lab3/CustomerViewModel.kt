package ca.gbc.comp3074.sharifli_punar.comp3074_lab3

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import ca.gbc.comp3074.sharifli_punar.comp3074_lab3.data.AppDatabase
import ca.gbc.comp3074.sharifli_punar.comp3074_lab3.data.Customer
import ca.gbc.comp3074.sharifli_punar.comp3074_lab3.data.CustomerRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class CustomerViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: CustomerRepository

    val allCustomers: StateFlow<List<Customer>>

    init {
        val customerDao = AppDatabase.getDatabase(application).customerDao()
        repository = CustomerRepository(customerDao)
        allCustomers = repository.allCustomers.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )
    }

    fun insert(customer: Customer) = viewModelScope.launch {
        repository.insert(customer)
    }

    fun delete(customer: Customer) = viewModelScope.launch {
        repository.delete(customer)
    }
}