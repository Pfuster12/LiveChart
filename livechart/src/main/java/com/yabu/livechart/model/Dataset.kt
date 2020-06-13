package com.yabu.livechart.model

data class Dataset(val points: MutableList<DataPoint>) {

    fun hasData(): Boolean {
        val sortedPoint = points.filter { point -> point.x > 0 }
        return sortedPoint.firstOrNull() != null

    }

    fun upperBound(): Float {
        val sortedPoint = points.sortedBy { point -> point.y }
        return sortedPoint.lastOrNull()?.y ?: 0f
    }

    fun lowerBound(): Float {
        val sortedPoint = points.sortedBy { point -> point.y }
            .filter { point -> point.x >= 0 }
        return sortedPoint.firstOrNull()?.y ?: 0f
    }

    companion object {
        fun new() = Dataset(
            points = mutableListOf(DataPoint(0f, 0f))
        )
    }
}