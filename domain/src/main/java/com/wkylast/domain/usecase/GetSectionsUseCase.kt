package com.wkylast.domain.usecase

import com.wkylast.domain.repository.KurlyRepository
import com.wkylast.model.entity.SectionsEntity
import javax.inject.Inject

class GetSectionsUseCase @Inject constructor(
    private val repository: KurlyRepository
) {
    suspend operator fun invoke(
        page: Int,
    ): SectionsEntity {
        return repository.getSections(page)
    }
}