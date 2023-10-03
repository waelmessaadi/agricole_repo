package com.templeton.agricol_app.presentation.myaccounts

import androidx.lifecycle.ViewModel
import com.templeton.agricol_app.common.Resource
import com.templeton.agricol_app.data.models.BankApiResponse
import kotlinx.coroutines.flow.Flow
import com.templeton.agricol_app.domain.usecases.GetBanksUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MyAccountsViewModel @Inject constructor(private val getBanksUseCase: GetBanksUseCase): ViewModel() {

    val banks: Flow<Resource<BankApiResponse>> = getBanksUseCase()

}