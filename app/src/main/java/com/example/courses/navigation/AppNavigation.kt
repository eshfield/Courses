package com.example.courses.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import androidx.navigation.toRoute
import com.example.courses.R
import com.example.courses.feature.account.presentation.ui.AccountScreen
import com.example.courses.feature.bookmarks.presentation.ui.BookmarksScreen
import com.example.courses.feature.home.presentation.ui.CourseDetailsScreen
import com.example.courses.feature.home.presentation.ui.HomeScreen
import com.example.courses.feature.signin.presentation.ui.SignInScreen
import kotlinx.serialization.Serializable

@Serializable
sealed class Route() {
    @Serializable
    data object SignIn : Route()

    @Serializable
    data object Home : Route()

    @Serializable
    data class CourseDetails(val id: Int) : Route()

    @Serializable
    data object Bookmarks : Route()

    @Serializable
    data object Account : Route()

    companion object {
        val subclasses = Route::class.sealedSubclasses
    }
}

val routesWithoutBottomBar = listOf(Route.SignIn)

interface NavBarDestination {
    @get:DrawableRes
    val icon: Int

    @get:StringRes
    val label: Int
}

@Serializable
object AuthGraph

@Serializable
data object NavHomeGraph : NavBarDestination {
    override val icon = R.drawable.nav_home
    override val label = R.string.nav_home
}

@Serializable
data object NavBookmarksGraph : NavBarDestination {
    override val icon: Int = R.drawable.nav_bookmarks
    override val label: Int = R.string.nav_bookmarks
}

@Serializable
data object NavAccountGraph : NavBarDestination {
    override val icon: Int = R.drawable.nav_account
    override val label: Int = R.string.nav_account
}

val navBarDestinations = listOf(NavHomeGraph, NavBookmarksGraph, NavAccountGraph)

fun NavGraphBuilder.authGraph(
    onSignIn: () -> Unit,
) {
    navigation<AuthGraph>(startDestination = Route.SignIn) {
        composable<Route.SignIn> {
            SignInScreen(
                onSignIn = onSignIn,
            )
        }
    }
}

fun NavGraphBuilder.homeGraph(
    onNavigateToCourseDetails: (id: Int) -> Unit,
    navigateBack: () -> Unit,
) {
    navigation<NavHomeGraph>(startDestination = Route.Home) {
        composable<Route.Home> {
            HomeScreen(
                onCourseClick = onNavigateToCourseDetails,
            )
        }
        composable<Route.CourseDetails> {
            CourseDetailsScreen(
                navigateBack = navigateBack,
            )
        }
    }
}

fun NavGraphBuilder.bookmarksGraph() {
    navigation<NavBookmarksGraph>(startDestination = Route.Bookmarks) {
        composable<Route.Bookmarks> {
            BookmarksScreen()
        }
    }
}

fun NavGraphBuilder.accountGraph() {
    navigation<NavAccountGraph>(startDestination = Route.Account) {
        composable<Route.Account> {
            AccountScreen()
        }
    }
}

fun NavBackStackEntry?.getRoute(): Route? {
    val entry = this ?: return null
    val routeSubclass = Route.subclasses.firstOrNull { subclass ->
        entry.destination.hasRoute(subclass)
    } ?: return null
    return entry.toRoute(routeSubclass)
}
