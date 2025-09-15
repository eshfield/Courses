package com.example.courses.navigation

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController

@Composable
fun AppNavigationBar(
    navController: NavHostController,
    currentDestination: NavDestination?,
) {
    Column {
        HorizontalDivider(thickness = 1.5.dp)
        NavigationBar {
            navBarDestinations.forEach { destination ->
                val selected = currentDestination?.hierarchy?.any {
                    it.hasRoute(destination::class)
                } == true
                NavigationBarItem(
                    selected = selected,
                    onClick = {
                        navController.navigate(destination) {
                            popUpTo(0) { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    icon = {
                        Icon(
                            painter = painterResource(destination.icon),
                            contentDescription = stringResource(destination.label),
                            tint = if (selected) MaterialTheme.colorScheme.primary
                            else Color.Unspecified,
                        )
                    },
                    label = {
                        Text(
                            text = stringResource(destination.label),
                            style = MaterialTheme.typography.labelMedium,
                            color = if (selected) MaterialTheme.colorScheme.primary
                            else Color.Unspecified,
                        )
                    },
                )
            }
        }
    }
}
