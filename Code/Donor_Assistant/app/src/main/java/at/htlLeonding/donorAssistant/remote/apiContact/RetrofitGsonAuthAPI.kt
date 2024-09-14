package at.htlLeonding.donorAssistant.remote.apiContact

import retrofit2.Response
import retrofit2.http.GET

interface RetrofitGsonAuthAPI {
    @GET("logout")
    suspend fun logout(): Response<Unit>
}