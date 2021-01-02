package com.yabu.livechart.view

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import com.yabu.livechart.R
import com.yabu.livechart.model.Bounds
import com.yabu.livechart.model.Dataset
import com.yabu.livechart.utils.EPointF
import com.yabu.livechart.utils.PolyBezierPathUtil
import com.yabu.livechart.utils.PublicApi
import com.yabu.livechart.view.LiveChartAttributes.TAG_PADDING
import com.yabu.livechart.view.LiveChartAttributes.TAG_WIDTH
import kotlin.math.max
import kotlin.math.min

/**
 * Base [View] subclass handling the drawing of dataset paths and chart bounds.
 */
open class LiveChartView(context: Context, attrs: AttributeSet?) : View(context, attrs) {

    /**
     * The chart bounds in the screen pixel space
     */
    private var chartBounds = Bounds(
        top = paddingTop.toFloat(),
        end = paddingEnd.toFloat(),
        bottom = paddingBottom.toFloat(),
        start = paddingLeft.toFloat()
    )

    /**
     * Baseline to determine paint color from data end point.
     */
    private var chartStyle: LiveChartStyle = LiveChartStyle()

    /**
     * Baseline to determine paint color from data end point.
     */
    private var baseline: Float = 0f

    /**
     * Dataset points to draw on chart.
     */
    private var dataset: Dataset = Dataset.new()

    /**
     * Second dataset
     */
    private var secondDataset: Dataset = Dataset.new()

    /**
     * The upper bound of this chart's dataset's.
     */
    private var upperBound = 0f

    /**
     * The lowest bound of this chart's dataset's.
     */
    private var lowerBound: Float = 0f

    /**
     * Y Bounds display flag.
     */
    private var drawYBounds = false

    /**
     * Y Bounds gravity.
     */
    private var yAxisGravity = Gravity.END

    /**
     * Y Guidelines display flag.
     */
    private var drawVerticalGuidelines = false

    /**
     * Y Number of guidelines.
     */
    private var verticalGuidelineStep = 4

    /**
     * X Guidelines display flag.
     */
    private var drawHorizontalGuidelines = false

    /**
     * X Number of guidelines.
     */
    private var horizontalGuidelineStep = 4

    /**
     * Baseline display flag.
     */
    private var drawBaseline = false

    /**
     * Smooth Path flag
     */
    private var drawSmoothPath = false

    /**
     * Fill display flag.
     */
    private var drawFill = false

    /**
     * Fill gradient display flag.
     */
    private var drawGradientFill = true

    /**
     * Label last point.
     */
    private var drawLastPointLabel = false

    /**
     * Disable baseline conditional.
     */
    private var drawBaselineConditionalColor = false

    /**
     * Manually set baseline flag.
     */
    private var manualBaseline = false

    init {
        clipToOutline = false

        // Gather the xml attributes to style the chart,
        context.theme?.obtainStyledAttributes(
            attrs,
            R.styleable.LiveChart,
            0, 0)?.apply {

            try {
                // Color attrs,
                chartStyle.mainColor = getColor(R.styleable.LiveChart_pathColor,
                    chartStyle.mainColor)
                chartStyle.baselineColor = getColor(R.styleable.LiveChart_baselineColor,
                    chartStyle.baselineColor)
                chartStyle.mainFillColor = getColor(R.styleable.LiveChart_fillColor,
                    chartStyle.mainFillColor)
                chartStyle.textColor = getColor(R.styleable.LiveChart_labelTextColor,
                    chartStyle.textColor)
                chartStyle.positiveColor = getColor(R.styleable.LiveChart_positiveColor,
                    chartStyle.positiveColor)
                chartStyle.negativeColor = getColor(R.styleable.LiveChart_negativeColor,
                    chartStyle.negativeColor)
                chartStyle.boundsLineColor = getColor(R.styleable.LiveChart_boundsColor,
                    chartStyle.boundsLineColor)

                // Width attrs,
                chartStyle.pathStrokeWidth = getDimension(R.styleable.LiveChart_pathStrokeWidth,
                    chartStyle.pathStrokeWidth)
                chartStyle.baselineStrokeWidth = getDimension(R.styleable.LiveChart_baselineStrokeWidth,
                    chartStyle.baselineStrokeWidth)
                chartStyle.baselineDashLineGap = getDimension(R.styleable.LiveChart_baselineDashGap,
                    chartStyle.baselineDashLineGap)

                chartStyle.textHeight = getDimension(R.styleable.LiveChart_labelTextHeight,
                    chartStyle.textHeight)
            } finally {
                recycle()
            }
        }
    }

    /**
     * Reset the chart to defaults.
     */
    @PublicApi
    fun reset() {
        baseline = 0f
        secondDataset = Dataset.new()
        upperBound = 0f
        lowerBound = 0f
        drawYBounds = false
        yAxisGravity = Gravity.END
        drawVerticalGuidelines = false
        verticalGuidelineStep = 4
        drawHorizontalGuidelines = false
        horizontalGuidelineStep = 4
        drawBaseline = false
        drawSmoothPath = false
        drawFill = false
        drawGradientFill = true
        drawLastPointLabel = false
        drawBaselineConditionalColor = false
        manualBaseline = false
    }

    /**
     * Set the [dataset] of this chart.
     */
    @PublicApi
    fun setDataset(dataset: Dataset): LiveChartView {
        // reset the chart to defaults.
        reset()

        this.dataset = dataset
        return this
    }

    /**
     * Set the Second [dataset] of this chart.
     */
    @PublicApi
    fun setSecondDataset(dataset: Dataset): LiveChartView {
        this.secondDataset = dataset
        return this
    }

    /**
     * Set the style object [LiveChartStyle] to this chart.
     */
    @PublicApi
    fun setLiveChartStyle(style: LiveChartStyle): LiveChartView {
        chartStyle = style

        // paint color
        datasetLinePaint.color = chartStyle.mainColor
        datasetFillPaint.color =chartStyle.mainFillColor
        boundsLinePaint.color =chartStyle.boundsLineColor
        guideLinePaint.color = chartStyle.guideLineColor
        baselinePaint.color = chartStyle.baselineColor
        boundsTextPaint.color = chartStyle.textColor
        secondDatasetPaint.color = chartStyle.secondColor

        // stroke width
        datasetLinePaint.strokeWidth = chartStyle.pathStrokeWidth
        secondDatasetPaint.strokeWidth = chartStyle.secondPathStrokeWidth
        baselinePaint.strokeWidth = chartStyle.baselineStrokeWidth
        // baseline path effect
        if (chartStyle.baselineDashLineGap > 0f) {
            baselinePaint.pathEffect = DashPathEffect(floatArrayOf(
                chartStyle.baselineDashLineWidth,
                chartStyle.baselineDashLineGap
            ), 0f)
        }

        return this
    }

    /**
     * Draw baseline flag.
     */
    @Suppress("UNUSED")
    @PublicApi
    fun drawBaselineConditionalColor(): LiveChartView {
        drawBaselineConditionalColor = true

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
     * Draw smooth path flag.
     */
    @PublicApi
    fun drawSmoothPath(): LiveChartView {
        drawSmoothPath = true

        return this
    }

    /**
     * Draw straight path flag.
     */
    @PublicApi
    fun drawStraightPath(): LiveChartView {
        drawSmoothPath = false
        chartStyle.mainCornerRadius = 0f
        chartStyle.secondCornerRadius = 0f

        return this
    }

    /**
     * Draw Fill flag.
     */
    @PublicApi
    fun drawFill(withGradient: Boolean = true): LiveChartView {
        drawFill = true
        drawGradientFill = withGradient
        return this
    }

    /**
     * Disable Fill flag.
     */
    @Suppress("UNUSED")
    @PublicApi
    fun disableFill(): LiveChartView {
        drawFill = false
        drawGradientFill = false
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
     * Set the Y Axis horizontal gravity (Start or End).
     */
    @PublicApi
    fun setYAxisGravity(gravity: Int): LiveChartView {
        yAxisGravity = gravity

        return this
    }

    /**
     * Draw last point label flag.
     */
    @Suppress("UNUSED")
    @PublicApi
    fun drawLastPointLabel(): LiveChartView {
        drawLastPointLabel = true

        return this
    }

    /**
     * Set [baseline] data point manually instead of determining from first dataset point.
     */
    @Suppress("UNUSED")
    @PublicApi
    fun setBaselineManually(baseline: Float): LiveChartView {
        manualBaseline = true
        this.baseline = baseline

        return this
    }

    /**
     * Draw [baseline] automatically from first point.
     */
    @Suppress("UNUSED")
    @PublicApi
    fun drawBaselineFromFirstPoint(): LiveChartView {
        manualBaseline = false
        drawBaseline = true

        return this
    }

    /**
     * Draw vertical guidelines
     * @param steps Number of guidelines
     */
    @Suppress("UNUSED")
    @PublicApi
    fun drawVerticalGuidelines(steps: Int): LiveChartView {
        drawVerticalGuidelines = true
        verticalGuidelineStep = steps

        return this
    }

    /**
     * Draw horizontal guidelines
     * @param steps Number of guidelines
     */
    @Suppress("UNUSED")
    @PublicApi
    fun drawHorizontalGuidelines(steps: Int): LiveChartView {
        drawHorizontalGuidelines = true
        horizontalGuidelineStep = steps

        return this
    }

    /**
     * Helper to set the chart highlight color according to [baseline]
     */
    private fun Paint.setColor() {
        if (drawBaselineConditionalColor) {
            this.color = if (dataset.points.last().y > baseline) {
                chartStyle.positiveColor
            } else {
                chartStyle.negativeColor
            }
        } else {
            this.color = chartStyle.mainColor
        }
    }

    /**
     * Path generated from dataset points.
     */
    private var datasetPath = Path()

    /**
     * Line [Paint] for this chart.
     */
    private var datasetLinePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        strokeWidth = chartStyle.pathStrokeWidth
        setColor()
        pathEffect = CornerPathEffect(LiveChartAttributes.CORNER_RADIUS)
        strokeCap = Paint.Cap.BUTT
        strokeJoin = Paint.Join.MITER
    }

    /**
     * Path generated from dataset points.
     */
    private var secondDatasetPath = Path()

    /**
     * Line [Paint] for this chart.
     */
    private var secondDatasetPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        strokeWidth = chartStyle.secondPathStrokeWidth
        color = chartStyle.secondColor
        strokeCap = Paint.Cap.BUTT
        strokeJoin = Paint.Join.MITER
    }

    /**
     * Path generated from dataset points.
     */
    private var datasetFillPath = Path()

    /**
     * Fill [Paint] for this chart.
     */
    private var datasetFillPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        isDither = true
        setColor()
    }

    /**
     * Baseline Line [Paint] for this chart.
     */
    private var baselinePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        strokeWidth = chartStyle.baselineStrokeWidth
        if (chartStyle.baselineDashLineGap > 0f) {
            pathEffect = DashPathEffect(floatArrayOf(
                chartStyle.baselineDashLineWidth,
                chartStyle.baselineDashLineGap
            ), 0f)
        }
        color = chartStyle.baselineColor
        strokeCap = Paint.Cap.SQUARE
        strokeJoin = Paint.Join.ROUND
    }

    /**
     * Bounds [Paint] for this chart.
     */
    private var boundsLinePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        strokeWidth = 1f
        color = chartStyle.boundsLineColor
        strokeCap = Paint.Cap.SQUARE
    }

    /**
     * Guideline [Paint] for this chart.
     */
    private var guideLinePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        strokeWidth = 1f
        color = chartStyle.guideLineColor
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
     * End Point Tag Text [Paint] for this chart.
     */
    private var endPointTagTextPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.WHITE
        typeface = Typeface.DEFAULT_BOLD
        textSize = chartStyle.textHeight
    }

    /**
     * Bounds Text [Paint] for this chart.
     */
    private var boundsTextPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = chartStyle.textColor
        textSize = chartStyle.textHeight
    }

    /**
     * Find the bounds data point to screen pixels ratio for the Y Axis.
     */
    private fun yBoundsToPixels(): Float {
        return (upperBound - lowerBound) / chartBounds.bottom
    }

    /**
     * Transform a Y Axis data point to screen pixels within bounds.
     */
    private fun Float.yPointToPixels(): Float {
        return chartBounds.bottom - ((this - lowerBound) / yBoundsToPixels())
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

            // Assign the lower bound of this chart,
            lowerBound = if (secondDataset.hasData()) {
               min(dataset.lowerBound(), secondDataset.lowerBound())
            } else {
                dataset.lowerBound()
            }

            // Assign the upper bound of this chart,
            upperBound = if (secondDataset.hasData()) {
                max(dataset.upperBound(), secondDataset.upperBound())
            } else {
                dataset.upperBound()
            }

            if (drawSmoothPath) {
                if (dataset.points.size > 1) {
                    datasetPath = PolyBezierPathUtil.computePathThroughDataPoints(
                        dataset.points.map {
                            EPointF(it.x.xPointToPixels(), it.y.yPointToPixels())
                        })
                }

                if (secondDataset.points.size > 1) {
                    secondDatasetPath = PolyBezierPathUtil
                        .computePathThroughDataPoints(
                            secondDataset.points.map {
                                EPointF(it.x.xPointToPixels(), it.y.yPointToPixels())
                            })
                }
            } else {
                datasetPath = Path().apply {
                    dataset.points.forEachIndexed { index, point ->
                        // move path to first data point,
                        if (index == 0) {
                            moveTo(
                                chartBounds.start + point.x.xPointToPixels(),
                                point.y.yPointToPixels()
                            )
                            return@forEachIndexed
                        }

                        lineTo(
                            chartBounds.start + point.x.xPointToPixels(),
                            point.y.yPointToPixels()
                        )
                    }
                }

                secondDatasetPath = Path().apply {
                    secondDataset.points.forEachIndexed { index, point ->
                        // move path to first data point,
                        if (index == 0) {
                            moveTo(
                                chartBounds.start + point.x.xPointToPixels(),
                                point.y.yPointToPixels()
                            )
                            return@forEachIndexed
                        }

                        lineTo(
                            chartBounds.start + point.x.xPointToPixels(),
                            point.y.yPointToPixels()
                        )
                    }
                }
            }

            datasetFillPath = Path().apply {
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
                lineTo(chartBounds.start + dataset.points.last().x.xPointToPixels(),
                    chartBounds.bottom)
                lineTo(chartBounds.start + dataset.points.first().x.xPointToPixels(),
                    chartBounds.bottom)
            }

            // Gradient paint
            var fillColor = chartStyle.mainFillColor

            if (drawBaselineConditionalColor) {
                fillColor = if (dataset.points.last().y > baseline) {
                    chartStyle.positiveFillColor
                } else {
                    chartStyle.negativeFillColor
                }
            }

            if (drawGradientFill) {
                datasetFillPaint.shader = LinearGradient(chartBounds.start,
                    dataset.upperBound().yPointToPixels(),
                    chartBounds.start,
                    chartBounds.bottom,
                    fillColor,
                    Color.parseColor(LiveChartAttributes.TRANSPARENT_COLOR),
                    Shader.TileMode.CLAMP)
            } else {
                datasetFillPaint.color = fillColor
            }

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

        if (drawVerticalGuidelines) {
            for (i in 0..verticalGuidelineStep) {
                // draw vertical guidelines
                canvas.drawLine((if (drawYBounds)
                    (chartBounds.end - chartStyle.chartEndPadding)/verticalGuidelineStep
                else
                    chartBounds.end/verticalGuidelineStep)*i,
                    chartBounds.bottom,
                    (if (drawYBounds)
                        (chartBounds.end - chartStyle.chartEndPadding)/verticalGuidelineStep
                    else
                        chartBounds.end/verticalGuidelineStep)*i,
                    chartBounds.top,
                    guideLinePaint
                )
            }
        }

        if (drawHorizontalGuidelines) {
            for (i in 0..horizontalGuidelineStep) {
                // draw vertical guidelines
                canvas.drawLine(chartBounds.start,
                    (chartBounds.bottom/horizontalGuidelineStep)*i,
                    if (drawYBounds)
                        (chartBounds.end - chartStyle.chartEndPadding)
                    else
                        chartBounds.end,
                    (chartBounds.bottom/horizontalGuidelineStep)*i,
                    guideLinePaint
                )
            }
        }

        if (drawBaseline) {
            // draw baseline
            canvas.drawLine(chartBounds.start,
                baseline.yPointToPixels(),
                // account for end padding only if Y Bounds are visible
                chartBounds.end  - if (drawYBounds) chartStyle.chartEndPadding else 0f,
                baseline.yPointToPixels(),
                baselinePaint)
        }

        if (secondDataset.points.size > 1) {
            canvas.drawPath(secondDatasetPath,
                secondDatasetPaint)
        }

        // draw dataset
        canvas.drawPath(datasetPath,
            datasetLinePaint)

        if (drawFill) {
            canvas.drawPath(datasetFillPath,
                datasetFillPaint)
        }

        if (drawYBounds) {
            when (yAxisGravity) {
                Gravity.START -> {
                    // draw y Bounds line,
                    canvas.drawLine(chartBounds.start + chartStyle.chartEndPadding,
                        chartBounds.top,
                        chartBounds.start + chartStyle.chartEndPadding,
                        chartBounds.bottom,
                        boundsLinePaint)

                    // LOWER BOUND
                    canvas.drawText("%.2f".format(lowerBound),
                        chartBounds.start + TAG_PADDING,
                        chartBounds.bottom,
                        boundsTextPaint)

                    // FIRST QUARTER BOUND
                    canvas.drawText(
                        "%.2f".format(upperBound-(upperBound - lowerBound)/4f),
                        chartBounds.start + TAG_PADDING,
                        chartBounds.top + chartBounds.bottom/4f,
                        boundsTextPaint)

                    // MIDDLE BOUND
                    canvas.drawText(
                        "%.2f".format(upperBound-(upperBound - lowerBound)/2f),
                        chartBounds.start + TAG_PADDING,
                        chartBounds.top + chartBounds.bottom/2f,
                        boundsTextPaint)

                    // THIRD QUARTER BOUND
                    canvas.drawText(
                        "%.2f".format(upperBound-(upperBound - lowerBound)*0.75f),
                        chartBounds.start + TAG_PADDING,
                        chartBounds.top + chartBounds.bottom*0.75f,
                        boundsTextPaint)

                    // UPPER BOUND
                    canvas.drawText("%.2f".format(upperBound),
                        chartBounds.start + TAG_PADDING,
                        chartBounds.top,
                        boundsTextPaint)
                }

                Gravity.END -> {
                    // draw y Bounds line,
                    canvas.drawLine(chartBounds.end - chartStyle.chartEndPadding,
                        chartBounds.top,
                        chartBounds.end - chartStyle.chartEndPadding,
                        chartBounds.bottom,
                        boundsLinePaint)

                    // LOWER BOUND
                    canvas.drawText("%.2f".format(lowerBound),
                        chartBounds.end - chartStyle.chartEndPadding + TAG_PADDING,
                        chartBounds.bottom,
                        boundsTextPaint)

                    // FIRST QUARTER BOUND
                    canvas.drawText(
                        "%.2f".format(upperBound-(upperBound - lowerBound)/4f),
                        chartBounds.end - chartStyle.chartEndPadding + TAG_PADDING,
                        chartBounds.top + chartBounds.bottom/4f,
                        boundsTextPaint)

                    // MIDDLE BOUND
                    canvas.drawText(
                        "%.2f".format(upperBound-(upperBound - lowerBound)/2f),
                        chartBounds.end - chartStyle.chartEndPadding + TAG_PADDING,
                        chartBounds.top + chartBounds.bottom/2f,
                        boundsTextPaint)

                    // THIRD QUARTER BOUND
                    canvas.drawText(
                        "%.2f".format(upperBound-(upperBound - lowerBound)*0.75f),
                        chartBounds.end - chartStyle.chartEndPadding + TAG_PADDING,
                        chartBounds.top + chartBounds.bottom*0.75f,
                        boundsTextPaint)

                    // UPPER BOUND
                    canvas.drawText("%.2f".format(upperBound),
                        chartBounds.end - chartStyle.chartEndPadding + TAG_PADDING,
                        chartBounds.top,
                        boundsTextPaint)
                }
            }

            // Last Point Label
            if (drawLastPointLabel) {
                // draw end tag line
                canvas.drawLine(chartBounds.start,
                    dataset.points.last().y.yPointToPixels(),
                    chartBounds.end - chartStyle.chartEndPadding,
                    dataset.points.last().y.yPointToPixels(),
                    endPointLinePaint)

                // TAG
                canvas.drawRect(chartBounds.end - chartStyle.chartEndPadding,
                    dataset.points.last().y.yPointToPixels() -
                            chartStyle.textHeight -
                            TAG_PADDING,
                    chartBounds.end - chartStyle.chartEndPadding + TAG_WIDTH,
                    dataset.points.last().y.yPointToPixels(),
                    endPointTagPaint)

                canvas.drawText("%.2f".format(dataset.points.last().y),
                    chartBounds.end - chartStyle.chartEndPadding + TAG_PADDING,
                    dataset.points.last().y.yPointToPixels() - TAG_PADDING,
                    endPointTagTextPaint)
            }
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