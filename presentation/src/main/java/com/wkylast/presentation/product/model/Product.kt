package com.wkylast.presentation.product.model

import com.wkylast.model.entity.ProductsEntity
import com.wkylast.presentation.common.DiscountUtil

data class Product(
    val id: Int?,
    val name: String?,
    val image: String?,
    val heart: Boolean,
    val originalPrice: Int?,
    val discountedPrice: Int?,
    val isSoldOut: Boolean?
) {
    val discountRate: Int? = DiscountUtil.calculateRate(originalPrice, discountedPrice)
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