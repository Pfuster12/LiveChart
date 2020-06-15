## Welcome to the LiveChart Repository :wave:

[![](https://jitpack.io/v/Pfuster12/LiveChart.svg)](https://jitpack.io/#Pfuster12/LiveChart) [![Commitizen friendly](https://img.shields.io/badge/commitizen-friendly-brightgreen.svg)](http://commitizen.github.io/cz-cli/)

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
	  implementation 'com.github.Pfuster12:LiveChart:v1.0.0'
}
```

## Contributing

Anybody is welcome to contribute! The repository is Commitizen friendly, please refer to their guidelines on commit messages.

## Roadmap

The LiveChart library has just started out. Have a look at the roadmap for new features in the horizon. Have a request? Open up an issue with a feature tag.

## How to Use

LiveChart knows how to draw from a `Dataset` only. Create a new dataset containing a list of `DataPoint`'s:

```kotlin
 val dataset = Dataset(mutableListOf(DataPoint(0f, 1f),
    DataPoint(1f, 3f),
    DataPoint(2f, 6f)))
```

In order to begin the draw operation, the library uses a chainable, descriptive public API:

```kotlin
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
    // Draws a customizable base line from the first point of the dataset or manually set a datapoint
    .drawBaseline()
    // Set manually the data point from where the baseline draws,
    .setBaselineManually(1.5f)
    // draws a gradient fill on the chart line,
    .drawFill()
    .drawBaselineConditionalColor()
    .drawDataset()
```

Refer to the screenshot to view the different options and color change on below baseline/above baseline.

You can find all the possible draw options under the API reference.

## Styling

Since `v1.1.0` LiveChart supports custom styling of almost all its interface 
through the `LiveChartStyle` class. The style object contains all available styling options as 
properties you can change:

```kotlin
val style = LiveChartStyle().apply {
    textColor = Color.BLUE
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
    .drawFill()
    .drawYBounds()
    .drawDataset()
```

The above example would result in a rather horrible (yet accurate) view of:

<img src="/.sample-images/livechart_styling_example_1.png" height="120"/>

For the full set of attributes available to customise refer to the LiveChartStyle reference.
Any attributes not explicitly set fallback to the LiveChartAttributes object defaults you can view in the
reference too.

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

<img src="/.sample-images/livechart_second_dataset_example_1.png" height="120"/>

> **NOTE** Want more than two datasets? Don't worry, the project roadmap intends to support drawing 
> an unlimited number of datasets provided in a list. 

## Things to consider

LiveChart tries to leave a minimal footprint as possible, extending from the built-in Android `View` 
class to perform the draw operations. It follows best practice advice to only perform draw ops
and avoid setting any variables to memory during the `onDraw()` call.

**HOWEVER**, drawing big datasets is a costly operation and the Android UI will appear 'janky' if you
are not careful with the amount of data you feed in. 

A good Android citizen will only draw the necessary data points, avoid calling `drawDataset()` repeatedly
and not animate the `LiveChartView` excessively.
