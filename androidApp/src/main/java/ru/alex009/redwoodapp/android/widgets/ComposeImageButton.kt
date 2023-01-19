package ru.alex009.redwoodapp.android.widgets

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import app.cash.redwood.LayoutModifier
import dev.icerock.moko.resources.ImageResource
import dev.icerock.moko.resources.desc.image.asImageDesc
import org.example.library.MR
import ru.alex009.redwood.schema.widget.ImageButton
import ru.alex009.redwoodapp.android.R

class ComposeImageButton : ImageButton<@Composable () -> Unit> {
    private var _text by mutableStateOf("")
    private var _icon: ImageResource? by mutableStateOf(null)
    private var _onClick: () -> Unit by mutableStateOf({})
    private var _isClicked by mutableStateOf(true)

    override var layoutModifiers: LayoutModifier = LayoutModifier
    override val value = @Composable {
        //val tmp: ImageResource = MR.images.like
        Button(
            onClick = _onClick,
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color.Transparent
            ),
            elevation = ButtonDefaults.elevation(0.dp, 0.dp)
        ) {
            Row {
                Icon(
                    modifier = Modifier.padding(end = 8.dp),
                    painter = painterResource(_icon?.drawableResId ?: R.drawable.like /*tmp.drawableResId*/),
                    contentDescription = null
                )
                Text(
                    text = _text,
                    color = if (_isClicked) Color(0xFF0C7BC7) else Color(0xFFA9A9A9)
                )
            }
        }
    }

    override fun text(text: String) {
        _text = text
    }

    override fun icon(icon: ImageResource?) {
        _icon = icon
    }

    override fun onClick(onClick: (() -> Unit)?) {
        _onClick = onClick ?: {}
    }

    override fun isClicked(isClicked: Boolean) {
        _isClicked = isClicked
    }
}
