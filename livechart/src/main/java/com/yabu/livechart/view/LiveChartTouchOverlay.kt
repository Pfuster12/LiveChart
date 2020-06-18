package com.yabu.livechart.view

import android.R.attr.path
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.animation.ValueAnimator.AnimatorUpdateListener
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Path
import android.graphics.PathMeasure
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.widget.FrameLayout
import com.yabu.livechart.R
import com.yabu.livechart.model.Bounds
import com.yabu.livechart.model.Dataset
import com.yabu.livechart.utils.PublicApi


/**
 * Touch overlay for the [LiveChartView]. Perform touch event draws in this class
 * instead of the LiveChart to avoid unnecessary expensive onDraw calls.
 * For example, the horizontal drag DataPoint slider.
 */
class LiveChartTouchOverlay : FrameLayout {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

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

    init {
        overlayPoint = overlay.findViewById(R.id.touch_overlay_point)
        overlay.alpha = 0f
        this.addView(overlay)
    }

    /**
     * Baseline to determine paint color from data end point.
     */
    private var chartStyle: LiveChartStyle = LiveChartStyle()

    /**
     * [Dataset] this overlay responds to.
     */
    private var dataset: Dataset = Dataset.new()

    /**
     * Path generated from dataset points.
     */
    private val pathMeasure = PathMeasure()

    /**
     * Y Bounds display flag.
     */
    private var drawYBounds = false

    /**
     * Draw Y bounds flag.
     */
    @PublicApi
    fun drawYBounds(): LiveChartTouchOverlay {
        drawYBounds = true

        return this
    }

    /**
     * Set the [Dataset] this overlay responds to.
     * @param dataset
     */
    @PublicApi
    fun setDataset(dataset: Dataset): LiveChartTouchOverlay {
        this.dataset = dataset

        return this
    }

    /**
     * Attach the overlay to the dataset set in this [LiveChartTouchOverlay].
     */
    @PublicApi
    fun bindOverlay() {
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
        return (dataset.upperBound() - dataset.lowerBound()) / chartBounds.bottom
    }

    /**
     * Transform a Y Axis data point to screen pixels within bounds.
     */
    private fun Float.yPointToPixels(): Float {
        return chartBounds.bottom - ((this - dataset.lowerBound()) / yBoundsToPixels())
    }

    /**
     * Find the bounds data point to screen pixels ratio for the X Axis.
     */
    private fun xBoundsToPixels(): Float {
        return  dataset.points.last().x /
                    (chartBounds.end - if (drawYBounds) chartStyle.chartEndPadding else 0f)
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
                val xNormalise = event.x / chartBounds.end

                overlay.alpha = 1f

                // Get coordinate in path from x
                pathMeasure.getPosTan(pathMeasure.length*xNormalise,
                    coordinates,
                    null)

                overlay.x = coordinates[0] - 6f
                overlayPoint.y = coordinates[1] - 6f
                true
            }

            MotionEvent.ACTION_MOVE -> {
                val xNormalise = event.x / chartBounds.end

                // Position overlay in coordinate,
                // Get coordinate in path from x
                pathMeasure.getPosTan(pathMeasure.length*xNormalise,
                    coordinates,
                    null)

                overlay.x = coordinates[0] - 6f
                overlayPoint.y = coordinates[1] - 6f
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