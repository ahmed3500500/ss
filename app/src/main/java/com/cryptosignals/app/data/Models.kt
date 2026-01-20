package com.cryptosignals.app.data

import com.google.gson.annotations.SerializedName

data class SignalResponse(
    val count: Int,
    val signals: List<Signal>
)

data class Signal(
    val symbol: String,
    val timeframe: String,
    val score: Double,
    val rating: String,
    val reasons: List<String>,
    @SerializedName("scenario_up") val scenarioUp: String,
    @SerializedName("scenario_down") val scenarioDown: String,
    @SerializedName("created_at") val createdAt: String
)
