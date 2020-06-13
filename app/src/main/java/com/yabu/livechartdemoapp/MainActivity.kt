package com.yabu.livechartdemoapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.yabu.livechart.view.LiveChartView

class MainActivity : AppCompatActivity() {
    private lateinit var livechart: LiveChartView
    private lateinit var livechartNegative: LiveChartView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        livechart = findViewById(R.id.main_live_chart)
        livechartNegative = findViewById(R.id.main_negative_live_chart)

        val dataset = SampleData.createSampleData()

        val negativeDataset = SampleData.createNegativeSampleData()

        livechart.setDataset(dataset)
            .drawYBounds()
            .drawBaseline()
            .drawFill()
            .drawDataset()

        livechartNegative.setDataset(negativeDataset)
            .drawYBounds()
            .drawBaseline()
            .drawFill()
            .drawDataset()
    }
}