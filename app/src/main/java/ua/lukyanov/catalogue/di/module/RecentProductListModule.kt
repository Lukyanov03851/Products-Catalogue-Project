package ua.lukyanov.catalogue.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap
import ua.lukyanov.catalogue.di.util.DaggerViewModelFactory
import ua.lukyanov.catalogue.di.util.ViewModelKey
import ua.lukyanov.catalogue.ui.recent.RecentProductListFragment
import ua.lukyanov.catalogue.ui.recent.RecentProductListViewModel

@Module
abstract class RecentProductListModule {

    @ContributesAndroidInjector(modules = [ProvideViewModel::class])
    abstract fun bindListFragment(): RecentProductListFragment

    @Module
    abstract class ProvideViewModel {

        @Binds
        abstract fun bindDaggerViewModelFactory(daggerViewModelFactory: DaggerViewModelFactory): ViewModelProvider.Factory

        @Binds
        @IntoMap
        @ViewModelKey(RecentProductListViewModel::class)
        abstract fun bindListViewModel(listViewModel: RecentProductListViewModel): ViewModel

    }

}