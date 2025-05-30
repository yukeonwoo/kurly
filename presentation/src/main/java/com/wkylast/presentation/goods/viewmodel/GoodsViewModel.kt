package com.wkylast.presentation.goods.viewmodel

import androidx.lifecycle.viewModelScope
import com.wkylast.domain.usecase.GetProductsUseCase
import com.wkylast.domain.usecase.GetSectionsUseCase
import com.wkylast.model.entity.SectionsEntity
import com.wkylast.model.type.UiType
import com.wkylast.presentation.base.MVIViewModel
import com.wkylast.presentation.base.model.ViewIntent
import com.wkylast.presentation.goods.model.Product
import com.wkylast.presentation.goods.model.toModel
import com.wkylast.presentation.goods.state.GoodsUiState
import com.wkylast.presentation.goods.state.SectionState
import com.wkylast.presentation.goods.type.UiContentType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope
import javax.inject.Inject

@HiltViewModel
class GoodsViewModel @Inject constructor(
    private val getSectionsUseCase: GetSectionsUseCase,
    private val getProductsUseCase: GetProductsUseCase
): MVIViewModel<GoodsViewModel.Intent>() {

    private val _uiState = MutableStateFlow(GoodsUiState())
    val uiState = _uiState.asStateFlow()

    private var nextPage: Int? = LOAD_SECTION_DEFAULT_PAGE

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
                loadGoods(sectionEntity)
                updateNextPage(sectionEntity.nextPage)
            }

            setRefreshing(false)
        }
    }

    private fun loadGoods(sections: SectionsEntity) {
        viewModelScope.launch {
            val productResults: List<PersistentList<Product>> = supervisorScope {
                sections.data.map { entity ->
                    async {
                        kotlin.runCatching {
                            getProductsUseCase(entity.id ?: 0).data.map {
                                it.toModel()
                            }.toPersistentList()
                        }.getOrElse { e ->
                            persistentListOf<Product>()
                        }
                    }
                }
            }.awaitAll()

            val result = getSectionStateFromProducts(
                sections = sections,
                productResults = productResults
            )

            setSection(
                initSection = nextPage == DEFAULT_BUFFER_SIZE,
                sections = result
            )
        }
    }

    private fun getSectionStateFromProducts(sections: SectionsEntity, productResults: List<PersistentList<Product>>):
            List<SectionState>{
        val result = arrayListOf<SectionState>()

        sections.data.forEachIndexed { index, entity ->
            val products = productResults[index]

            if (products.isNotEmpty()) {
                result.add(
                    SectionState.Title(
                        title = entity.title,
                        uiContentType = UiContentType.TITLE
                    )
                )

                when (entity.type) {
                    UiType.HORIZONTAL -> {
                        result.add(
                            SectionState.Horizontal(
                                products = products,
                                uiContentType = UiContentType.HORIZONTAL
                            )
                        )
                        result.add(
                            SectionState.Divider(
                                uiContentType = UiContentType.DIVIDER
                            )
                        )
                    }

                    UiType.VERTICAL -> {
                        products.map { product ->
                            result.add(
                                SectionState.Vertical(
                                    product = product,
                                    uiContentType = UiContentType.VERTICAL
                                )
                            )
                        }
                    }

                    UiType.GRID -> {
                        result.add(
                            SectionState.Grid(
                                products = products.take(GRID_TYPE_DEFAULT_COUNT).toPersistentList(),
                                uiContentType = UiContentType.GRID
                            )
                        )
                        result.add(
                            SectionState.Divider(
                                uiContentType = UiContentType.DIVIDER
                            )
                        )
                    }

                    else -> Unit
                }
            }
        }

        return result
    }

    private fun setRefreshing(isRefreshing: Boolean) {
        _uiState.update { currentState ->
            currentState.copy(
                isRefreshing = isRefreshing
            )
        }
    }

    private fun updateNextPage(page: Int?) {
        nextPage = page
    }

    private fun setSection(initSection: Boolean, sections: List<SectionState>) {
        _uiState.update { currentState ->
            currentState.copy(
                sections = if (initSection) {
                    sections.toPersistentList()
                } else {
                    (currentState.sections + sections).toPersistentList()
                }
            )
        }
    }

    sealed class Intent: ViewIntent {
        data class LoadSection(val page: Int): Intent()
    }

    companion object {
        private const val LOAD_SECTION_DEFAULT_PAGE = 1
        private const val GRID_TYPE_DEFAULT_COUNT = 6
    }
}