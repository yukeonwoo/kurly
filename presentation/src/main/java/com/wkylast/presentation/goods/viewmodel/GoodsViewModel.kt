package com.wkylast.presentation.goods.viewmodel

import com.wkylast.presentation.base.MVIViewModel
import com.wkylast.presentation.base.model.ViewIntent
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class GoodsViewModel @Inject constructor(): MVIViewModel<GoodsViewModel.Intent>() {

    override fun dispatchIntent(intent: Intent) {
        TODO("Not yet implemented")
    }

    sealed class Intent: ViewIntent {
        data class LoadSection(val page: Int): ViewIntent
    }
}