package com.wkylast.presentation.navigation

sealed class NavItem(val route: String) {
    data object Product: NavItem(SCREEN_PRODUCT)
}

private const val SCREEN_PRODUCT = "product"