package com.templeton.agricol_app.data.models

data class BankApiResponseItem(
    val accounts: List<Account>,
    val isCA: Int,
    val name: String
)