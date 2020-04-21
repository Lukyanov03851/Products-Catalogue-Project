package ua.lukyanov.catalogue.ui.detail

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

class ProductDetailsViewModel @Inject constructor(private val mProductsRepository: ProductsRepository): ViewModel() {

    private val loadingState: MutableLiveData<Status> = MutableLiveData()
    private val dataLoadingErrorMessage = MutableLiveData<String>()
    val productMutableData: MutableLiveData<Product> = MutableLiveData()

    fun state(): LiveData<Status> = loadingState

    fun errorMessage(): LiveData<String> = dataLoadingErrorMessage

    fun getProductById(productId: String){
        loadingState.value = Status.LOADING

        viewModelScope.launch{
            val productResult: Resource<Product> = mProductsRepository.getProductById(productId)

            if (productResult.status == Status.SUCCESS) {
                mProductsRepository.dbSaveLastVisitedProduct(productResult.data!!)
                loadingState.value = Status.SUCCESS
                productMutableData.value = productResult.data
            } else {
                loadingState.value = Status.ERROR
                dataLoadingErrorMessage.value = productResult.message
            }
        }
    }

}