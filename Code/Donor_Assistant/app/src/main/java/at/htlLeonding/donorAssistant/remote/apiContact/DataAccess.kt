package at.htlLeonding.donorAssistant.remote.apiContact

import android.util.Log
import at.htlLeonding.donorAssistant.remote.model.Question
import at.htlLeonding.donorAssistant.remote.model.Quote
import com.softbankrobotics.facerecognition.TAG
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import com.softbankrobotics.facerecognition.TAG
import okhttp3.*
import org.json.JSONObject

class DataAccess {

    private val username = "pepper"
    private val password = "jkasfi8219FNI21wesfg"
    private var authToken: String? = null
    private val baseUrl = "https://student.cloud.htl-leonding.ac.at/l.federseljwt/"//"http://10.0.2.2:8080"//

    private lateinit var retrofit: Retrofit
    private lateinit var questionAPI: RetrofitGsonQuestionAPI
    private lateinit var quoteAPI: RetrofitGsonQuoteAPI
    private lateinit var authService: RetrofitGsonAuthAPI

    private var client: OkHttpClient
    //private val okHttpClient = OkHttpClient.Builder().build()

    init {
        client = OkHttpClient()

        /*retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()*/
    }
    private fun createAuthenticatedClient(token:String): OkHttpClient {
        return OkHttpClient.Builder().addInterceptor { chain ->
            val originalRequest = chain.request()
            val authenticatedRequest = originalRequest.newBuilder()
                .header("Authorization", "Bearer $token")
                .build()
            chain.proceed(authenticatedRequest)
        }.build()
    }

    fun login(callback: (String?) -> Unit) {
        val json = JSONObject()
        json.put("username", username)
        json.put("password", password)

        val requestBody = RequestBody.create(MediaType.parse("application/json"), json.toString())

        val request = Request.Builder()
            .url("$baseUrl/users/login")
            .post(requestBody)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e(javaClass.simpleName, "Login failed: ${e.message}")
                callback(null)
            }

            override fun onResponse(call: Call, response: okhttp3.Response) {
                Log.i(javaClass.simpleName, "the response of call: ${response.isSuccessful}")
                if (response.isSuccessful) {
                    authToken = response.body()?.string()
                    Log.i(javaClass.simpleName, "Login token: $authToken")
                    // Set authenticated client here
                    client = createAuthenticatedClient(authToken!!)
                    // Build Retrofit instance after setting the client
                    retrofit = Retrofit.Builder()
                        .baseUrl(baseUrl)
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(client)
                        .build()
                    createGsons(retrofit)
                    callback(authToken)
                } else {
                    Log.i(javaClass.simpleName, "\nWomp womp: ${response.code()} ${response.isSuccessful}\n")
                    callback(null)
                }
            }
        })
    }

    private fun createGsons(retro:Retrofit) {
        Log.i(javaClass.simpleName, retro.toString())
        questionAPI = retro.create(RetrofitGsonQuestionAPI::class.java)
        quoteAPI = retro.create(RetrofitGsonQuoteAPI::class.java)
        authService = retrofit.create(RetrofitGsonAuthAPI::class.java)
    }


    suspend fun logout(): Boolean {
        val response = authService.logout()
        return response.isSuccessful
    }

    suspend fun getRandomQuestions(amount: Int): List<Question> {
        return questionAPI.getRandomQuestions(amount)
    }
    suspend fun getAllQuestions(): List<Question> {
        return questionAPI.getAllQuestions()
    }

    suspend fun getRandomQuote(): Quote {
        return quoteAPI.getRandomQuote()
    }

    suspend fun getQuestion(id: Int): Question {
        return questionAPI.getQuestionById(id)
    }

    suspend fun getQuote(id: Int): Quote {
        return quoteAPI.getQuoteById(id)
    }
}