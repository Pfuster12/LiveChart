package com.yabu.livechart.utils

/**
 * Annotates a function exposed to users of this library as public api
 * to interact with the live chart.
 */
@Target(AnnotationTarget.FUNCTION)
@MustBeDocumented
internal annotation class PublicApi