package com.wkylast.model.entity

data class ProductsEntity(
    val data: List<ProductEntity>
) {
    data class ProductEntity(
        val id: Int?,
        val name: String?,
        val image: String?,
        val originalPrice: Int?,
        val discountedPrice: Int?,
        val isSoldOut: Boolean?,
    )
}
