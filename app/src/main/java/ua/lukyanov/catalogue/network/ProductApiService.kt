package ua.lukyanov.catalogue.network

import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import ua.lukyanov.catalogue.model.Product
import ua.lukyanov.catalogue.model.SearchProductsResponse

interface ProductApiService {

    @GET(Endpoints.PRODUCT_LIST)
    fun findProductsAsync(@Query("q") query: String ) : Deferred<Response<SearchProductsResponse>>

    @GET(Endpoints.PRODUCT_BY_ID)
    fun getProductByIdAsync(@Path("id") productId: String ) : Deferred<Response<Product>>

}