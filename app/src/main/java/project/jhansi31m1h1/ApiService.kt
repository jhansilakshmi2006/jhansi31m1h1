package project.jhansi31m1h1

import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {

    @POST("v1beta/models/gemini-2.0-flash:generateContent")
    suspend fun askGemini(
        @Body request: ChatRequest,
        @Query("key") apiKey: String
    ): ChatResponse
}