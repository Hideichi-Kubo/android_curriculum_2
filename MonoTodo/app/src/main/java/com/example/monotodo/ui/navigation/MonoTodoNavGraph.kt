package com.example.monotodo.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.example.monotodo.ui.home.HomeDestination
import com.example.monotodo.ui.home.HomeScreen
import com.example.monotodo.ui.task.TaskCompletedDestination
import com.example.monotodo.ui.task.TaskCompletedScreen
import com.example.monotodo.ui.task.TaskEntryDestination
import com.example.monotodo.ui.task.TaskEntryScreen

@Composable
fun MonoTodoNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navController,
        startDestination = HomeDestination.route,
        modifier = modifier
    ) {
        composable(route = HomeDestination.route) {
            HomeScreen(
                navigateToTaskEntry = { navController.navigate(TaskEntryDestination.route) },
                navigateToTaskCompleted = { navController.navigate(TaskCompletedDestination.route) }
            )
        }
        composable(route = TaskCompletedDestination.route) {
            TaskCompletedScreen(
                navigateToTaskEntry = { navController.navigate(TaskEntryDestination.route) },
                onNavigateUp = { navController.popBackStack() }
            )
        }
        composable(route = TaskEntryDestination.route) {
            TaskEntryScreen(
                navigateBack = { navController.popBackStack(HomeDestination.route, inclusive = false) },
                onNavigateUp = { navController.popBackStack() }
            )

        }
    }
}