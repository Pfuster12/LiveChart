package com.yabu.livechart.view

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import com.yabu.livechart.R
import com.yabu.livechart.model.Bounds
import com.yabu.livechart.model.Dataset
import com.yabu.livechart.utils.PublicApi
import com.yabu.livechart.view.LiveChartAttributes.CHART_END_PADDING
import com.yabu.livechart.view.LiveChartAttributes.DASH_LINE_GAP
import com.yabu.livechart.view.LiveChartAttributes.DASH_LINE_STROKE
import com.yabu.livechart.view.LiveChartAttributes.NEGATIVE_COLOR
import com.yabu.livechart.view.LiveChartAttributes.NEGATIVE_FILL_COLOR
import com.yabu.livechart.view.LiveChartAttributes.POSITIVE_COLOR
import com.yabu.livechart.view.LiveChartAttributes.POSITIVE_FILL_COLOR
import com.yabu.livechart.view.LiveChartAttributes.STROKE_WIDTH
import com.yabu.livechart.view.LiveChartAttributes.TAG_PADDING
import com.yabu.livechart.view.LiveChartAttributes.TAG_WIDTH
import com.yabu.livechart.view.LiveChartAttributes.TEXT_HEIGHT
import kotlin.math.max
import kotlin.math.min

/**
 * A Live Chart displays a 2 Dimensional lined data points, with an optional live
 * subscription to push new data points to the end of the data set.
 *
 * The chart can have a baseline onto which the end point data set is compared to determine whether
 * it has positive or negative change, and highlights the data set accordingly with color.
 *
 * The end data point can be tagged with a label and draw a line across the chart, with highlighted
 * color according to the baseline.
 */
class LiveChartView : View {
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    init {
        clipToOutline = false
    }

    private var chartBounds = Bounds(
        top = paddingTop.toFloat(),
        end = paddingEnd.toFloat(),
        bottom = paddingBottom.toFloat(),
        start = paddingLeft.toFloat()
    )

    /**
     * Baseline to determine paint color from data end point.
     */
    private var baseline: Float = 0f

    /**
     * Dataset points to draw on chart.
     */
    private var dataset: Dataset = Dataset.new()

    /**
     * Previous day dataset
     */
    private var previousDataset: Dataset = Dataset.new()

    /**
     * Y Bounds display flag.
     */
    private var drawYBounds = false

    /**
     * Baseline display flag.
     */
    private var drawBaseline = false

    /**
     * Fill display flag.
     */
    private var drawFill = false

    /**
     * Manually set baseline flag.
     */
    private var manualBaseline = false

    /**
     * Bounds text color.
     */
    private var boundsTextColor = Color.BLACK

    /**
     * Previous data set path color.
     */
    private var previousDatasetPathColor = ContextCompat.getColor(context,
        R.color.previousDataset)

    /**
     * Set the [dataset] of this chart.
     */
    @PublicApi
    fun setDataset(dataset: Dataset): LiveChartView {
        this.dataset = dataset
        return this
    }

    /**
     * Set the previous [dataset] of this chart.
     */
    @PublicApi
    fun setPreviousDataset(dataset: Dataset): LiveChartView {
        this.previousDataset = dataset
        return this
    }

    /**
     * Draw baseline flag.
     */
    @PublicApi
    fun drawBaseline(): LiveChartView {
        drawBaseline = true

        return this
    }

    /**
     * Draw Fill flag.
     */
    @PublicApi
    fun drawFill(): LiveChartView {
        drawFill = true

        return this
    }

    /**
     * Draw Y bounds flag.
     */
    @PublicApi
    fun drawYBounds(): LiveChartView {
        drawYBounds = true

        return this
    }

    /**
     * Set [baseline] data point manually instead of determining from first dataset point.
     */
    @PublicApi
    fun setBaselineManually(baseline: Float): LiveChartView {
        manualBaseline = true
        this.baseline = baseline

        return this
    }

    /**
     * Set Previous dataset path color.
     */
    @PublicApi
    fun setPreviousDatasetPathColor(pathColor: Int): LiveChartView {
        previousDatasetPathColor = pathColor

        previousDatasetPaint.color = pathColor

        return this
    }

    /**
     * Set bounds text color.
     */
    @PublicApi
    fun setBoundsTextColor(boundsColor: Int): LiveChartView {
        boundsTextColor = boundsColor

        boundsTextPaint.color = boundsTextColor

        return this
    }

    /**
     * Helper to set the chart highlight color according to [baseline]
     */
    private fun Paint.setColor() {
        this.color = if (dataset.points.last().y > baseline) {
            Color.parseColor(POSITIVE_COLOR)
        } else {
            Color.parseColor(NEGATIVE_COLOR)
        }
    }

    /**
     * Path generated from dataset points.
     */
    private var datasetPath = Path().apply {
        moveTo(chartBounds.start, baseline)
        dataset.points.forEach { point ->
            lineTo(point.x,
                baseline.yPointToPixels())
        }
    }

    /**
     * Line [Paint] for this chart.
     */
    private var datasetLinePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        strokeWidth =
            STROKE_WIDTH
        setColor()
        strokeCap = Paint.Cap.BUTT
        strokeJoin = Paint.Join.MITER
    }

    /**
     * Path generated from dataset points.
     */
    private var previousDatasetPath = Path().apply {
        moveTo(chartBounds.start, baseline)
        dataset.points.forEach { point ->
            lineTo(point.x,
                baseline.yPointToPixels())
        }
    }

    /**
     * Line [Paint] for this chart.
     */
    private var previousDatasetPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        strokeWidth =
            STROKE_WIDTH
        color = previousDatasetPathColor
        strokeCap = Paint.Cap.BUTT
        strokeJoin = Paint.Join.MITER
    }

    /**
     * Path generated from dataset points.
     */
    private var datasetFillPath = Path().apply {
        moveTo(chartBounds.start, baseline)
        dataset.points.forEach { point ->
            lineTo(point.x,
                point.y)
        }
    }

    /**
     * Line [Paint] for this chart.
     */
    private var datasetFillPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        isDither = true
        setColor()
    }

    /**
     * Line [Paint] for this chart.
     */
    private var baselinePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        strokeWidth = LiveChartAttributes.BASELINE_STROKE_WIDTH
        pathEffect = DashPathEffect(floatArrayOf(
            DASH_LINE_STROKE,
            DASH_LINE_GAP
        ), 0f)
        color = Color.GRAY
        strokeCap = Paint.Cap.SQUARE
        strokeJoin = Paint.Join.ROUND
    }

    /**
     * Line [Paint] for this chart.
     */
    private var yBoundLinePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        strokeWidth = 1f
        color = Color.GRAY
        strokeCap = Paint.Cap.SQUARE
    }

    /**
     * End Point Line [Paint] for this chart.
     */
    private var endPointLinePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        strokeWidth = 3f
        setColor()
        strokeCap = Paint.Cap.SQUARE
        strokeJoin = Paint.Join.ROUND
    }

    /**
     * End Point Tag [Paint] for this chart.
     */
    private var endPointTagPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        typeface = Typeface.DEFAULT_BOLD
        setColor()
    }

    /**
     * End Point Tag [Paint] for this chart.
     */
    private var endPointTagTextPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.WHITE
        typeface = Typeface.DEFAULT_BOLD
        textSize = TEXT_HEIGHT
    }

    /**
     * End Point Tag [Paint] for this chart.
     */
    private var boundsTextPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = boundsTextColor
        textSize = TEXT_HEIGHT
    }

    /**
     * Find the bounds data point to screen pixels ratio for the Y Axis.
     */
    private fun yBoundsToPixels(): Float {
        return if (previousDataset.hasData()) {
            (max(dataset.upperBound(),
                previousDataset.upperBound()) -
                    min(dataset.lowerBound(),
                        previousDataset.lowerBound())) / chartBounds.bottom
        } else {
            (dataset.upperBound() - dataset.lowerBound()) / chartBounds.bottom
        }
    }

    /**
     * Transform a Y Axis data point to screen pixels within bounds.
     */
    private fun Float.yPointToPixels(): Float {
        return if (previousDataset.hasData()) {
            chartBounds.bottom -
                    ((this - min(dataset.lowerBound(),
                        previousDataset.lowerBound())) / yBoundsToPixels())
        } else {
            chartBounds.bottom - ((this - dataset.lowerBound()) / yBoundsToPixels())
        }
    }

    /**
     * Find the bounds data point to screen pixels ratio for the X Axis.
     */
    private fun xBoundsToPixels(): Float {
        return dataset.points.last().x /
                (chartBounds.end - if (drawYBounds) CHART_END_PADDING else 0f)
    }

    /**
     * Transform a X Axis data point to screen pixels.
     */
    private fun Float.xPointToPixels(): Float {
        return this/xBoundsToPixels()
    }

    /**
     * Set the charts paints current color.
     */
    private fun setChartHighlightColor() {
        datasetLinePaint.setColor()
        endPointLinePaint.setColor()
        endPointTagPaint.setColor()
    }

    /**
     * Draw the current [dataset].
     */
    @PublicApi
    fun drawDataset(): LiveChartView {
        this.post {
            if (dataset.points.isNullOrEmpty()) {
                return@post
            }

            if (!manualBaseline) {
                baseline = dataset.points.first().y
            }

            datasetPath = Path().apply {
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

            previousDatasetPath = Path().apply {
                previousDataset.points.forEachIndexed { index, point ->
                    // move path to first data point,
                    if (index == 0) {
                        moveTo(chartBounds.start, point.y.yPointToPixels())
                        return@forEachIndexed
                    }

                    lineTo(chartBounds.start + point.x.xPointToPixels(),
                        point.y.yPointToPixels())
                }
            }

            datasetFillPath = Path().apply {
                dataset.points.forEachIndexed { index, point ->
                    // move path to first data point,
                    if (index == 0) {
                        moveTo(chartBounds.start, baseline.yPointToPixels())
                        return@forEachIndexed
                    }

                    lineTo(chartBounds.start + point.x.xPointToPixels(),
                        point.y.yPointToPixels())
                }
                lineTo(chartBounds.start + dataset.points.last().x.xPointToPixels(),
                    chartBounds.bottom)
                lineTo(chartBounds.start,
                    chartBounds.bottom)
            }

            val fillColor = if (dataset.points.last().y > baseline) {
                POSITIVE_FILL_COLOR
            } else {
                NEGATIVE_FILL_COLOR
            }

            datasetFillPaint.shader = LinearGradient(chartBounds.start,
                dataset.upperBound().yPointToPixels(),
                chartBounds.start,
                chartBounds.bottom,
                Color.parseColor(fillColor),
                Color.parseColor("#00000000"),
                Shader.TileMode.CLAMP)

            setChartHighlightColor()

            // invalidate view to call onDraw,
            invalidate()
        }

        return this
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        if (!dataset.hasData()) {
            return
        }

        if (drawBaseline) {
            // draw baseline
            canvas.drawLine(chartBounds.start,
                baseline.yPointToPixels(),
                // account for end padding only if Y Bounds are visible
                chartBounds.end  - if (drawYBounds) CHART_END_PADDING else 0f,
                baseline.yPointToPixels(),
                baselinePaint)
        }

        if (previousDataset.points.size > 1) {
            canvas.drawPath(previousDatasetPath,
                previousDatasetPaint)
        }

        // draw dataset
        canvas.drawPath(datasetPath,
            datasetLinePaint)

        if (drawFill) {
            canvas.drawPath(datasetFillPath,
                datasetFillPaint)
        }

        if (drawYBounds) {
            // draw y Bounds line,
            canvas.drawLine(chartBounds.end - CHART_END_PADDING,
                chartBounds.top,
                chartBounds.end - CHART_END_PADDING,
                chartBounds.bottom,
                yBoundLinePaint)

            // LOWER BOUND
            canvas.drawText("%.2f".format(dataset.lowerBound()),
                chartBounds.end - TAG_WIDTH,
                chartBounds.bottom,
                boundsTextPaint)

            // UPPER BOUND
            canvas.drawText("%.2f".format(dataset.upperBound()),
                chartBounds.end - TAG_WIDTH,
                chartBounds.top,
                boundsTextPaint)

            // draw end tag line
            canvas.drawLine(chartBounds.start,
                dataset.points.last().y.yPointToPixels(),
                chartBounds.end - CHART_END_PADDING,
                dataset.points.last().y.yPointToPixels(),
                endPointLinePaint)

            // TAG
            canvas.drawRect(chartBounds.end - CHART_END_PADDING,
                dataset.points.last().y.yPointToPixels() - TEXT_HEIGHT - TAG_PADDING,
                chartBounds.end - CHART_END_PADDING + TAG_WIDTH,
                dataset.points.last().y.yPointToPixels(),
                endPointTagPaint)

            canvas.drawText("%.2f".format(dataset.points.last().y),
                chartBounds.end - CHART_END_PADDING + TAG_PADDING,
                dataset.points.last().y.yPointToPixels() - TAG_PADDING,
                endPointTagTextPaint)
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