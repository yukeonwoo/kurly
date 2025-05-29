package com.wkylast.domain.repository

import com.wkylast.model.entity.ProductsEntity
import com.wkylast.model.entity.SectionsEntity

interface KurlyRepository {
    suspend fun getSections(
        page: Int,
    ): SectionsEntity

    suspend fun getProducts(
        sectionId: Int,
    ): ProductsEntity
}