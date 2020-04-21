package ua.lukyanov.catalogue.di.component

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import ua.lukyanov.catalogue.AppCatalogue
import ua.lukyanov.catalogue.di.module.*
import javax.inject.Singleton

@Singleton
@Component(modules = [AndroidInjectionModule::class, NetworkModule::class, DatabaseModule::class, MainActivityModule::class])
interface AppComponent : AndroidInjector<AppCatalogue> {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

    override fun inject(app: AppCatalogue)
}