package com.wkylast.network.model

import com.wkylast.model.entity.SectionsEntity
import com.wkylast.model.type.UiType
import kotlinx.serialization.Serializable

@Serializable
data class SectionsResponse(
    val data: List<SectionResponse>?,
    val paging: SectionPaging?,
) {
    @Serializable
    data class SectionResponse(
        val id: Int?,
        val type: String?,
        val title: String?,
        val url: String?
    )

    @Serializable
    data class SectionPaging(
        val next_page: Int?,
    )
}

fun SectionsResponse.toEntity(): SectionsEntity {
    return SectionsEntity(
        data = data?.map {
            SectionsEntity.SectionEntity(
                id = it.id,
                type = UiType.from(it.type),
                title = it.title,
                url = it.url,
            )
        }.orEmpty(),
        nextPage = paging?.next_page,
    )
}