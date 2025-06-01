package com.wkylast.model.entity

import com.wkylast.model.type.UiType

data class SectionsEntity(
    val data: List<SectionEntity>,
    val nextPage: Int?,
) {
    data class SectionEntity(
        val id: Int,
        val title: String,
        val type: UiType,
        val url: String,
    )
}