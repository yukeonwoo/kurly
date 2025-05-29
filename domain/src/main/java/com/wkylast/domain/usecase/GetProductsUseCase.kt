package com.wkylast.domain.usecase

import com.wkylast.domain.repository.KurlyRepository
import com.wkylast.model.entity.ProductsEntity
import javax.inject.Inject

class GetProductsUseCase @Inject constructor(
    private val repository: KurlyRepository
) {
    suspend operator fun invoke(
        sectionId: Int
    ): ProductsEntity {
        return repository.getProducts(sectionId)
    }
}