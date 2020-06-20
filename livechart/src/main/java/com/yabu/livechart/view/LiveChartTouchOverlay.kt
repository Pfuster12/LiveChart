package com.yabu.livechart.view

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.DashPathEffect
import android.graphics.Path
import android.graphics.PathMeasure
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.widget.FrameLayout
import com.yabu.livechart.R
import com.yabu.livechart.model.Bounds
import com.yabu.livechart.model.DataPoint
import com.yabu.livechart.model.Dataset
import com.yabu.livechart.utils.PublicApi
import com.yabu.livechart.view.LiveChart.OnTouchCallback
import kotlin.math.max
import kotlin.math.min

/**
 * Touch overlay for the [LiveChartView]. Perform touch event draws in this class
 * instead of the LiveChart to avoid unnecessary expensive onDraw calls.
 * For example, the horizontal drag DataPoint slider.
 */
class LiveChartTouchOverlay(context: Context, attrs: AttributeSet?)
    : FrameLayout(context, attrs) {

    /**
     * The chart bounds in the screen pixel space
     */
    private var chartBounds = Bounds(
        top = paddingTop.toFloat(),
        end = paddingEnd.toFloat(),
        bottom = paddingBottom.toFloat(),
        start = paddingLeft.toFloat()
    )

    private val overlay = LayoutInflater.from(context)
        .inflate(R.layout.livechart_touch_overlay,
        this,
        false)

    private var overlayPoint: View

    private var overlayLine: View

    /**
     * Baseline to determine paint color from data end point.
     */
    private var chartStyle: LiveChartStyle = LiveChartStyle()

    /**
     * [Dataset] this overlay responds to.
     */
    private var dataset: Dataset = Dataset.new()

    /**
     * Second dataset
     */
    private var secondDataset: Dataset = Dataset.new()

    /**
     * Path generated from dataset points.
     */
    private val pathMeasure = PathMeasure()

    /**
     * Y Bounds display flag.
     */
    private var drawYBounds = false

    /**
     * [OnTouchCallback] listener.
     */
    private var touchListener: OnTouchCallback? = null

    init {
        clipChildren = false

        overlayLine = overlay.findViewById(R.id.touch_overlay_line)
        overlayPoint = overlay.findViewById(R.id.touch_overlay_point)
        overlay.alpha = 0f

        // Gather the xml attributes to style the chart,
        context.theme?.obtainStyledAttributes(
            attrs,
            R.styleable.LiveChart,
            0, 0)?.apply {

            try {
                // Color attrs,
                chartStyle.overlayLineColor = getColor(R.styleable.LiveChart_overlayLineColor,
                    chartStyle.overlayLineColor)
                chartStyle.overlayCircleColor = getColor(R.styleable.LiveChart_overlayCircleColor,
                    chartStyle.overlayCircleColor)
                chartStyle.overlayCircleDiameter = getDimension(R.styleable.LiveChart_overlayCircleDiameter,
                    chartStyle.overlayCircleDiameter)
            } finally {
                recycle()
            }
        }

        setLiveChartStyle(chartStyle)

        this.addView(overlay)
    }

    /**
     * Draw Y bounds flag.
     */
    fun drawYBounds(): LiveChartTouchOverlay {
        drawYBounds = true

        return this
    }

    /**
     * Set the [Dataset] this overlay responds to.
     * @param dataset
     */
    fun setDataset(dataset: Dataset): LiveChartTouchOverlay {
        this.dataset = dataset

        return this
    }

    /**
     * Set the Second [dataset] of this chart.
     */
    fun setSecondDataset(dataset: Dataset): LiveChartTouchOverlay {
        this.secondDataset = dataset
        return this
    }

    /**
     * Set the style object [LiveChartStyle] to this overlay.
     */
    fun setLiveChartStyle(style: LiveChartStyle): LiveChartTouchOverlay {
        chartStyle = style

        val lp = overlayPoint.layoutParams
        lp.width = chartStyle.overlayCircleDiameter.toInt()
        lp.height = chartStyle.overlayCircleDiameter.toInt()
        overlayPoint.layoutParams = lp

        overlayLine.setBackgroundColor(chartStyle.overlayLineColor)
        overlayPoint.backgroundTintList = ColorStateList.valueOf(chartStyle.overlayCircleColor)

        return this
    }

    @Suppress("UNUSED")
    fun setOnTouchCallbackListener(listener: OnTouchCallback): LiveChartTouchOverlay {
        this.touchListener = listener

        return this
    }

    /**
     * Bind the overlay to the dataset set in this [LiveChartTouchOverlay].
     */
    @PublicApi
    fun bindToDataset() {
        this.post {
            val path = Path().apply {
                dataset.points.forEachIndexed { index, point ->
                    // move path to first data point,
                    if (index == 0) {
                        moveTo(chartBounds.start + point.x.xPointToPixels(),
                            point.y.yPointToPixels())
                        return@forEachIndexed
                    }

                    lineTo(chartBounds.start + point.x.xPointToPixels(),
                        point.y.yPointToPixels())
                }
            }

            // Measure the dataset path.
            pathMeasure.setPath(path, false)

            invalidate()
        }
    }
    /**
     * Find the bounds data point to screen pixels ratio for the Y Axis.
     */
    private fun yBoundsToPixels(): Float {
        return if (secondDataset.hasData()) {
            (max(dataset.upperBound(),
                secondDataset.upperBound()) -
                    min(dataset.lowerBound(),
                        secondDataset.lowerBound())) / chartBounds.bottom
        } else {
            (dataset.upperBound() - dataset.lowerBound()) / chartBounds.bottom
        }
    }

    /**
     * Transform a Y Axis data point to screen pixels within bounds.
     */
    private fun Float.yPointToPixels(): Float {
        return if (secondDataset.hasData()) {
            chartBounds.bottom -
                    ((this - min(dataset.lowerBound(), secondDataset.lowerBound())) /
                            yBoundsToPixels())
        } else {
            chartBounds.bottom - ((this - dataset.lowerBound()) / yBoundsToPixels())
        }
    }

    /**
     * Transform a Y Axis screen pixels to a point within bounds.
     */
    private fun Float.yPixelsToPoint(): Float {
        return if (secondDataset.hasData()) {
            ((this - chartBounds.bottom) * -yBoundsToPixels()) +
                    min(dataset.lowerBound(), secondDataset.lowerBound())
        } else {
            ((this - chartBounds.bottom) * -yBoundsToPixels()) + dataset.lowerBound()
        }
    }

    /**
     * Find the bounds data point to screen pixels ratio for the X Axis.
     */
    private fun xBoundsToPixels(): Float {
        return if (secondDataset.hasData()) {
            (max(dataset.points.last().x, secondDataset.points.last().x) -
                    min(dataset.points.first().x, secondDataset.points.first().x)) /
                    (chartBounds.end - if (drawYBounds) chartStyle.chartEndPadding else 0f)
        } else {
            dataset.points.last().x /
                    (chartBounds.end - if (drawYBounds) chartStyle.chartEndPadding else 0f)
        }
    }

    /**
     * Transform a X Axis data point to screen pixels.
     */
    private fun Float.xPointToPixels(): Float {
        return this/xBoundsToPixels()
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        val coordinates = FloatArray(2)
        return when (event.actionMasked) {
            MotionEvent.ACTION_DOWN -> {
                val xNormalise = event.x /
                        (chartBounds.end - if (drawYBounds) chartStyle.chartEndPadding else 0f)

                overlay.alpha = 1f

                // Get coordinate in path from x
                pathMeasure.getPosTan(pathMeasure.length*xNormalise,
                    coordinates,
                    null)

                overlay.x = coordinates[0] - (chartStyle.overlayCircleDiameter/2)
                overlayPoint.y = coordinates[1] - (chartStyle.overlayCircleDiameter/2)

                touchListener?.onTouchCallback(DataPoint(
                    x = coordinates[0],
                    y = coordinates[1].yPixelsToPoint()
                ))

                true
            }

            MotionEvent.ACTION_MOVE -> {
                val xNormalise = event.x /
                        (chartBounds.end - if (drawYBounds) chartStyle.chartEndPadding else 0f)

                // Position overlay in coordinate,
                // Get coordinate in path from x
                pathMeasure.getPosTan(pathMeasure.length*xNormalise,
                    coordinates,
                    null)

                overlay.x = coordinates[0] - (chartStyle.overlayCircleDiameter/2)
                overlayPoint.y = coordinates[1] - (chartStyle.overlayCircleDiameter/2)

                touchListener?.onTouchCallback(DataPoint(
                    x = coordinates[0],
                    y = coordinates[1].yPixelsToPoint()
                ))

                true
            }

            MotionEvent.ACTION_UP,
            MotionEvent.ACTION_CANCEL,
            MotionEvent.ACTION_OUTSIDE -> {
                overlay.alpha = 0f
                true
            }

            else -> true
        }
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        // Account for padding
        val xpad = (paddingLeft + paddingRight).toFloat()
        val ypad = (paddingTop + paddingBottom).toFloat()

        val ww = w.toFloat() - xpad
        val hh = h.toFloat() - ypad

        // Figure out how big we can make the pie.
        chartBounds = Bounds(
            top = paddingTop.toFloat(),
            end = ww,
            bottom = hh,
            start = paddingLeft.toFloat()
        )
    }
}