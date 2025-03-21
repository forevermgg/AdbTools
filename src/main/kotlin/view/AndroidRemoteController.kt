package view

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import util.ShellUtilsKt
import util.formatAdbCommand

@Composable
@Preview
fun viewAdbKeyEvent() {

    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    // val scale by animateFloatAsState(targetValue = if (isPressed) 0.9f else 1f, label = "scale")
    var textAdbPathFieldValue by rememberSaveable { mutableStateOf(TextFieldValue("")) }
    val scope = rememberCoroutineScope()
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth(),
        content = {
            TextField(
                value = textAdbPathFieldValue,
                onValueChange = { newText ->
                    textAdbPathFieldValue = newText
                },
                placeholder = { // 定义 TextField 中的提示文本
                    Text(text = "请输入Adb路径")
                }
            )
            Button(
                interactionSource = interactionSource,
                modifier = Modifier.align(Alignment.CenterHorizontally),
                onClick = {
                    scope.launch(Dispatchers.Default) {
                        execAdb(textAdbPathFieldValue.text, 19)
                    }
                }) {
                Text("⬆️")
            }
            Row {
                Button(
                    onClick = {
                    scope.launch(Dispatchers.Default) {
                        execAdb(textAdbPathFieldValue.text, 21)
                    }
                }) {
                    Text("⬅️")
                }
                Button(onClick = {
                    scope.launch(Dispatchers.Default) {
                        execAdb(textAdbPathFieldValue.text, 23)
                    }
                }) {
                    Text("👌")
                }
                Button(onClick = {
                    scope.launch(Dispatchers.Default) {
                        execAdb(textAdbPathFieldValue.text, 22)
                    }
                }) {
                    Text("➡️")
                }
            }
            Button(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                onClick = {
                    scope.launch(Dispatchers.Default) {
                        execAdb(textAdbPathFieldValue.text, 20)
                    }
                }) {
                Text("⬇️")
            }

            Button(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                onClick = {
                    scope.launch(Dispatchers.Default) {
                        execAdb(textAdbPathFieldValue.text, 4)
                    }
                }) {
                Text("🔙")
            }
        }
    )
}

// https://github.com/JetBrains/compose-multiplatform/issues/1810
suspend fun whichAdb(): String {
    // Perform some long-running operation here
    val command = "which adb".formatAdbCommand("")
    return ShellUtilsKt.shell(charset = Charsets.UTF_8, command)
}

suspend fun execAdb(adbPath: String?, keyCode: Int): String {
    if (adbPath.isNullOrEmpty()) {
        return ""
    }
    val command = adbPath + " shell input keyevent $keyCode".formatAdbCommand("")
    return ShellUtilsKt.shell(charset = Charsets.UTF_8, command)
}