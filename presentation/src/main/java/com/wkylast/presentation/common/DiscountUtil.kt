package com.wkylast.presentation.common

object DiscountUtil {
    fun calculateRate(originalPrice: Int?, discountedPrice: Int?): Int? {
        return if (originalPrice != null && discountedPrice != null && originalPrice > discountedPrice) {
            ((originalPrice - discountedPrice) / originalPrice.toFloat() * 100).toInt()
        } else null
    }
}