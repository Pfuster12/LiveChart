package com.yabu.livechart.model

data class Dataset(val points: MutableList<DataPoint>) {

    private var upperBound= 0.0f
    private var lowerBound= 0.0f

    init {
        val sortedPoint = points.sortedBy { point -> point.y }
        upperBound = sortedPoint.lastOrNull()?.y ?: 0f
        lowerBound = sortedPoint.firstOrNull { point -> point.x >= 0 }?.y ?: 0f
        // If the upper and lower bound are the same the chart won't draw anything because
        // `upperBound` and `lowerBound` are used or division and that can cause problems
        if(hasData() && lowerBound==upperBound){
            upperBound = increaseBound(upperBound)
        }
    }

    fun hasData(): Boolean {
        val sortedPoint = points.filter { point -> point.x > 0 }
        return sortedPoint.firstOrNull() != null

    }

    fun upperBound(): Float {
        return upperBound
    }

    fun lowerBound(): Float {
        return lowerBound
    }

    private fun increaseBound(bound:Float): Float{
        var step = bound
        var increase = 0.1f
        while (step < 1.0f) {
            step *= 10.0f
            increase *= 0.1f
        }
        return bound + increase
    }

    companion object {
        fun new() = Dataset(
            points = mutableListOf(DataPoint(0f, 0f))
        )
    }
}