package ua.lukyanov.catalogue.di.module

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import ua.lukyanov.catalogue.data.AppDatabase
import javax.inject.Singleton

private const val PRODUCTS_DATABASE = "catalogue.db"

@Module
class DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(context: Application): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, PRODUCTS_DATABASE).build()
    }

}