package com.wkylast.network.model

import com.wkylast.model.entity.ProductsEntity
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

fun ProductsResponse.toEntity(): ProductsEntity {
    return ProductsEntity(
        data = data?.map {
            ProductsEntity.ProductEntity(
                id = it.id,
                name = it.name,
                image = it.image,
                originalPrice = it.originalPrice,
                discountedPrice = it.discountedPrice,
                isSoldOut = it.isSoldOut,
            )
        }.orEmpty()
    )
}