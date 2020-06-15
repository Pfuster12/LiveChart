package com.yabu.livechartdemoapp

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.yabu.livechart.model.DataPoint
import com.yabu.livechart.model.Dataset
import com.yabu.livechart.view.LiveChartStyle
import com.yabu.livechart.view.LiveChartView

class MainActivity : AppCompatActivity() {
    private lateinit var livechart: LiveChartView
    private lateinit var livechartSimple: LiveChartView
    private lateinit var livechartNegative: LiveChartView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        livechart = findViewById(R.id.main_live_chart)
        livechartSimple = findViewById(R.id.main_simple_live_chart)
        livechartNegative = findViewById(R.id.main_negative_live_chart)

        val dataset = SampleData.createSampleData()

        val negativeDataset = SampleData.createNegativeSampleData()

        livechartSimple.setDataset(dataset)
            .drawDataset()

        val firstDataset = Dataset(mutableListOf(DataPoint(0f, 1f),
            DataPoint(1f, 2f),
            DataPoint(2f, 3f),
            DataPoint(3f, 4f),
            DataPoint(4f, 5f),
            DataPoint(5f, 8f),
            DataPoint(6f, 13f),
            DataPoint(7f, 21f)
        ))

        val secondDataset = Dataset(mutableListOf(DataPoint(0f, 0f),
            DataPoint(1f, 1f),
            DataPoint(2f, 2f),
            DataPoint(3f, 3f),
            DataPoint(4f, 4f),
            DataPoint(5f, 5f),
            DataPoint(6f, 10f),
            DataPoint(7f, 18f)
        ))

        val style = LiveChartStyle().apply {
            mainColor = Color.GRAY
            secondColor = Color.MAGENTA
            pathStrokeWidth = 8f
            secondPathStrokeWidth = 8f
        }

        livechart.setDataset(firstDataset)
            .setSecondDataset(secondDataset)
            .setLiveChartStyle(style)
            .drawYBounds()
            .drawDataset()

        livechartNegative.setDataset(negativeDataset)
            .drawYBounds()
            .drawBaseline()
            .drawFill()
            .drawDataset()
    }
}