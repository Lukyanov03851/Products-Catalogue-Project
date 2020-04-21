package ua.lukyanov.catalogue.model

import com.google.gson.annotations.SerializedName

data class SearchProductsResponse(
    @SerializedName("site_id")
    val siteId: String,
    @SerializedName("query")
    val query: String,
    @SerializedName("results")
    val results: List<Product>) {

    override fun toString(): String {
        return "SearchProductsResponse(siteId='$siteId', query='$query', results=$results)"
    }
}