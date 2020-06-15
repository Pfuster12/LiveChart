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
    var secondColor: Int = Color.GRAY

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
     * Baseline color.
     */
    var baselineColor: Int = Color.GRAY

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
     * Baseline dash line gap width
     */
    var chartEndPadding = LiveChartAttributes.CHART_END_PADDING
}