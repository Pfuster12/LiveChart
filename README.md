## Welcome to the LiveChart Repository :wave:

[![](https://jitpack.io/v/Pfuster12/LiveChart.svg)](https://jitpack.io/#Pfuster12/LiveChart) [![Commitizen friendly](https://img.shields.io/badge/commitizen-friendly-brightgreen.svg)](http://commitizen.github.io/cz-cli/) [![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-LiveChart-blue.svg?style=flat)](https://android-arsenal.com/details/1/8143)

LiveChart is an open-source Android library to draw beautiful yet powerful charts. The library allows for color and data display customization, in an easy to learn, descriptive API.

<img src="/.sample-images/livechart_sample.png" height="680"/>

Draw from a simple line to a fully tagged chart with bounds and baseline. The library is perfect (and started out) to draw financial charts where the baseline and color of the chart matters.

## Add it to your app

To get LiveChart into your build:

**Step 1. Add the JitPack repository to your build file**

Add it in your root build.gradle at the end of repositories:
```gradle
allprojects {
	repositories {
		...
		maven { url 'https://jitpack.io' }
	}
}
```

**Step 2. Add the dependency**

```gradle
dependencies {
	  implementation 'com.github.Pfuster12:LiveChart:1.3.3'
}
```

## Contributing

Anybody is welcome to contribute! The repository is Commitizen friendly, please refer to their guidelines on commit messages.

## Roadmap

The LiveChart library has just started out. Have a look at the roadmap for new features in the horizon. Have a request? Open up an issue with a feature tag.

## How to Use

You'll need a reference to a `LiveChart` view first, either through XML or programmatically:

```xml
<?xml version="1.0" encoding="utf-8"?>
<FrameLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:clipChildren="false"
    tools:context=".MainActivity">

    <com.yabu.livechart.view.LiveChart
        android:id="@+id/live_chart"
        android:layout_width="match_parent"
        android:layout_height="300dp"/>

</FrameLayout>
```

```kotlin
val liveChart = findViewById(R.id.live_chart)
```

LiveChart knows how to draw from a `Dataset` only. Create a new dataset containing a list of `DataPoint`'s:

```kotlin
val liveChart = findViewById(R.id.live_chart)

 val dataset = Dataset(mutableListOf(DataPoint(0f, 1f),
    DataPoint(1f, 3f),
    DataPoint(2f, 6f)))
```

In order to begin the draw operation, the library uses a chainable, descriptive public API:

```kotlin
val liveChart = findViewById(R.id.live_chart)

 val dataset = Dataset(mutableListOf(DataPoint(0f, 1f),
    DataPoint(1f, 3f),
    DataPoint(2f, 6f)))

// set dataset, display options, and ... draw!
livechart.setDataset(dataset)
    .drawYBounds()
    .drawBaseline()
    .drawFill()
    .drawDataset()
```

## Draw Options

The chart can be as simple as drawing a line on a blank canvas:

```kotlin
 val dataset = Dataset(mutableListOf(DataPoint(0f, 1f),
    DataPoint(1f, 3f),
    DataPoint(2f, 6f)))

livechart.setDataset(dataset)
    .drawDataset()
```

Or provide the full set of display capabilities by adding a baseline, a gradient fill and
the axis bounds with data labels:

```kotlin
 val dataset = Dataset(mutableListOf(DataPoint(0f, 1f),
    DataPoint(1f, 3f),
    DataPoint(2f, 6f)))

livechart.setDataset(dataset)
     // Draws the Y Axis bounds with Text data points.
    .drawYBounds()
    // Draws a customizable base line from the first point of the dataset or manually set a data point
    .drawBaseline()
    // Set manually the data point from where the baseline draws,
    .setBaselineManually(1.5f)
    // Draws a fill on the chart line. You can set whether to draw with a transparent gradient
    // or a solid fill. Defaults to gradient.
    .drawFill(withGradient = true)
    // draws the color of the path and fill conditional to being above/below the baseline datapoint
    .drawBaselineConditionalColor()
    // Draw Guidelines in the background
    .drawVerticalGuidelines(steps=4)
    .drawHorizontalGuidelines(steps=4)
    // Draw smooth path
    .drawSmoothPath()
    // Draw last point tag label
    .drawLastPointLabel()
    .drawDataset()
```

Refer to the screenshot to view the different options and color change on below baseline/above baseline.

You can find all the possible draw options under the API reference.

## Styling

### Style Programmatically

Since `v1.1.0` LiveChart supports custom styling of almost all its interface 
through the `LiveChartStyle` class. The style object contains all available styling options as 
properties you can change:

```kotlin
val style = LiveChartStyle().apply {
    textColor = Color.BLUE
    textHeight = 30f 
    mainColor = Color.GREEN
    mainFillColor = Color.MAGENTA
    baselineColor = Color.BLUE
    pathStrokeWidth = 12f
    baselineStrokeWidth = 6f
}

// Pass the styling object to the view through the method setLiveChartStyle(style: LiveChartStyle)
livechart.setDataset(dataset)
    .setLiveChartStyle(style)
    .drawBaseline()
    .drawFill(withGradient = true)
    .drawYBounds()
    .drawDataset()
```

The above example would result in a rather horrible (yet accurate) view of:

<img src="/.sample-images/livechart_styling_example_1.png" height="120"/>

For the full set of attributes available to customise refer to the `LiveChartStyle` reference.
Any attributes not explicitly set fallback to the `LiveChartAttributes` object defaults you can view in the
reference too.

### Style with XML

You can also style a number of attributes through the XML layout attributes. For example:

```xml
    <com.yabu.livechart.view.LiveChart
        android:id="@+id/live_chart"
        android:layout_width="match_parent"
        android:layout_height="300dp"
        app:labelTextColor="@color/colorAccent"
        app:pathColor="@color/colorAccent"
        app:secondPathColor="@color/colorAccent"
        app:boundsColor="@color/grey"
        app:mainCornerRadius="4dp"
        app:secondCornerRadius="4dp"
        app:pathStrokeWidth="4dp"
        app:baselineStrokeWidth="4dp"
        app:baselineDashGap="8dp"
        app:labelTextHeight="14sp"
        app:baselineColor="@color/colorPrimaryDark"
        app:overlayCircleColor="@color/colorPrimaryDark"
        app:overlayLineColor="@color/colorPrimary"
        app:overlayCircleDiameter="8dp"/>
```

For a full set of available attributes you can check the `LiveChartView` reference.

## Second Dataset

The library allows for data comparisons by drawing a second dataset on the same chart. The
second dataset defaults to a grey color but you can set the color manually through the style object:

```kotlin
 val firstDataset = Dataset(mutableListOf(DataPoint(0f, 1f),
    DataPoint(1f, 2f),
    DataPoint(2f, 3f),
    DataPoint(3f, 4f),
    DataPoint(4f, 5f),
    DataPoint(5f, 8f),
    DataPoint(6f, 13f),
    DataPoint(7f, 21f)
))

val secondDataset = Dataset(mutableListOf(DataPoint(0f, 0f),
    DataPoint(1f, 1f),
    DataPoint(2f, 2f),
    DataPoint(3f, 3f),
    DataPoint(4f, 4f),
    DataPoint(5f, 5f),
    DataPoint(6f, 10f),
    DataPoint(7f, 18f)
))

val style = LiveChartStyle().apply {
    mainColor = Color.GRAY
    secondColor = Color.MAGENTA
    pathStrokeWidth = 8f
    secondPathStrokeWidth = 8f
}

livechart.setDataset(firstDataset)
    .setSecondDataset(secondDataset)
    .setLiveChartStyle(style)
    .drawYBounds()
    .drawDataset()
```

This results in the following chart:

<img src="/.sample-images/livechart_second_dataset_example_1.PNG" height="160"/>

> **NOTE** Want more than two datasets? Don't worry, the project roadmap intends to support drawing 
> an unlimited number of datasets provided in a list. 

## Touch Events
 
Since v1.2.0 LiveChart supports touch events and can draw a visual DataPoint slider that moves with your finger.

<img src="/.sample-images/livechart_slider.gif" height="160"/>

The touch overlay is built in to the `LiveChart` class. You can style the vertical slider and
circle through the `LiveChartStyle` object or through the XML attributes (See above):

```kotlin
val chartStyle = LiveChartStyle().apply {
    overlayLineColor = Color.BLUE
    overlayCircleDiameter = 32f
    overlayCircleColor = Color.GREEN
}

livechart.setDataset(dataset)
    .setLiveChartStyle(chartStyle)
    .drawDataset()
```

Just drawing the horizontal slider doesn't tell us much though. We can add a listener to get the current
`DataPoint` of the touch event with the `LiveChart.OnTouchCallback`:

```kotlin
val textView = findViewById(R.id.text_view)

livechart.setDataset(dataset)
        .setLiveChartStyle(chartStyle)
        .setOnTouchCallbackListener(object : LiveChart.OnTouchCallback {
            override fun onTouchCallback(point: DataPoint) {
                textView.text = "(${"%.2f".format(point.x)}, ${"%.2f".format(point.y)})"
            }
        })
        .drawDataset()
```

This allows us to show the current point the user is dragging along.

### Disabling the touch overlay

If you don't want the touch overlay it can be disabled easily:

```kotlin
livechart.setDataset(dataset)
        .disableTouchOverlay()
        .drawDataset()
```

You might want to do this when the chart view is too small to benefit from touch interactions,
or if you require extra optimization in your view drawing and would require as little overhead as
possible.

## Smooth Path

Since `1.3.0` LiveChart supports drawing a rounded path instead of straight corners.

<img src="/.sample-images/smooth_path.PNG" height="300"/>

There are two options to enable this feature:

1. Toggle an optimized rounded smooth path with `drawSmoothPath`. 

**NOTE** There is a calculation overhead to toggling it this way.
**NOTE** This is the only way for Touch overlay to work correctly.

```kotlin
livechart.setDataset(dataset)
        .drawSmoothPath()
        .drawDataset()
```

2. Use the built in Android CornerPathEffect by setting the corner radius in the `LiveChartStyle` properties:

**NOTE** Touch overlay doesn't work correctly here because the Android generated path is not available. 

```kotlin
val chartStyle = LiveChartStyle().apply {
    mainCornerRadius = 40f
}

livechart.setDataset(dataset)
    .setLiveChartStyle(chartStyle)
    .drawDataset()
```

## Guidelines

Since `1.3.1` the chart supports drawing vertical and/or horizontal chart guidelines at *n* number of steps:

<img src="/.sample-images/guidelines.PNG" height="300"/>

Simply toggle them and pass the desired number of steps. You can also color them through the `LiveChartStyle.guideLineColor` property:

```kotlin
livechart.setDataset(dataset)
    .drawVerticalGuidelines(steps=4)
    .drawHorizontalGuidelines(steps=4)
    .drawDataset()
```

## Known Issues with Touch Overlay

If you place the LiveChart in a scrollview the touch event gets consumed early.

Unfortunately this is a feature of the Android Touch system. Without ruining the ScrollView
functionality from the library side, a working solution is to call the 
LiveChart view's parent `requestDisallowInterceptTouchEvent(disallowIntercept: Boolean)` :

```
liveChart.setDataset(dataset)
                .setOnTouchCallbackListener(object : LiveChart.OnTouchCallback {
                    override fun onTouchCallback(point: DataPoint) {
                        liveChart
                            .parent
                            .requestDisallowInterceptTouchEvent(true)
                    }

                    override fun onTouchFinished() {
                         liveChart
                              .parent
                              .requestDisallowInterceptTouchEvent(false)
                     }
                })
                .drawDataset()
```

## Things to consider

LiveChart tries to leave a minimal footprint as possible, extending from the built-in Android `View` 
class to perform the draw operations. It follows best practice advice to only perform draw ops
and avoid setting any variables to memory during the `onDraw()` call.

**HOWEVER**, drawing big datasets is a costly operation and the Android UI will appear 'janky' if you
are not careful with the amount of data you feed in. 

A good Android citizen will only draw the necessary data points, avoid calling `drawDataset()` repeatedly
and not animate the `LiveChartView` excessively.

## API Reference

Dokka Generated Kdocs:

- [LiveChart](https://pfuster12.github.io/LiveChart/livechart/com.yabu.livechart.view/-live-chart/index.html)
- [LiveChartStyle](https://pfuster12.github.io/LiveChart/livechart/com.yabu.livechart.view/-live-chart-style/index.html)

Public Methods for LiveChart:

```kotlin

    /**
     * Set the [dataset] of this chart.
     */
    fun setDataset(dataset: Dataset): LiveChart

    /**
     * Set the Second [dataset] of this chart.
     */
    fun setSecondDataset(dataset: Dataset): LiveChart
    /**
     * Set the style object [LiveChartStyle] to this chart.
     */
    fun setLiveChartStyle(style: LiveChartStyle): LiveChart

    /**
     * Draw baseline flag.
     */
    fun drawBaselineConditionalColor(): LiveChart
    
    /**
     * Draw baseline flag.
     */
    fun drawBaseline(): LiveChart

    /**
     * Draw baseline automatically from first point.
     */
    fun drawBaselineFromFirstPoint(): LiveChart

    /**
     * Draw Fill flag.
     */
    fun drawFill(withGradient: Boolean = true): LiveChart
    
     /**
     * Draw smooth path flag.
     */
    fun drawSmoothPath(): LiveChart

     /**
     * Draw straight path flag.
     */
    fun drawStraightPath(): LiveChart

    /**
     * Disable Fill flag.
     */
    fun disableFill(): LiveChart

    /**
     * Draw Y bounds flag.
     */
    fun drawYBounds(): LiveChart

    /**
     * Draw last point label flag.
     */
    fun drawLastPointLabel(): LiveChart
    
    /**
     * Draw vertical guidelines
     * @param steps Number of guidelines
     */
    fun drawVerticalGuidelines(steps: Int): LiveChart

    /**
     * Draw horizontal guidelines
     * @param steps Number of guidelines
     */
    fun drawHorizontalGuidelines(steps: Int): LiveChart

    /**
     * Set [baseline] data point manually instead of determining from first dataset point.
     */
    fun setBaselineManually(baseline: Float): LiveChart
    
    /**
     * Set the OnTouchCallback interface.
     */
    fun setOnTouchCallbackListener(listener: OnTouchCallback): LiveChart

    /**
     * Disable the touch overlay component.
     * This is useful for small charts that do not benefit from showing the touch event
     * or as an optimization if you require less overhead on your View.
     */
    fun disableTouchOverlay(): LiveChart

    /**
     * Draw on chart and bind overlay to dataset.
     */
    fun drawDataset()
```

LiveChartStyle properties:

```kotlin

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
     * Baseline dash line gap width
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
```

### Using LiveChartView only

Even though the main entry point to this library is the `LiveChart` layout class which contains extra
touch functionality, the base `View` class `LiveChartView` that actually performs the drawing is kept 
public in case there is performance requirements and you don't need want the touch overlay views 
overhead (You can also disable it, see above).

It also allows to override it and add custom functionality to this base class, as LiveChart is kept final.

The View has the exact same public API and xml attributes as the `LiveChart` class so they are almost
interchangeable.
