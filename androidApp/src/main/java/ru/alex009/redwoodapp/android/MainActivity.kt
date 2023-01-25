package ru.alex009.redwoodapp.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import app.cash.redwood.layout.composeui.ComposeUiRedwoodLayoutWidgetFactory
import ru.alex009.redwoodapp.android.widgets.ComposeWidgetFactory
import ru.alex009.redwoodapp.mainApp
import ru.alex009.redwoodapp.navigation.NavigationHost

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val factories = SchemaWidgetFactories(
            RedwoodAppSchema = ComposeWidgetFactory,
            RedwoodLayout = ComposeUiRedwoodLayoutWidgetFactory(),
        )

        val app: NavigationHost = mainApp()

        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    app.Render(factories)
                }
            }
        }
    }
}
