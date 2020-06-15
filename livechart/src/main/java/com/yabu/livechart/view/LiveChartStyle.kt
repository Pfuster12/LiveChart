package com.yabu.livechart.view

import android.graphics.Color

/**
 * Class containing style properties to format a LiveChart.
 */
class LiveChartStyle {

    /**
     * Label text color.
     */
    var textColor: Int = Color.BLACK

    /**
     * Main color
     */
    var mainColor: Int = Color.BLACK

    /**
     * Positive from baseline fill color.
     */
    var mainFillColor: String = LiveChartAttributes.FILL_COLOR

    /**
     * Positive from baseline color.
     */
    var positiveColor: String = LiveChartAttributes.POSITIVE_COLOR

    /**
     * Negative from baseline color.
     */
    var negativeColor: String = LiveChartAttributes.NEGATIVE_COLOR

    /**
     * Positive from baseline fill color.
     */
    var positiveFillColor: String = LiveChartAttributes.POSITIVE_FILL_COLOR

    /**
     * Negative from baseline fill color.
     */
    var negativeFillColor: String = LiveChartAttributes.NEGATIVE_FILL_COLOR

    /**
     * Baseline color.
     */
    var baselineColor: Int = Color.GRAY

    /**
     * Path stroke width
     */
    var pathStrokeWidth = LiveChartAttributes.STROKE_WIDTH

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