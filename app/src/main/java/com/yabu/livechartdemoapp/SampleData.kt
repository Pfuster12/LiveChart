package com.yabu.livechartdemoapp

import com.google.gson.Gson
import com.yabu.livechart.model.DataPoint
import com.yabu.livechart.model.Dataset

object SampleData {
    fun createSampleData(): Dataset {
        val json = "[\n" +
                "{\n" +
                "date: \"2019-11-25\",\n" +
                "close: 14.42,\n" +
                "volume: 11566939,\n" +
                "change: -0.07,\n" +
                "changePercent: -0.5086,\n" +
                "changeOverTime: 0.086718\n" +
                "},\n" +
                "{\n" +
                "date: \"2019-12-03\",\n" +
                "close: 14.55,\n" +
                "volume: 18164933,\n" +
                "change: 0.14,\n" +
                "changePercent: 1.0483,\n" +
                "changeOverTime: 0.102737\n" +
                "},\n" +
                "{\n" +
                "date: \"2019-12-10\",\n" +
                "close: 14,\n" +
                "volume: 8861763,\n" +
                "change: 0.03,\n" +
                "changePercent: 0.2189,\n" +
                "changeOverTime: 0.092229\n" +
                "},\n" +
                "{\n" +
                "date: \"2019-12-17\",\n" +
                "close: 14.6,\n" +
                "volume: 24158613,\n" +
                "change: -0.02,\n" +
                "changePercent: -0.1472,\n" +
                "changeOverTime: 0.099627\n" +
                "},\n" +
                "{\n" +
                "date: \"2019-12-24\",\n" +
                "close: 14.33,\n" +
                "volume: 5446246,\n" +
                "change: 0.13,\n" +
                "changePercent: 0.9382,\n" +
                "changeOverTime: 0.117788\n" +
                "},\n" +
                "{\n" +
                "date: \"2020-01-02\",\n" +
                "close: 15.24,\n" +
                "volume: 37906723,\n" +
                "change: 0.11,\n" +
                "changePercent: 0.7948,\n" +
                "changeOverTime: 0.140012\n" +
                "},\n" +
                "{\n" +
                "date: \"2020-01-09\",\n" +
                "close: 15.38,\n" +
                "volume: 16876278,\n" +
                "change: -0.09,\n" +
                "changePercent: -0.6081,\n" +
                "changeOverTime: 0.156017\n" +
                "},\n" +
                "{\n" +
                "date: \"2020-01-16\",\n" +
                "close: 15.12,\n" +
                "volume: 15620943,\n" +
                "change: -0.03,\n" +
                "changePercent: -0.2092,\n" +
                "changeOverTime: 0.16296\n" +
                "},\n" +
                "{\n" +
                "date: \"2020-01-24\",\n" +
                "close: 15.33,\n" +
                "volume: 20919356,\n" +
                "change: 0.08,\n" +
                "changePercent: 0.5551,\n" +
                "changeOverTime: 0.173889\n" +
                "},\n" +
                "{\n" +
                "date: \"2020-01-31\",\n" +
                "close: 15.63,\n" +
                "volume: 28900030,\n" +
                "change: 0.1,\n" +
                "changePercent: 0.6878,\n" +
                "changeOverTime: 0.182919\n" +
                "},\n" +
                "{\n" +
                "date: \"2020-02-07\",\n" +
                "close: 15,\n" +
                "volume: 14576050,\n" +
                "change: 0.03,\n" +
                "changePercent: 0.2097,\n" +
                "changeOverTime: 0.171494\n" +
                "},\n" +
                "{\n" +
                "date: \"2020-02-14\",\n" +
                "close: 15.57,\n" +
                "volume: 11386011,\n" +
                "change: 0.06,\n" +
                "changePercent: 0.4083,\n" +
                "changeOverTime: 0.185619\n" +
                "},\n" +
                "{\n" +
                "date: \"2020-02-24\",\n" +
                "close: 16.03,\n" +
                "volume: 49109291,\n" +
                "change: 0.15,\n" +
                "changePercent: 0.9186,\n" +
                "changeOverTime: 0.246542\n" +
                "},\n" +
                "{\n" +
                "date: \"2020-03-02\",\n" +
                "close: 15.23,\n" +
                "volume: 35997597,\n" +
                "change: 0.08,\n" +
                "changePercent: 0.5396,\n" +
                "changeOverTime: 0.186289\n" +
                "},\n" +
                "{\n" +
                "date: \"2020-03-09\",\n" +
                "close: 16.33,\n" +
                "volume: 38820384,\n" +
                "change: 0.04,\n" +
                "changePercent: 0.25,\n" +
                "changeOverTime: 0.260098\n" +
                "},\n" +
                "{\n" +
                "date: \"2020-03-16\",\n" +
                "close: 14.9,\n" +
                "volume: 60527598,\n" +
                "change: -0.15,\n" +
                "changePercent: -1.035,\n" +
                "changeOverTime: 0.124428\n" +
                "},\n" +
                "{\n" +
                "date: \"2020-03-23\",\n" +
                "close: 15.39,\n" +
                "volume: 36981448,\n" +
                "change: 0.65,\n" +
                "changePercent: 4.399,\n" +
                "changeOverTime: 0.163769\n" +
                "},\n" +
                "{\n" +
                "date: \"2020-03-30\",\n" +
                "close: 15.79,\n" +
                "volume: 19231983,\n" +
                "change: 0.04,\n" +
                "changePercent: 0.2695,\n" +
                "changeOverTime: 0.216423\n" +
                "},\n" +
                "{\n" +
                "date: \"2020-04-06\",\n" +
                "close: 16.53,\n" +
                "volume: 25039624,\n" +
                "change: 0.42,\n" +
                "changePercent: 2.6593,\n" +
                "changeOverTime: 0.252827\n" +
                "},\n" +
                "{\n" +
                "date: \"2020-04-14\",\n" +
                "close: 16.76,\n" +
                "volume: 30188740,\n" +
                "change: 0.16,\n" +
                "changePercent: 0.9881,\n" +
                "changeOverTime: 0.294901\n" +
                "},\n" +
                "{\n" +
                "date: \"2020-04-21\",\n" +
                "close: 16.17,\n" +
                "volume: 19911611,\n" +
                "change: -0.12,\n" +
                "changePercent: -0.7424,\n" +
                "changeOverTime: 0.263775\n" +
                "},\n" +
                "{\n" +
                "date: \"2020-04-28\",\n" +
                "close: 16.56,\n" +
                "volume: 15797294,\n" +
                "change: -0.09,\n" +
                "changePercent: -0.5506,\n" +
                "changeOverTime: 0.286653\n" +
                "},\n" +
                "{\n" +
                "date: \"2020-05-05\",\n" +
                "close: 16.66,\n" +
                "volume: 22607147,\n" +
                "change: 0.04,\n" +
                "changePercent: 0.2471,\n" +
                "changeOverTime: 0.277164\n" +
                "},\n" +
                "{\n" +
                "date: \"2020-05-12\",\n" +
                "close: 16.54,\n" +
                "volume: 20726095,\n" +
                "change: 0.07,\n" +
                "changePercent: 0.4441,\n" +
                "changeOverTime: 0.276746\n" +
                "},\n" +
                "{\n" +
                "date: \"2020-05-19\",\n" +
                "close: 17.3,\n" +
                "volume: 17578135,\n" +
                "change: 0.13,\n" +
                "changePercent: 0.8021,\n" +
                "changeOverTime: 0.306148\n" +
                "},\n" +
                "{\n" +
                "date: \"2020-05-27\",\n" +
                "close: 16.66,\n" +
                "volume: 30192569,\n" +
                "change: 0.02,\n" +
                "changePercent: 0.126,\n" +
                "changeOverTime: 0.277279\n" +
                "},\n" +
                "{\n" +
                "date: \"2020-06-03\",\n" +
                "close: 16.36,\n" +
                "volume: 34419478,\n" +
                "change: -0.3,\n" +
                "changePercent: -1.855,\n" +
                "changeOverTime: 0.267956\n" +
                "},\n" +
                "{\n" +
                "date: \"2020-06-10\",\n" +
                "close: 16.95,\n" +
                "volume: 26122038,\n" +
                "change: 0.23,\n" +
                "changePercent: 1.4635,\n" +
                "changeOverTime: 0.302488\n" +
                "}\n" +
                "]"

        val data = Gson().fromJson(json, TestDataResponse::class.java)
        return Dataset(data.mapIndexed { index, it ->
            DataPoint(index.toFloat(), it.close.toFloat())
        }.toMutableList())
    }

    fun createNegativeSampleData(): Dataset {
        val json = "[\n" +
                "{\n" +
                "date: \"2019-11-25\",\n" +
                "close: 15.42,\n" +
                "volume: 11566939,\n" +
                "change: -0.07,\n" +
                "changePercent: -0.5086,\n" +
                "changeOverTime: 0.086718\n" +
                "},\n" +
                "{\n" +
                "date: \"2019-12-03\",\n" +
                "close: 14.55,\n" +
                "volume: 18164933,\n" +
                "change: 0.14,\n" +
                "changePercent: 1.0483,\n" +
                "changeOverTime: 0.102737\n" +
                "},\n" +
                "{\n" +
                "date: \"2019-12-10\",\n" +
                "close: 14,\n" +
                "volume: 8861763,\n" +
                "change: 0.03,\n" +
                "changePercent: 0.2189,\n" +
                "changeOverTime: 0.092229\n" +
                "},\n" +
                "{\n" +
                "date: \"2019-12-17\",\n" +
                "close: 14.6,\n" +
                "volume: 24158613,\n" +
                "change: -0.02,\n" +
                "changePercent: -0.1472,\n" +
                "changeOverTime: 0.099627\n" +
                "},\n" +
                "{\n" +
                "date: \"2019-12-24\",\n" +
                "close: 14.33,\n" +
                "volume: 5446246,\n" +
                "change: 0.13,\n" +
                "changePercent: 0.9382,\n" +
                "changeOverTime: 0.117788\n" +
                "},\n" +
                "{\n" +
                "date: \"2020-01-02\",\n" +
                "close: 15.24,\n" +
                "volume: 37906723,\n" +
                "change: 0.11,\n" +
                "changePercent: 0.7948,\n" +
                "changeOverTime: 0.140012\n" +
                "},\n" +
                "{\n" +
                "date: \"2020-01-09\",\n" +
                "close: 15.38,\n" +
                "volume: 16876278,\n" +
                "change: -0.09,\n" +
                "changePercent: -0.6081,\n" +
                "changeOverTime: 0.156017\n" +
                "},\n" +
                "{\n" +
                "date: \"2020-01-16\",\n" +
                "close: 15.12,\n" +
                "volume: 15620943,\n" +
                "change: -0.03,\n" +
                "changePercent: -0.2092,\n" +
                "changeOverTime: 0.16296\n" +
                "},\n" +
                "{\n" +
                "date: \"2020-01-24\",\n" +
                "close: 15.33,\n" +
                "volume: 20919356,\n" +
                "change: 0.08,\n" +
                "changePercent: 0.5551,\n" +
                "changeOverTime: 0.173889\n" +
                "},\n" +
                "{\n" +
                "date: \"2020-01-31\",\n" +
                "close: 15.63,\n" +
                "volume: 28900030,\n" +
                "change: 0.1,\n" +
                "changePercent: 0.6878,\n" +
                "changeOverTime: 0.182919\n" +
                "},\n" +
                "{\n" +
                "date: \"2020-02-07\",\n" +
                "close: 15,\n" +
                "volume: 14576050,\n" +
                "change: 0.03,\n" +
                "changePercent: 0.2097,\n" +
                "changeOverTime: 0.171494\n" +
                "},\n" +
                "{\n" +
                "date: \"2020-02-14\",\n" +
                "close: 15.57,\n" +
                "volume: 11386011,\n" +
                "change: 0.06,\n" +
                "changePercent: 0.4083,\n" +
                "changeOverTime: 0.185619\n" +
                "},\n" +
                "{\n" +
                "date: \"2020-02-24\",\n" +
                "close: 16.03,\n" +
                "volume: 49109291,\n" +
                "change: 0.15,\n" +
                "changePercent: 0.9186,\n" +
                "changeOverTime: 0.246542\n" +
                "},\n" +
                "{\n" +
                "date: \"2020-03-02\",\n" +
                "close: 15.23,\n" +
                "volume: 35997597,\n" +
                "change: 0.08,\n" +
                "changePercent: 0.5396,\n" +
                "changeOverTime: 0.186289\n" +
                "},\n" +
                "{\n" +
                "date: \"2020-03-09\",\n" +
                "close: 16.33,\n" +
                "volume: 38820384,\n" +
                "change: 0.04,\n" +
                "changePercent: 0.25,\n" +
                "changeOverTime: 0.260098\n" +
                "},\n" +
                "{\n" +
                "date: \"2020-03-16\",\n" +
                "close: 14.9,\n" +
                "volume: 60527598,\n" +
                "change: -0.15,\n" +
                "changePercent: -1.035,\n" +
                "changeOverTime: 0.124428\n" +
                "},\n" +
                "{\n" +
                "date: \"2020-03-23\",\n" +
                "close: 15.39,\n" +
                "volume: 36981448,\n" +
                "change: 0.65,\n" +
                "changePercent: 4.399,\n" +
                "changeOverTime: 0.163769\n" +
                "},\n" +
                "{\n" +
                "date: \"2020-03-30\",\n" +
                "close: 15.79,\n" +
                "volume: 19231983,\n" +
                "change: 0.04,\n" +
                "changePercent: 0.2695,\n" +
                "changeOverTime: 0.216423\n" +
                "},\n" +
                "{\n" +
                "date: \"2020-04-06\",\n" +
                "close: 16.53,\n" +
                "volume: 25039624,\n" +
                "change: 0.42,\n" +
                "changePercent: 2.6593,\n" +
                "changeOverTime: 0.252827\n" +
                "},\n" +
                "{\n" +
                "date: \"2020-04-14\",\n" +
                "close: 16.76,\n" +
                "volume: 30188740,\n" +
                "change: 0.16,\n" +
                "changePercent: 0.9881,\n" +
                "changeOverTime: 0.294901\n" +
                "},\n" +
                "{\n" +
                "date: \"2020-04-21\",\n" +
                "close: 16.17,\n" +
                "volume: 19911611,\n" +
                "change: -0.12,\n" +
                "changePercent: -0.7424,\n" +
                "changeOverTime: 0.263775\n" +
                "},\n" +
                "{\n" +
                "date: \"2020-04-28\",\n" +
                "close: 16.56,\n" +
                "volume: 15797294,\n" +
                "change: -0.09,\n" +
                "changePercent: -0.5506,\n" +
                "changeOverTime: 0.286653\n" +
                "},\n" +
                "{\n" +
                "date: \"2020-05-05\",\n" +
                "close: 16.66,\n" +
                "volume: 22607147,\n" +
                "change: 0.04,\n" +
                "changePercent: 0.2471,\n" +
                "changeOverTime: 0.277164\n" +
                "},\n" +
                "{\n" +
                "date: \"2020-05-12\",\n" +
                "close: 17.20,\n" +
                "volume: 20726095,\n" +
                "change: 0.07,\n" +
                "changePercent: 0.4441,\n" +
                "changeOverTime: 0.276746\n" +
                "},\n" +
                "{\n" +
                "date: \"2020-05-19\",\n" +
                "close: 16.53,\n" +
                "volume: 17578135,\n" +
                "change: 0.13,\n" +
                "changePercent: 0.8021,\n" +
                "changeOverTime: 0.306148\n" +
                "},\n" +
                "{\n" +
                "date: \"2020-05-27\",\n" +
                "close: 16.01,\n" +
                "volume: 30192569,\n" +
                "change: 0.02,\n" +
                "changePercent: 0.126,\n" +
                "changeOverTime: 0.277279\n" +
                "},\n" +
                "{\n" +
                "date: \"2020-06-03\",\n" +
                "close: 15.36,\n" +
                "volume: 34419478,\n" +
                "change: -0.3,\n" +
                "changePercent: -1.855,\n" +
                "changeOverTime: 0.267956\n" +
                "},\n" +
                "{\n" +
                "date: \"2020-06-10\",\n" +
                "close: 14.91,\n" +
                "volume: 26122038,\n" +
                "change: 0.23,\n" +
                "changePercent: 1.4635,\n" +
                "changeOverTime: 0.302488\n" +
                "}\n" +
                "]"

        val data = Gson().fromJson(json, TestDataResponse::class.java)
        return Dataset(data.mapIndexed { index, it ->
            DataPoint(index.toFloat(), it.close.toFloat())
        }.toMutableList())
    }

}