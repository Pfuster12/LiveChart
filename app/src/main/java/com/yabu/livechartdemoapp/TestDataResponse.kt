package com.yabu.livechartdemoapp

class TestDataResponse : ArrayList<TestDataResponseItem>()

data class TestDataResponseItem(
    val change: Double,
    val changeOverTime: Double,
    val changePercent: Double,
    val close: Double,
    val date: String,
    val volume: Int
)