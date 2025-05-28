package com.wkylast.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.wkylast.presentation.goods.view.GoodsScreen

@Composable
fun KurlyNavGraph(
    navController: NavHostController = rememberNavController()
) {
    NavHost(navController = navController, startDestination = NavItem.Goods.route) {
        composable(
            route = NavItem.Goods.route
        ) {
            GoodsScreen()
        }
    }
}