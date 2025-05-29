package com.wkylast.presentation.goods.state

import androidx.compose.runtime.Immutable
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Immutable
data class GoodsUiState(
    val isLoading: Boolean = true,
    val isRefreshing: Boolean = false,
    val sections: ImmutableList<SectionState> = persistentListOf()
)