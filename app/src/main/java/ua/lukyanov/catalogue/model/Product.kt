package ua.lukyanov.catalogue.model

import com.google.gson.annotations.SerializedName

data class Product(
    @SerializedName("id")
    val id: String?,

    @SerializedName("thumbnail")
    val thumbnailUrl: String?,

    @SerializedName("title")
    val title: String?,

    @SerializedName("price")
    val price: Double?,

    @SerializedName("currency_id")
    val currency: String?
){
    override fun toString(): String {
        return "Product(id='$id', thumbnailUrl='$thumbnailUrl', title='$title', price=$price, currency='$currency')"
    }
}