package com.wkylast.presentation.navigation

sealed class NavItem(val route: String) {
    data object Goods: NavItem(SCREEN_GOODS)
}

private const val SCREEN_GOODS = "goods"