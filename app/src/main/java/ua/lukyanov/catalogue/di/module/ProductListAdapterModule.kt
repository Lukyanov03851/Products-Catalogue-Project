package ua.lukyanov.catalogue.di.module

import dagger.Module
import dagger.Provides
import ua.lukyanov.catalogue.ui.adapter.ProductListAdapter

@Module
class ProductListAdapterModule {
    @Provides
    fun provideAlbumAdapter(): ProductListAdapter {
        return ProductListAdapter()
    }
}