package com.yabu.livechart.view

import android.graphics.Color

/**
 * Class containing style properties to format a LiveChart.
 */
class LiveChartStyle {

    /**
     * Label text color.
     */
    var textColor: Int = LiveChartAttributes.TEXT_COLOR

    /**
     * Main color
     */
    var mainColor: Int = LiveChartAttributes.MAIN_COLOR

    /**
     * Main color
     */
    var secondColor: Int = LiveChartAttributes.SECOND_COLOR

    /**
     * Positive from baseline fill color.
     */
    var mainFillColor: Int = Color.parseColor(LiveChartAttributes.FILL_COLOR)

    /**
     * Positive from baseline color.
     */
    var positiveColor: Int = Color.parseColor(LiveChartAttributes.POSITIVE_COLOR)

    /**
     * Negative from baseline color.
     */
    var negativeColor: Int =  Color.parseColor(LiveChartAttributes.NEGATIVE_COLOR)

    /**
     * Positive from baseline fill color.
     */
    var positiveFillColor: Int =  Color.parseColor(LiveChartAttributes.POSITIVE_FILL_COLOR)

    /**
     * Negative from baseline fill color.
     */
    var negativeFillColor: Int = Color.parseColor(LiveChartAttributes.NEGATIVE_FILL_COLOR)

    /**
     * Main Path corner radius pixel amount.
     */
    var mainCornerRadius: Float = LiveChartAttributes.CORNER_RADIUS

    /**
     * Second Path corner radius pixel amount.
     */
    var secondCornerRadius: Float = LiveChartAttributes.CORNER_RADIUS

    /**
     * Baseline color.
     */
    var baselineColor: Int = LiveChartAttributes.BASELINE_LINE_COLOR

    /**
     * Bounds color.
     */
    var boundsLineColor: Int = LiveChartAttributes.BOUNDS_LINE_COLOR

    /**
     * Baseline color.
     */
    var guideLineColor: Int = LiveChartAttributes.GUIDELINE_COLOR

    /**
     * Path stroke width
     */
    var pathStrokeWidth = LiveChartAttributes.STROKE_WIDTH

    /**
     * Second dataset Path stroke width
     */
    var secondPathStrokeWidth = LiveChartAttributes.STROKE_WIDTH

    /**
     * Baseline stroke width
     */
    var baselineStrokeWidth = LiveChartAttributes.BASELINE_STROKE_WIDTH

    /**
     * Baseline dash line width
     */
    var baselineDashLineWidth = LiveChartAttributes.DASH_LINE_STROKE

    /**
     * Baseline dash line gap width
     */
    var baselineDashLineGap = LiveChartAttributes.DASH_LINE_GAP

    /**
     * Axis Padding
     */
    var chartEndPadding = LiveChartAttributes.CHART_END_PADDING

    /**
     * Chart text height
     */
    var textHeight = LiveChartAttributes.TEXT_HEIGHT

    /**
     * Overlay vertical line color.
     */
    var overlayLineColor = LiveChartAttributes.OVERLAY_LINE_COLOR

    /**
     * Overlay livechart_circle color.
     */
    var overlayCircleColor = LiveChartAttributes.OVERLAY_CIRCLE_COLOR

    /**
     * Overlay livechart_circle diameter.
     */
    var overlayCircleDiameter = LiveChartAttributes.OVERLAY_CIRCLE_DIAMETER
}