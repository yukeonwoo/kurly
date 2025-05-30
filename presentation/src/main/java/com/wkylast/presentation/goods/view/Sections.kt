package com.wkylast.presentation.goods.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times
import coil.compose.AsyncImage
import com.wkylast.model.type.UiType
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
       modifier = Modifier.fillMaxSize(),
       state = listState
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
               is SectionState.Vertical -> {
                   SectionVertical(
                       product = item.product,
                       onHeartClick = onHeartClick
                   )
               }
               is SectionState.Grid -> {
                   SectionGrid(
                       products = item.products,
                       onHeartClick = onHeartClick
                   )
               }
               is SectionState.Divider -> {
                   HorizontalDivider(
                       color = Color.Gray,
                       thickness = 0.5.dp,
                       modifier = Modifier.padding(
                           vertical = 20.dp
                       )
                   )
               }
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
                uiType = UiType.HORIZONTAL,
                titleLines = 2,
                onHeartClick = onHeartClick
            )
        }
    }
}

@Composable
fun SectionVertical(
    product: Product,
    modifier: Modifier = Modifier,
    onHeartClick : (productId: Int) -> Unit = {}
) {
    Product(
        product = product,
        modifier = modifier.fillMaxWidth(),
        imageModifier = Modifier
            .fillMaxWidth()
            .aspectRatio(6f / 4f),
        uiType = UiType.VERTICAL,
        titleLines = 1,
        onHeartClick = onHeartClick
    )
}

@Composable
fun SectionGrid(
    products: ImmutableList<Product>,
    modifier: Modifier = Modifier,
    onHeartClick : (productId: Int) -> Unit = {}
) {
    val rowCount = (products.size + 2) / 3
    val gridHeight = rowCount * 280.dp

    LazyVerticalGrid(
        modifier = modifier
            .fillMaxWidth()
            .height(gridHeight),
        columns = GridCells.Fixed(3),
        contentPadding = PaddingValues(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        userScrollEnabled = false
    ) {
        items(
            items = products,
            key = { item ->
                item.id ?: 0
            }
        ) { product ->
            Product(
                modifier = Modifier.width(150.dp),
                imageModifier = Modifier
                    .size(150.dp, 200.dp),
                titleLines = 2,
                product = product,
                uiType = UiType.GRID,
                onHeartClick = onHeartClick,
            )
        }
    }
}

@Composable
fun Product(
    product: Product,
    modifier: Modifier = Modifier,
    imageModifier: Modifier = Modifier,
    uiType: UiType = UiType.HORIZONTAL,
    titleLines: Int = Int.MAX_VALUE,
    onHeartClick : (productId: Int) -> Unit = {}
) {
    Box(
        modifier = modifier
    ) {
        Column {
            AsyncImage(
                modifier = imageModifier,
                model = product.image,
                contentScale = ContentScale.Crop,
                contentDescription = stringResource(R.string.goods_content_description)
            )

            Text(
                text = product.name ?: "",
                overflow = TextOverflow.Ellipsis,
                maxLines = titleLines,
            )

            if (product.originalPrice != null) {
                Price(
                    originalPrice = product.originalPrice,
                    discountedPrice = product.discountedPrice,
                    discountRate = product.discountRate,
                    uiType = uiType
                )
            }
        }

        IconButton(
            onClick = {
                onHeartClick(product.id ?: 0)
            },
            modifier = Modifier
                .align(Alignment.TopEnd)
                .size(48.dp)
                .padding(4.dp)
        ) {
            Icon(
                 painter = if (product.heart) {
                    painterResource(R.drawable.ic_btn_heart_on)
                } else {
                    painterResource(R.drawable.ic_btn_heart_off)
                },
                contentDescription = stringResource(R.string.goods_like),
                tint = Color.Unspecified
            )
        }

    }
}

@Composable
fun Price(
    originalPrice: Int,
    discountedPrice: Int?,
    discountRate: Int?,
    uiType: UiType,
    modifier: Modifier = Modifier,
) {
    if (uiType == UiType.VERTICAL) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (discountedPrice != null) {
                val formatedDiscountedPrice = stringResource(R.string.goods_price_won).format(discountedPrice)
                Text(
                    text = formatedDiscountedPrice,
                    color = Color(0xFA622F), //TODO color 관리
                    style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold)
                )
            }

            if (discountRate != null) {
                val formatedDiscountRate = stringResource(R.string.goods_price_rate).format(discountRate)

                Text(
                    text = formatedDiscountRate,
                    style = MaterialTheme.typography.bodySmall.copy(
                        fontWeight = if (discountedPrice != null) FontWeight.Bold else FontWeight.Normal
                    ),
                    color = Color.Black
                )
            }

            val formatedOriginalPrice = stringResource(R.string.goods_price_won).format(originalPrice)

            Text(
                text = formatedOriginalPrice,
                style = MaterialTheme.typography.bodySmall.copy(textDecoration = TextDecoration.LineThrough),
                color = Color.Gray
            )
        }
    } else {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (discountedPrice != null) {
                    val formatedDiscountedPrice = stringResource(R.string.goods_price_won).format(discountedPrice)
                    Text(
                        text = formatedDiscountedPrice,
                        color = Color(0xFA622F), //TODO color 관리
                        style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold)
                    )
                }

                if (discountRate != null) {
                    val formatedDiscountRate = stringResource(R.string.goods_price_rate).format(discountRate)

                    Text(
                        text = formatedDiscountRate,
                        style = MaterialTheme.typography.bodySmall.copy(
                            fontWeight = if (discountedPrice != null) FontWeight.Bold else FontWeight.Normal
                        ),
                        color = Color.Black
                    )
                }
            }

            val formatedOriginalPrice = stringResource(R.string.goods_price_won).format(originalPrice)

            Text(
                text = formatedOriginalPrice,
                style = MaterialTheme.typography.bodySmall.copy(textDecoration = TextDecoration.LineThrough),
                color = Color.Gray
            )
        }
    }
}