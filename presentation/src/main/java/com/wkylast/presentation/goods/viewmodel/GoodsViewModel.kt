package com.wkylast.presentation.goods.viewmodel

import com.wkylast.presentation.base.MVIViewModel
import com.wkylast.presentation.base.model.ViewIntent
import com.wkylast.presentation.goods.state.GoodsUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class GoodsViewModel @Inject constructor(): MVIViewModel<GoodsViewModel.Intent>() {

    private val _uiState = MutableStateFlow(GoodsUiState())
    val uiState = _uiState.asStateFlow()


    override fun dispatchIntent(intent: Intent) {
        TODO("Not yet implemented")
    }

    sealed class Intent: ViewIntent {
        data class LoadSection(val page: Int): ViewIntent
    }
}