package com.wkylast.network.model

import kotlinx.serialization.Serializable

@Serializable
data class ProductsResponse(
    val data: List<ProductResponse>?,
) {
    @Serializable
    data class ProductResponse(
        val id: Int?,
        val name: String?,
        val image: String?,
        val originalPrice: Int?,
        val discountedPrice: Int?,
        val isSoldOut: Boolean?,
    )
}