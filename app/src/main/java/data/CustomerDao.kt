package ca.gbc.comp3074.sharifli_punar.comp3074_lab3.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface CustomerDao {

    // Bütün müştəriləri oxumaq üçün (Flow istifadə edirik ki, məlumat dəyişəndə avtomatik yenilənsin)
    @Query("SELECT * FROM customers")
    fun getAllCustomers(): Flow<List<Customer>>

    // Yeni müştəri əlavə etmək (əgər varsa yeniləsin)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCustomer(customer: Customer)

    // Müştாளini silmək
    @Delete
    suspend fun deleteCustomer(customer: Customer)
}