package com.templeton.agricol_app.data.models

import android.annotation.SuppressLint
import android.os.Parcel
import android.os.Parcelable

@SuppressLint("ParcelCreator")
data class Account(
    val balance: Double,
    val contract_number: String,
    val holder: String,
    val id: String,
    val label: String,
    val operations: List<Operation>,
    val order: Int,
    val product_code: String,
    val role: Int
): Parcelable {
    override fun describeContents(): Int {
        TODO("Not yet implemented")
    }

    override fun writeToParcel(p0: Parcel, p1: Int) {
        TODO("Not yet implemented")
    }
}