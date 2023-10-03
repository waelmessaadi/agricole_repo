package com.templeton.agricol_app.data.remote

import com.templeton.agricol_app.data.models.BankApiResponse
import retrofit2.http.GET

interface ApiService {
    @GET("banks.json")
    suspend fun getBanks(): BankApiResponse
}