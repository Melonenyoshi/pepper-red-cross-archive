package at.htlLeonding.donorAssistant.remote.apiContact

import at.htlLeonding.donorAssistant.remote.model.Question
import retrofit2.http.GET
import retrofit2.http.Path

interface RetrofitGsonQuestionAPI {
    @GET("questions/")
    suspend fun getAllQuestions(): List<Question>

    @GET("questions/{id}")
    suspend fun getQuestionById(@Path("id") id: Int): Question

    @GET("questions/random/{amount}/")
    suspend fun getRandomQuestions(@Path("amount") id: Int): List<Question>
}