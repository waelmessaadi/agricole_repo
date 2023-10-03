package com.templeton.agricol_app.domain

import com.templeton.agricol_app.data.models.BankApiResponse

interface Repository {
    suspend fun getBanks(): BankApiResponse
}