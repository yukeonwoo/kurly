package com.wkylast.presentation.goods.model

import com.wkylast.model.entity.ProductsEntity

data class Product(
    val id: Int?,
    val name: String?,
    val image: String?,
    val heart: Boolean,
    val originalPrice: Int?,
    val discountedPrice: Int?,
    val isSoldOut: Boolean?
) {
    val discountRate: Int? = if (originalPrice != null && discountedPrice != null && originalPrice > discountedPrice) {
        ((originalPrice - discountedPrice) / originalPrice.toFloat() * 100).toInt()
    } else null
}

fun ProductsEntity.ProductEntity.toModel(): Product {
    return Product(
        id = id,
        name = name,
        image = image,
        heart = false,
        originalPrice = originalPrice,
        discountedPrice = discountedPrice,
        isSoldOut = isSoldOut
    )
}