package com.wkylast.presentation.product.state

import androidx.compose.runtime.Immutable
import com.wkylast.presentation.base.model.ViewState
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Immutable
data class ProductUiState(
    val isLoading: Boolean = true,
    val isRefreshing: Boolean = false,
    val sections: ImmutableList<SectionState> = persistentListOf()
): ViewState