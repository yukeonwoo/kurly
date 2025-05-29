package com.wkylast.domain.repository

import com.wkylast.model.entity.SectionsEntity

interface KurlyRepository {
    suspend fun getSections(
        page: Int,
    ): SectionsEntity
}