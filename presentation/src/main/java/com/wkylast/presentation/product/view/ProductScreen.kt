package com.wkylast.presentation.product.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults
import androidx.compose.material3.pulltorefresh.pullToRefresh
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.wkylast.presentation.product.viewmodel.ProductViewModel

@Preview(showBackground = true)
@Composable
fun ProductScreenPreview() {
    ProductScreen()
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductScreen(
    viewModel: ProductViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val pullRefreshState = rememberPullToRefreshState()

    val onRefresh = remember {
        {
            viewModel.dispatchIntent(
                ProductViewModel.Intent.RefreshSection
            )
        }
    }

    val onLoadMore = remember {
        {
            viewModel.dispatchIntent(
                ProductViewModel.Intent.LoadSection
            )
        }
    }

    val onHeartClick = remember<(productId: Int) -> Unit> {
        { productId ->
            viewModel.dispatchIntent(
                ProductViewModel.Intent.HeartClick(productId)
            )
        }
    }
    
    Box(
        modifier = Modifier
            .fillMaxSize()
            .pullToRefresh(
                state = pullRefreshState,
                isRefreshing = uiState.isRefreshing,
                onRefresh = onRefresh
            )
    ) {
        Sections(
            sections = uiState.sections,
            isLoading = { uiState.isLoading },
            onLoadMore = onLoadMore,
            onHeartClick = onHeartClick
        )

        Loading(
            isLoading = uiState.isLoading
        )

        PullToRefreshDefaults.Indicator(
            modifier = Modifier.align(Alignment.TopCenter),
            isRefreshing = uiState.isRefreshing,
            state = pullRefreshState
        )
    }

    LaunchedEffect(Unit) {
        viewModel.dispatchIntent(
            ProductViewModel.Intent.LoadSection
        )
    }
}