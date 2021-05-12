package com.yabu.livechartdemoapp

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.widget.Button
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
            textColor = Color.GRAY
            overlayLineColor = Color.BLUE
            overlayCircleDiameter = 32f
            overlayCircleColor = Color.GREEN
        }

        livechartSimple.setDataset(dataset)
            .setLiveChartStyle(chartStyle)
            .drawTouchOverlayAlways()
            .drawSmoothPath()
            .setInitialTouchOverlayPosition(dataset.points[3].x)
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

        val style = LiveChartStyle().apply {
            mainColor = Color.parseColor("#01C194")
            textColor = Color.GRAY
            positiveColor = Color.parseColor("#01C194")
            positiveFillColor = Color.parseColor("#01C194")
            negativeColor = Color.parseColor("#d70a53")
            negativeFillColor = Color.parseColor("#d70a53")
            pathStrokeWidth = 6f
            secondPathStrokeWidth = 4f
        }

        livechart.setDataset(dataset)
            .setLiveChartStyle(style)
            .drawYBounds()
            .drawFill(withGradient = true)
            .drawBaseline()
            .drawLastPointLabel()
            .drawBaselineConditionalColor()
            .setOnTouchCallbackListener(object : LiveChart.OnTouchCallback {
                @SuppressLint("SetTextI18n")
                override fun onTouchCallback(point: DataPoint) {
                    livechart.parent
                        .requestDisallowInterceptTouchEvent(true)
                }

                override fun onTouchFinished() {
                    livechart.parent
                        .requestDisallowInterceptTouchEvent(false)
                }
            })
            .drawDataset()

        livechartNegative.setDataset(negativeDataset)
            .setLiveChartStyle(style)
            .drawYBounds()
            .drawSmoothPath()
            .drawBaseline()
            .drawFill()
            .drawHorizontalGuidelines(steps = 4)
            .drawVerticalGuidelines(steps = 4)
            .drawBaselineConditionalColor()
            .drawLastPointLabel()
            .setOnTouchCallbackListener(object : LiveChart.OnTouchCallback {
                @SuppressLint("SetTextI18n")
                override fun onTouchCallback(point: DataPoint) {
                    livechartNegative.parent
                        .requestDisallowInterceptTouchEvent(true)
                }

                override fun onTouchFinished() {
                    livechartNegative.parent
                        .requestDisallowInterceptTouchEvent(false)
                }
            })
            .drawDataset()
    }
}