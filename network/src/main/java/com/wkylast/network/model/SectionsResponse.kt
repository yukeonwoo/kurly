package com.wkylast.network.model

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
        val url: String?,
    )

    @Serializable
    data class SectionPaging(
        val nextPage: Int?,
    )
}
