package com.yabu.livechart.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import com.yabu.livechart.model.DataPoint
import com.yabu.livechart.model.Dataset
import com.yabu.livechart.utils.PublicApi

/**
 * A Live Chart displays a 2 Dimensional [Dataset], with an optional live
 * subscription to push new data points to the end of the data set.
 *
 * The chart can have a baseline onto which the end point data set is compared to determine whether
 * it has positive or negative change, and highlights the data set accordingly with color.
 *
 * The end data point can be tagged with a label and draw a line across the chart, with highlighted
 * color according to the baseline.
 *
 * The [FrameLayout] contains two modular components: a [LiveChartView] and a [LiveChartTouchOverlay]
 * working together to handle drawing and offer touch events on the chart.
 * @param context
 * @param attrs
 */
class LiveChart(context: Context, attrs: AttributeSet?) : FrameLayout(context, attrs) {

    interface OnTouchCallback {
        fun onTouchCallback(point: DataPoint)

        fun onTouchFinished()
    }

    /**
     * [LiveChartView] reference.
     * Pass the attributes to the chart.
     */
    private val livechart = LiveChartView(context, attrs).apply {
        layoutParams = LayoutParams(LayoutParams.MATCH_PARENT,
            LayoutParams.MATCH_PARENT)
    }

    /**
     * [LiveChartTouchOverlay] reference.
     * Pass the attributes to the view.
     */
    private val overlay = LiveChartTouchOverlay(context, attrs).apply {
        layoutParams = LayoutParams(LayoutParams.MATCH_PARENT,
            LayoutParams.MATCH_PARENT)
    }

    /**
     * Flag to disable touch overlay.
     */
    private var disableTouchOverlay: Boolean = false

    init {
        clipToOutline = false
        clipToPadding = false
        clipChildren = false

        // Add the views to the layout.
        this.addView(livechart)
        this.addView(overlay)
    }

    /**
     * Set the [dataset] of this chart.
     */
    @PublicApi
    fun setDataset(dataset: Dataset): LiveChart {
        livechart.setDataset(dataset)
        overlay.setDataset(dataset)
        return this
    }

    /**
     * Set the Second [dataset] of this chart.
     */
    @PublicApi
    fun setSecondDataset(dataset: Dataset): LiveChart {
        livechart.setSecondDataset(dataset)
        overlay.setSecondDataset(dataset)
        return this
    }

    /**
     * Set the style object [LiveChartStyle] to this chart.
     */
    @PublicApi
    fun setLiveChartStyle(style: LiveChartStyle): LiveChart {
        livechart.setLiveChartStyle(style)
        overlay.setLiveChartStyle(style)
        return this
    }

    /**
     * Draw baseline flag.
     */
    @Suppress("UNUSED")
    @PublicApi
    fun drawBaselineConditionalColor(): LiveChart {
        livechart.drawBaselineConditionalColor()

        return this
    }

    /**
     * Draw baseline flag.
     */
    @PublicApi
    fun drawBaseline(): LiveChart {
        livechart.drawBaseline()

        return this
    }

    /**
     * Draw [baseline] automatically from first point.
     */
    @Suppress("UNUSED")
    @PublicApi
    fun drawBaselineFromFirstPoint(): LiveChart {
        livechart.drawBaselineFromFirstPoint()

        return this
    }

    /**
     * Draw Fill flag.
     */
    @PublicApi
    fun drawFill(withGradient: Boolean = true): LiveChart {
        livechart.drawFill(withGradient)
        return this
    }

    /**
     * Disable Fill flag.
     */
    @Suppress("UNUSED")
    @PublicApi
    fun disableFill(): LiveChart {
        livechart.disableFill()
        return this
    }

    /**
     * Draw Y bounds flag.
     */
    @PublicApi
    fun drawYBounds(): LiveChart {
        livechart.drawYBounds()
        overlay.drawYBounds()
        return this
    }

    /**
     * Draw last point label flag.
     */
    @Suppress("UNUSED")
    @PublicApi
    fun drawLastPointLabel(): LiveChart {
        livechart.drawLastPointLabel()

        return this
    }

    /**
     * Set [baseline] data point manually instead of determining from first dataset point.
     */
    @Suppress("UNUSED")
    @PublicApi
    fun setBaselineManually(baseline: Float): LiveChart {
        livechart.setBaselineManually(baseline)

        return this
    }

    @Suppress("UNUSED")
    @PublicApi
    fun setOnTouchCallbackListener(listener: OnTouchCallback): LiveChart {
        overlay.setOnTouchCallbackListener(listener)

        return this
    }

    /**
     * Disable the touch overlay component.
     * This is useful for small charts that do not benefit from showing the touch event
     * or as an optimization if you require less overhead on your View.
     */
    @Suppress("UNUSED")
    @PublicApi
    fun disableTouchOverlay(): LiveChart {
        overlay.visibility = View.GONE
        disableTouchOverlay = true
        return this
    }

    /**
     * Draw on chart and bind overlay to dataset.
     */
    @PublicApi
    fun drawDataset() {
        livechart.drawDataset()
        if (!disableTouchOverlay) {
            overlay.bindToDataset()
        }
    }
}