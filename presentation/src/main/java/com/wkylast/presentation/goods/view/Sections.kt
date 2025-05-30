package com.wkylast.presentation.goods.view

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
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
               is SectionState.Title -> {
                   SectionTitle(item.title ?: "")
               }
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

@Composable
fun SectionTitle(
    title: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = title,
        modifier = modifier.padding(16.dp),
        style = MaterialTheme.typography.titleLarge
    )
}