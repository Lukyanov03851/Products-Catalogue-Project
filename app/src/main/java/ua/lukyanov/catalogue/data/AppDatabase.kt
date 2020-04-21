package ua.lukyanov.catalogue.data

import androidx.room.Database
import androidx.room.RoomDatabase
import ua.lukyanov.catalogue.data.dao.ProductsDao
import ua.lukyanov.catalogue.data.entity.ProductRef

@Database(entities = [ProductRef::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun productDao(): ProductsDao

}