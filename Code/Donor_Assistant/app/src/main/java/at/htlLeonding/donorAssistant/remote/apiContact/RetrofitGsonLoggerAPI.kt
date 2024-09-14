package at.htlLeonding.donorAssistant.remote.apiContact

import at.htlLeonding.donorAssistant.remote.model.LogInfo
import retrofit2.http.*

interface RetrofitGsonLoggerAPI {
    // Define a POST method for pushing log information
    @POST("logs/")
    suspend fun postLog(@Body log: LogInfo)

}