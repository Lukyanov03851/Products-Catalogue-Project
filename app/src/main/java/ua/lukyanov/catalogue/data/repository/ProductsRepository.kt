package ua.lukyanov.catalogue.data.repository

import ua.lukyanov.catalogue.data.dao.ProductsDao
import ua.lukyanov.catalogue.data.entity.ProductRef
import ua.lukyanov.catalogue.model.Product
import ua.lukyanov.catalogue.model.SearchProductsResponse
import ua.lukyanov.catalogue.network.Network
import ua.lukyanov.catalogue.network.ProductApiService
import ua.lukyanov.catalogue.util.Resource
import ua.lukyanov.catalogue.util.Status
import javax.inject.Inject

class ProductsRepository @Inject constructor (
    private val productListService: ProductApiService,
    private val productDao: ProductsDao
) {

    suspend fun findProducts(query: String): Resource<List<Product>>{
        val response: Resource<SearchProductsResponse> = Network.request(productListService.findProductsAsync(query))

        return when (response.status) {
            Status.SUCCESS -> Resource.success(response.data?.results!!)
            else -> Resource.error(response.message!!)
        }
    }

    suspend fun getProductById(productId: String): Resource<Product>{
        return Network.request(productListService.getProductByIdAsync(productId))
    }

    suspend fun dbSaveLastVisitedProduct(product: Product) {
        productDao.insert(ProductRef(product.id, System.currentTimeMillis()))

        val productsList = productDao.getAllProducts()

        if(productsList.size > 5){
            val sortedList = productsList.sortedBy { it.date }
            val oldestProduct = sortedList.first()
            productDao.deleteProduct(oldestProduct)
        }
    }

    suspend fun getRecentProductRefs(): List<ProductRef> = productDao.getAllProducts()

}