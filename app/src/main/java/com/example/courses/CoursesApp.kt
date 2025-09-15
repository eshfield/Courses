package com.example.courses

import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.courses.navigation.AppNavigationBar
import com.example.courses.navigation.AuthGraph
import com.example.courses.navigation.NavHomeGraph
import com.example.courses.navigation.Route
import com.example.courses.navigation.accountGraph
import com.example.courses.navigation.authGraph
import com.example.courses.navigation.bookmarksGraph
import com.example.courses.navigation.getRoute
import com.example.courses.navigation.homeGraph
import com.example.courses.navigation.routesWithoutBottomBar

@Composable
fun CoursesApp() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val currentRoute = navBackStackEntry.getRoute()

    Scaffold(
        bottomBar = {
            if (currentRoute !in routesWithoutBottomBar) {
                AppNavigationBar(navController, currentDestination)
            }
        },
    ) { innerPadding ->
        NavHost(
            navController,
            startDestination = AuthGraph,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = dimensionResource(R.dimen.medium))
                .consumeWindowInsets(innerPadding)
                .imePadding(),
        ) {
            authGraph(
                onSignIn = { navController.navigate(NavHomeGraph) },
            )
            homeGraph(
                navigateBack = { navController.popBackStack() },
                onNavigateToCourseDetails = { id ->
                    navController.navigate(Route.CourseDetails(id))
                },
            )
            bookmarksGraph()
            accountGraph()
        }
    }
}
