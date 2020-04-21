package ua.lukyanov.catalogue.ui.recent

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

class RecentProductListViewModel @Inject constructor(private val mProductsRepository: ProductsRepository): ViewModel() {

    private val dataLoading = MutableLiveData<Boolean>()
    private val isDataLoadingError = MutableLiveData<Boolean>()
    private val dataLoadingErrorMessage = MutableLiveData<String>()
    val productsMutableData: MutableLiveData<List<Product>> = MutableLiveData()

    fun isLoading(): LiveData<Boolean> = dataLoading
    fun hasError(): LiveData<Boolean> = isDataLoadingError
    fun errorMessage(): LiveData<String> = dataLoadingErrorMessage

    fun loadRecentProductsList(){
        dataLoading.value = true

        viewModelScope.launch {
            val resultProductList = arrayListOf<Product>()
            val productsRefs = mProductsRepository.getRecentProductRefs().sortedByDescending { it.date }

            productsRefs.forEach{ productRef ->
                val productResult: Resource<Product> = mProductsRepository.getProductById(productRef.productId)

                if (productResult.status == Status.SUCCESS) {
                    resultProductList.add(productResult.data!!)
                }
            }

            if (resultProductList.isNotEmpty()) {
                isDataLoadingError.value = false
                productsMutableData.value = resultProductList
            } else {
                isDataLoadingError.value = true
                productsMutableData.value = emptyList()
                dataLoadingErrorMessage.value = "Nothing found"
            }

            dataLoading.value = false
        }
    }

}