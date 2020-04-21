package ua.lukyanov.catalogue.di.module

import androidx.recyclerview.widget.LinearLayoutManager
import dagger.Module
import dagger.Provides
import ua.lukyanov.catalogue.ui.MainActivity
import ua.lukyanov.catalogue.ui.list.ProductListAdapter

@Module
class ProductListAdapterModule {

    @Provides
    fun provideAlbumAdapter(): ProductListAdapter {
        return ProductListAdapter()
    }

    @Provides
    fun provideLayoutManager(context: MainActivity) : LinearLayoutManager {
        return LinearLayoutManager(context)
    }
}