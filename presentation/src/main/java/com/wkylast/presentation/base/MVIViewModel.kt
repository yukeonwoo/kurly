package com.wkylast.presentation.base

import androidx.lifecycle.ViewModel
import com.wkylast.presentation.base.model.ViewIntent

abstract class MVIViewModel<INTENT: ViewIntent> : ViewModel() {
    abstract fun dispatchIntent(intent: INTENT)
}