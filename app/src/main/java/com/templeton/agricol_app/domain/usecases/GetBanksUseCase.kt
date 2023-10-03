package com.templeton.agricol_app.domain.usecases

import com.templeton.agricol_app.common.Resource
import com.templeton.agricol_app.data.models.BankApiResponse
import com.templeton.agricol_app.domain.Repository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import javax.inject.Inject

class GetBanksUseCase @Inject constructor(private val repository: Repository) {
    operator fun invoke(): Flow<Resource<BankApiResponse>> = flow {
        try {
            emit(Resource.Loading())
            val banks = repository.getBanks()
            emit(Resource.Success(banks))
        } catch(e: HttpException) {
            emit(Resource.Error(e.message ?: "An unexpected error occurred"))
        }
    }
}