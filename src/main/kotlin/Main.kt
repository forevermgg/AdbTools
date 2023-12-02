import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.*
import view.viewAdbKeyEvent

@Composable
@Preview
fun app() {
    MaterialTheme {
        Column(
            modifier = Modifier.fillMaxSize(),
            content = {
                viewAdbKeyEvent()
            }
        )
    }
}

fun main() = application {
    // Setting the application tray icon
    // You can create a tray icon for your application:
    val icon = painterResource("android.png")
    Tray(
        icon = icon,
        menu = {
            Item("退出⏏️", onClick = ::exitApplication)
        }
    )
    val windowState = rememberWindowState(
        size = DpSize(380.dp, 280.dp),
        position = WindowPosition.Aligned(Alignment.Center),
    )
    Window(
        onCloseRequest = ::exitApplication,
        title = "adb模拟简易遥控器",
        state = windowState,
        alwaysOnTop = true,
        icon = icon,
    ) {
        app()
    }
}
