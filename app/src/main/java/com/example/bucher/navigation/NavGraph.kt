package com.example.bucher.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.bucher.screens.PdfScreen
import com.example.bucher.screens.PdfViewer
import com.example.bucher.viewModel.PdfViewModel

@Composable
fun NavGraph(viewModel: PdfViewModel) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Routes.SHELF
    ) {
        composable(
            route = Routes.SHELF
        ){
            PdfScreen(viewModel,onBookClick = {navController.navigate(Routes.VIEWER)})
        }
        composable(
            route = Routes.VIEWER
        ){
            PdfViewer(
                uri = viewModel.selectedBook!!.uri,
                onBack = { navController.popBackStack() }
            )
        }

    }
}