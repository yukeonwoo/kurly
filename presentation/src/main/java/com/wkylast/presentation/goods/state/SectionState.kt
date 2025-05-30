package com.wkylast.presentation.goods.state

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import com.wkylast.presentation.goods.model.Product
import com.wkylast.presentation.goods.type.UiContentType
import kotlinx.collections.immutable.ImmutableList

@Stable
sealed interface SectionState {

    val uiContentType: UiContentType

    @Immutable
    data class Title(
        val title: String?,
        override val uiContentType: UiContentType,
    ): SectionState

    @Immutable
    data class Horizontal(
        val products: ImmutableList<Product>,
        override val uiContentType: UiContentType
    ): SectionState

    @Immutable
    data class Vertical(
        val product: Product,
        override val uiContentType: UiContentType
    ): SectionState

    @Immutable
    data class Grid(
        val products: ImmutableList<Product>,
        override val uiContentType: UiContentType
    ): SectionState

    @Immutable
    data class Divider(
        override val uiContentType: UiContentType
    ): SectionState
}