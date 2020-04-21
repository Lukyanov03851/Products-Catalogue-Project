package ua.lukyanov.catalogue.data.dao

import androidx.room.*
import ua.lukyanov.catalogue.data.entity.ProductRef

@Dao
interface ProductsDao {

    @Query("SELECT * FROM products")
    suspend fun getAllProducts(): List<ProductRef>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(productRef : ProductRef)

    @Delete
    suspend fun deleteProduct(productRef: ProductRef)
}