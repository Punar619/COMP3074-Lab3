package ca.gbc.comp3074.sharifli_punar.comp3074_lab3

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import ca.gbc.comp3074.sharifli_punar.comp3074_lab3.data.Customer
import ca.gbc.comp3074.sharifli_punar.comp3074_lab3.ui.theme.COMP3074_Lab3Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            COMP3074_Lab3Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    CustomerScreen()
                }
            }
        }
    }
}

@Composable
fun CustomerScreen(viewModel: CustomerViewModel = viewModel()) {
    val customers by viewModel.allCustomers.collectAsState()

    var name by remember { mutableStateOf("") }
    var age by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(text = "Customer Manager (Lab 4)", style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Customer Name") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = age,
            onValueChange = { age = it },
            label = { Text("Age") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = {
                if (name.isNotBlank() && age.isNotBlank()) {
                    val newCustomer = Customer(
                        name = name,
                        age = age.toIntOrNull() ?: 0,
                        isActive = true
                    )
                    viewModel.insert(newCustomer)
                    name = ""
                    age = ""
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Add Customer")
        }

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            items(customers) { customer ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    elevation = CardDefaults.cardElevation(4.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            Text(text = customer.name, style = MaterialTheme.typography.titleMedium)
                            Text(text = "Age: ${customer.age}", style = MaterialTheme.typography.bodyMedium)
                        }
                        Button(onClick = { viewModel.delete(customer) }) {
                            Text("Delete")
                        }
                    }
                }
            }
        }
    }
}