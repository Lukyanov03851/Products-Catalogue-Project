package ua.lukyanov.catalogue.ui.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ua.lukyanov.catalogue.data.repository.ProductsRepository
import ua.lukyanov.catalogue.model.Product
import ua.lukyanov.catalogue.util.Resource
import ua.lukyanov.catalogue.util.Status
import javax.inject.Inject

class ProductListViewModel @Inject constructor(private val mProductsRepository: ProductsRepository): ViewModel() {

    private val dataLoading = MutableLiveData<Boolean>()
    private val isDataLoadingError = MutableLiveData<Boolean>()
    private val dataLoadingErrorMessage = MutableLiveData<String>()
    val productsMutableData: MutableLiveData<List<Product>> = MutableLiveData()

    fun isLoading(): LiveData<Boolean> = dataLoading
    fun hasError(): LiveData<Boolean> = isDataLoadingError
    fun errorMessage(): LiveData<String> = dataLoadingErrorMessage

    fun findProductsList(query: String){
        dataLoading.value = true

        viewModelScope.launch {
            val productsResult: Resource<List<Product>> = mProductsRepository.findProducts(query)

            if (productsResult.status == Status.SUCCESS) {
                if (productsResult.data.isNullOrEmpty()){
                    isDataLoadingError.value = true
                    productsMutableData.value = emptyList()
                    dataLoadingErrorMessage.value = "Nothing found"
                } else {
                    isDataLoadingError.value = false
                    productsMutableData.value = productsResult.data
                }
            } else {
                isDataLoadingError.value = true
                productsMutableData.value = emptyList()
                dataLoadingErrorMessage.value = productsResult.message
            }

            dataLoading.value = false
        }
    }

}