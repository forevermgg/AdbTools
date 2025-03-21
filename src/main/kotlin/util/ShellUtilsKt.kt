package util

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.InputStreamReader
import java.nio.charset.Charset

@Suppress("NAME_SHADOWING")
object ShellUtilsKt {

    //工作目录, 即是在某个目录下进行命令执行
    private var workDirectory: File? = null

    suspend fun shell(charset: Charset = Charsets.UTF_8, command: String): String {
        val command = command.split(" ").toTypedArray()
        return shell(charset, *command)
    }

    suspend fun shell(charset: Charset = Charsets.UTF_8, vararg commands: String): String {
        if (commands.isEmpty()) return ""
        val process = withContext(Dispatchers.IO) {
            Runtime.getRuntime().exec(commands, null, workDirectory)
        }
        val readText = InputStreamReader(process.inputStream, charset).use { it.readText() }
        process.destroy()
        return readText.trim()
    }

    suspend fun shell(charset: Charset = Charsets.UTF_8, command: String, block: (success: String, error: String) -> Unit) {
        val command = command.split(" ").toTypedArray()
        shell(charset, *command, block = block)
    }

    suspend fun shell(charset: Charset = Charsets.UTF_8, vararg commands: String, block: (success: String, error: String) -> Unit) {
        if (commands.isEmpty()) return

        val process = withContext(Dispatchers.IO) {
            Runtime.getRuntime().exec(commands, null, workDirectory)
        }

        val successText = InputStreamReader(process.inputStream, charset).use { it.readText() }
        val errorText = InputStreamReader(process.errorStream, charset).use { it.readText() }
        process.destroy()
        block.invoke(successText, errorText)
    }
}