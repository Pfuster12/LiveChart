package com.yabu.livechartdemoapp

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.yabu.livechart.model.DataPoint
import com.yabu.livechart.model.Dataset
import com.yabu.livechart.view.LiveChart
import com.yabu.livechart.view.LiveChartStyle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var livechart: LiveChart
    private lateinit var livechartSimple: LiveChart
    private lateinit var livechartNegative: LiveChart

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        livechart = findViewById(R.id.main_live_chart)
        livechartSimple = findViewById(R.id.main_simple_live_chart)
        livechartNegative = findViewById(R.id.main_negative_live_chart)

        val dataset = SampleData.createSampleData()

        val negativeDataset = SampleData.createNegativeSampleData()

        val chartStyle = LiveChartStyle().apply {
            mainColor = Color.GRAY
            pathStrokeWidth = 8f
            secondPathStrokeWidth = 4f
            textHeight = 40f
            overlayLineColor = Color.BLUE
            overlayCircleDiameter = 32f
            overlayCircleColor = Color.GREEN
        }

        val secondDataset = Dataset(mutableListOf(DataPoint(0f, 0f),
            DataPoint(1f, 1f),
            DataPoint(2f, 2f),
            DataPoint(3f, 3f),
            DataPoint(4f, 4f),
            DataPoint(5f, 5f),
            DataPoint(6f, 10f),
            DataPoint(7f, 18f)
        ))

        livechartSimple.setDataset(dataset)
            .setLiveChartStyle(chartStyle)
            .drawSmoothPath()
            .setOnTouchCallbackListener(object : LiveChart.OnTouchCallback {
                @SuppressLint("SetTextI18n")
                override fun onTouchCallback(point: DataPoint) {
                    livechartSimple.parent.requestDisallowInterceptTouchEvent(true)
                    main_simple_data_point.text = "(${"%.2f".format(point.x)}, ${"%.2f".format(point.y)})"
                }

                override fun onTouchFinished() {
                    livechartSimple.parent.requestDisallowInterceptTouchEvent(false)
                    main_simple_data_point.text = "Touch Finished"
                }
            })
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

        val style = LiveChartStyle().apply {
            mainColor = Color.GRAY
            secondColor = Color.MAGENTA
            pathStrokeWidth = 8f
            secondPathStrokeWidth = 4f
            textHeight = 40f
        }

        livechart.setDataset(firstDataset)
            .setLiveChartStyle(style)
            .disableTouchOverlay()
            .drawSmoothPath()
            .drawYBounds()
            .drawDataset()

        livechartNegative.setDataset(negativeDataset)
            .drawYBounds()
            .drawBaseline()
            .drawLastPointLabel()
            .drawFill(false)
            .drawDataset()
    }
}