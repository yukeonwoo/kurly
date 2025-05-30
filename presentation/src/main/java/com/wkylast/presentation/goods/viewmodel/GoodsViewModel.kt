package com.wkylast.presentation.goods.viewmodel

import androidx.lifecycle.viewModelScope
import com.wkylast.domain.usecase.GetSectionsUseCase
import com.wkylast.presentation.base.MVIViewModel
import com.wkylast.presentation.base.model.ViewIntent
import com.wkylast.presentation.goods.state.GoodsUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GoodsViewModel @Inject constructor(
    private val getSectionsUseCase: GetSectionsUseCase,
): MVIViewModel<GoodsViewModel.Intent>() {

    private val _uiState = MutableStateFlow(GoodsUiState())
    val uiState = _uiState.asStateFlow()

    var nextPage: Int? = LOAD_SECTION_DEFAULT_PAGE

    override fun dispatchIntent(intent: Intent) {
        when(intent) {
            is Intent.LoadSection -> {
                loadSection(intent.page)
            }
        }
    }

    private fun loadSection(page: Int = LOAD_SECTION_DEFAULT_PAGE) {
        if (nextPage == null) return

        viewModelScope.launch {
            setRefreshing(true)

            kotlin.runCatching {
                getSectionsUseCase(page)
            }.onFailure {
                // TODO 에러 스낵바
            }.onSuccess { sectionEntity ->

                nextPage = sectionEntity.nextPage
            }

            setRefreshing(false)
        }
    }

    private fun setRefreshing(isRefreshing: Boolean) {
        _uiState.update { currentState ->
            currentState.copy(
                isRefreshing = isRefreshing
            )
        }
    }

    private fun setNextPage(page: Int?) {
        nextPage = page
    }

    sealed class Intent: ViewIntent {
        data class LoadSection(val page: Int): Intent()
    }

    companion object {
        private const val LOAD_SECTION_DEFAULT_PAGE = 1
    }
}