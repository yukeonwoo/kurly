package com.wkylast.presentation.goods.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.wkylast.presentation.R
import com.wkylast.presentation.goods.model.Product
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
    onHeartClick : (productId: Int) -> Unit = {}
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
               is SectionState.Horizontal -> {
                   SectionHorizontal(
                       products = item.products,
                       onHeartClick = onHeartClick
                   )
               }
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

@Composable
fun SectionHorizontal(
    products: ImmutableList<Product>,
    modifier: Modifier = Modifier,
    onHeartClick : (productId: Int) -> Unit = {}
) {
    LazyRow(
        modifier = modifier
            .fillMaxWidth(),
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(
            items = products,
            key = { item ->
                item.id ?: 0
            }
        ) { product ->
            Product(
                product = product,
                modifier = Modifier
                    .width(
                        width = 150.dp
                    ),
                imageModifier = Modifier
                    .size(
                        width = 150.dp,
                        height = 200.dp
                    ),
                onHeartClick = onHeartClick
            )
        }
    }
}

@Composable
fun Product(
    product: Product,
    modifier: Modifier = Modifier,
    imageModifier: Modifier = Modifier,
    onHeartClick : (productId: Int) -> Unit = {}
) {
    Box(
        modifier = modifier
    ) {
        Column {
            AsyncImage(
                modifier = imageModifier,
                model = product.image,
                contentDescription = stringResource(R.string.goods_content_description)
            )
        }

        IconButton(
            onClick = {
                onHeartClick(product.id ?: 0)
            },
            modifier = Modifier
                .align(Alignment.TopEnd)
                .size(24.dp)
                .padding(4.dp)
        ) {
            Icon(
                 painter = if (product.heart) {
                    painterResource(R.drawable.ic_btn_heart_on)
                } else {
                    painterResource(R.drawable.ic_btn_heart_off)
                },
                contentDescription = stringResource(R.string.goods_like)
            )
        }

    }
}