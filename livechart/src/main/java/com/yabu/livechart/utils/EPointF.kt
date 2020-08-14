package com.yabu.livechart.utils

/**
 * Code adapted for LiveChart from Stuart Kent - July 3, 2015
 *  https://www.stkent.com/2015/07/03/building-smooth-paths-using-bezier-curves.html
 */
class EPointF(val x: Float, val y: Float) {

    fun plus(factor: Float, ePointF: EPointF): EPointF {
        return EPointF(x + factor * ePointF.x, y + factor * ePointF.y)
    }

    operator fun plus(ePointF: EPointF): EPointF {
        return plus(1.0f, ePointF)
    }

    fun minus(factor: Float, ePointF: EPointF): EPointF {
        return EPointF(x - factor * ePointF.x, y - factor * ePointF.y)
    }

    operator fun minus(ePointF: EPointF): EPointF {
        return minus(1.0f, ePointF)
    }

    fun scaleBy(factor: Float): EPointF {
        return EPointF(factor * x, factor * y)
    }

}