package at.htlLeonding.donorAssistant.remote.apiContact

import at.htlLeonding.donorAssistant.remote.model.Quote
import retrofit2.http.GET
import retrofit2.http.Path

interface RetrofitGsonQuoteAPI {
    @GET("quotes/{id}")
    suspend fun getQuoteById(@Path("id") id: Int): Quote

    @GET("quotes/random")
    suspend fun getRandomQuote(): Quote
}