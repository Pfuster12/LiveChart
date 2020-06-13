# Welcome to LiveChart

LiveChart is an open-source Android library to draw beautiful yet powerful charts.

The library allows for color and data display customization, in an easy to learn, descriptive API.

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

The LiveChart can be as simple as drawing a line on a blank canvas:

```kotlin
 val dataset = Dataset(mutableListOf(DataPoint(0f, 1f),
    DataPoint(1f, 3f),
    DataPoint(2f, 6f)))

livechart.setDataset(dataset)
    .drawDataset()
```

Or provide the full set of display capabilities by adding a baseline line, a gradient fill and
the axis bounds with text data points:

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
    .setBaselineManually()
    // draws a gradient fill on the chart line,
    .drawFill()
    .drawDataset()
```

## Second Dataset

The library allows for powerful comparisons by drawing a second dataset on the same chart. The
second dataset defaults to a grey color but you can set the color manually:

```kotlin
val firstDataset = Dataset(mutableListOf(DataPoint(0f, 1f),
    DataPoint(1f, 3f),
    DataPoint(2f, 6f)))
    
 val secondDataset = Dataset(mutableListOf(DataPoint(3f, 0f),
    DataPoint(4f, 10f),
    DataPoint(6f, 2f)))

livechart.setDataset(dataset)
    .setSecondDataset(secondDataset)
    .setSecondDatasetColor(Color.BLACK)
    .drawDataset()
```

## Things to consider

LiveChart tries to leave a minimal footprint as possible, extending from the built-in Android `View` 
class to perform the draw operations. It follows best practice advice to only perform draw ops
and avoid setting any variables to memory during the `onDraw()` call.

HOWEVER, drawing big datasets is a costly operation and the Android UI will appear 'janky' if you
are not careful with the amount of data you feed in. 

Good Android behavior is to only draw the necessary data points, avoid calling `drawDataset()` repeatedly
and not animate the `LiveChartView` excessively.