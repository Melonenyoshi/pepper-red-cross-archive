package at.htlLeonding.donorAssistant.remote.apiContact

import android.os.Build
import androidx.annotation.RequiresApi
import org.apache.logging.log4j.core.Filter
import org.apache.logging.log4j.core.Layout
import org.apache.logging.log4j.core.appender.AbstractOutputStreamAppender
import org.apache.logging.log4j.core.appender.OutputStreamManager
import java.io.ByteArrayOutputStream
import java.io.Serializable
import java.net.HttpURLConnection
import java.net.URL
import java.util.*

class HttpAppender(
    name: String,
    layout: Layout<out Serializable>,
    filter: Filter,
    ignoreExceptions: Boolean,
    manager: HttpManager
) : AbstractOutputStreamAppender<HttpAppender.HttpManager>(
    name, layout, filter, ignoreExceptions, true, manager
) {

    class HttpManager(
        private val url: URL,
        private val username: String,
        private val password: String,
        layout: Layout<out Serializable>,
        writeHeader: Boolean
    ) : OutputStreamManager(ByteArrayOutputStream(), url.toString(), layout, writeHeader) {

        @RequiresApi(Build.VERSION_CODES.O)
        @Synchronized
        override fun write(bytes: ByteArray, offset: Int, length: Int) {
            super.write(bytes, offset, length)
            val connection = url.openConnection() as HttpURLConnection
            connection.requestMethod = "POST"
            val auth = Base64.getEncoder().encodeToString("$username:$password".toByteArray())
            connection.setRequestProperty("Authorization", "Basic $auth")
            connection.outputStream.use { os ->
                os.write((outputStream as ByteArrayOutputStream).toByteArray())
                os.flush()
            }
            (outputStream as ByteArrayOutputStream).reset()
        }
    }
}