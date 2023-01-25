package ru.alex009.redwoodapp.navigation

import androidx.compose.runtime.Composable

interface FlatNavigationDsl {
    fun registerScreen(
        uri: String,
        screen: @Composable (Navigator, Map<String, String>) -> Unit
    )

    fun registerNavigation(
        uri: String,
        childNavigation: (Navigator, Map<String, String>) -> NavigationHost
    )
}

expect fun navigation(
    startDestination: String,
    block: FlatNavigationDsl.() -> Unit
): NavigationHost
