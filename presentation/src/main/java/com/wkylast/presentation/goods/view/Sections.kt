package com.wkylast.presentation.goods.view

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import com.wkylast.presentation.goods.state.SectionState
import kotlinx.collections.immutable.ImmutableList
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map

@Composable
fun Sections(
    sections: ImmutableList<SectionState>,
    isLoading: Boolean,
    listState: LazyListState = rememberLazyListState(),
    onLoadMore: () -> Unit,
) {
   LazyColumn(
       state = listState,
   ) {
       items(
           items = sections,
           contentType = { it.uiContentType }
       ) { item ->
           when (item) {
               is SectionState.Title -> Unit
               is SectionState.Horizontal -> Unit
               is SectionState.Vertical -> Unit
               is SectionState.Grid -> Unit
               is SectionState.Divider -> Unit
           }
       }
   }

    LaunchedEffect(listState) {
        snapshotFlow { listState.layoutInfo }
            .map { layoutInfo ->
                val visibleItems = layoutInfo.visibleItemsInfo
                val totalItems = layoutInfo.totalItemsCount
                val lastVisibleIndex = visibleItems.lastOrNull()?.index ?: 0
                lastVisibleIndex to totalItems
            }
            .distinctUntilChanged()
            .collect { (lastVisible, total) ->
                if (lastVisible >= total -2 && !isLoading) {
                    onLoadMore()
                }
            }
    }
}