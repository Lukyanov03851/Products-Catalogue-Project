package ua.lukyanov.catalogue.di.module

import dagger.Module
import dagger.Provides
import ua.lukyanov.catalogue.data.AppDatabase
import ua.lukyanov.catalogue.data.dao.ProductsDao

@Module
class ProductsDatabaseModule {

    @Provides
    fun provideProductsDao(database: AppDatabase): ProductsDao {
        return database.productDao()
    }
}