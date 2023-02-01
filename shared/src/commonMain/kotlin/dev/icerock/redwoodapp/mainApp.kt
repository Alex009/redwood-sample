package dev.icerock.redwoodapp

import androidx.compose.runtime.Composable
import app.cash.redwood.layout.api.Constraint
import app.cash.redwood.layout.api.CrossAxisAlignment
import app.cash.redwood.layout.api.MainAxisAlignment
import app.cash.redwood.layout.compose.Column
import app.cash.redwood.layout.compose.Row
import dev.icerock.moko.resources.desc.desc
import dev.icerock.redwood.schema.compose.Text
import org.example.library.MR
import dev.icerock.redwoodapp.navigation.NavigationHost
import dev.icerock.redwoodapp.navigation.Navigator
import dev.icerock.redwoodapp.navigation.navigation
import dev.icerock.redwoodapp.navigation.navigationTabs
import dev.icerock.redwoodapp.screens.DetailsScreen
import dev.icerock.redwoodapp.screens.LoginScreen
import dev.icerock.redwoodapp.screens.PostsList
import dev.icerock.redwoodapp.screens.ProfileScreen

fun mainApp(): NavigationHost {
    return navigation(startDestination = "login") {
        registerScreen(
            uri = "login",
            isToolbarVisible = true
        ) { navigator, _, screenSettings, viewModelOwner ->
            LoginScreen(navigator, screenSettings,viewModelOwner)
        }
        registerScreen(
            uri = "tabs",
            isToolbarVisible = true
        ) { navigator, _, screenSettings, viewModelOwner ->
            Box {
                Text("SecondScreem")
            }
        }
        registerNavigation(
            uri = "sdsd",
            isToolbarVisible = false,
            childNavigation = { navigator, _, _, _ ->
                mainScreenNavigation(navigator)
            }
        )
    }
}

private fun mainScreenNavigation(rootNavigator: Navigator): NavigationHost =
    navigationTabs(startDestination = "line") {
        registerNavigation(
            uri = "line",
            title = MR.strings.tab_list.desc(),
            icon = MR.images.line,
            childNavigation = {
                secondTabNavigation()
            }
        )
        registerScreen(
            uri = "settings",
            title = MR.strings.tab_settings.desc(),
            icon = MR.images.settings,
            screen = {
                ProfileScreen(rootNavigator)
            }
        )
    }

private fun secondTabNavigation() = navigation(startDestination = "start") {
    registerScreen(
        "start",
        isToolbarVisible = true,
    ) { navigator, _, screenSettings, viweModelOwner ->
        PostsList(screenSettings,viweModelOwner) { date, text ->
            navigator.navigate("/details/${date}?description=${text}")
        }
    }
    registerScreen(
        "/details/{date}?description={description}",
        isToolbarVisible = true
    ) { navController, args, screenSettings, _ ->
        DetailsScreen(
            navController,
            args["date"].orEmpty(),
            args["description"].orEmpty(),
            screenSettings
        )
    }
}

@Composable
fun Box(content: @Composable () -> Unit) {
    Column(
        horizontalAlignment = CrossAxisAlignment.Center,
        verticalAlignment = MainAxisAlignment.Center,
        height = Constraint.Fill
    ) {
        Row(
            verticalAlignment = CrossAxisAlignment.Center,
            horizontalAlignment = MainAxisAlignment.Center,
            width = Constraint.Fill
        ) {
            content()
        }
    }
}
