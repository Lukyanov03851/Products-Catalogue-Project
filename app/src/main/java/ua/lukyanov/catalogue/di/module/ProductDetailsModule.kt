package ua.lukyanov.catalogue.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap
import ua.lukyanov.catalogue.di.util.DaggerViewModelFactory
import ua.lukyanov.catalogue.di.util.ViewModelKey
import ua.lukyanov.catalogue.ui.detail.ProductDetailsFragment
import ua.lukyanov.catalogue.ui.detail.ProductDetailsViewModel

@Module
abstract class ProductDetailsModule {

    @ContributesAndroidInjector(modules = [ProvideProductDetailsViewModel::class])
    abstract fun bindProductDetailsFragment(): ProductDetailsFragment

    @Module
    abstract class ProvideProductDetailsViewModel {

        @Binds
        abstract fun bindDaggerViewModelFactory(daggerViewModelFactory: DaggerViewModelFactory): ViewModelProvider.Factory

        @Binds
        @IntoMap
        @ViewModelKey(ProductDetailsViewModel::class)
        abstract fun bindProductDetailsViewModel(productDetailsViewModel: ProductDetailsViewModel): ViewModel

    }

}