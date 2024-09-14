package at.htlLeonding.donorAssistant.remote.logger

import at.htlLeonding.donorAssistant.remote.apiContact.RetrofitGsonLoggerAPI
import at.htlLeonding.donorAssistant.remote.model.LogInfo
import kotlinx.coroutines.runBlocking
import okhttp3.Credentials
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.sql.Timestamp

class RemoteLogger {

    private val username = "logger"
    private val password = "UpdZQqRPYrQpOR5diFrj"
    private val baseUrl = "https://student.cloud.htl-leonding.ac.at/l.federsel/"

    fun log(message: String, category: String, origin: String) {
        val timestamp = Timestamp(System.currentTimeMillis()).toString()
        val okHttpClient = OkHttpClient.Builder().authenticator { _, response ->
            response.request().newBuilder()
                .header("Authorization", Credentials.basic(username, password)).build()
        }.build()

        val retrofit = Retrofit.Builder().baseUrl(baseUrl).client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create()).build()

        val loggerAPI = retrofit.create(RetrofitGsonLoggerAPI::class.java)

        val logInfo = LogInfo(message, timestamp, category, origin)

        runBlocking {
            try {
                loggerAPI.postLog(logInfo)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    /*inline fun <reified T> log(message: String, timestamp: Timestamp, category: String) {
        val origin = T::class.java.simpleName
        log(message, timestamp, category, origin)
    }*/
}