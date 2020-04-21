package ua.lukyanov.catalogue.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "products")
data class ProductRef(
    @PrimaryKey
    @ColumnInfo(name = "productId")
    val productId: String,

    @ColumnInfo(name = "date")
    val date: Long
)
