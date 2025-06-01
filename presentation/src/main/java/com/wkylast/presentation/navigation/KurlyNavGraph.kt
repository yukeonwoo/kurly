package com.wkylast.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.wkylast.presentation.product.view.ProductScreen

@Composable
fun KurlyNavGraph(
    navController: NavHostController = rememberNavController()
) {
    NavHost(navController = navController, startDestination = NavItem.Product.route) {
        composable(
            route = NavItem.Product.route
        ) {
            ProductScreen()
        }
    }
}