package ua.lukyanov.catalogue.di.module

import dagger.Module
import dagger.android.ContributesAndroidInjector
import ua.lukyanov.catalogue.ui.MainActivity

@Module
abstract class MainActivityModule {

    @ContributesAndroidInjector(modules = [
        ProductListModule::class,
        RecentProductListModule::class,
        ProductDetailsModule::class,
        ProductsDatabaseModule::class,
        ProductListAdapterModule::class
    ])
    abstract fun bindMainActivity(): MainActivity

}