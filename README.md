Dark Jaguar Decorations
===========================
===========================
A RecyclerView decor library.
Allows the user to easily add decorations to their RecyclerView.

Currently supported Decorations:
* Header decoration for LinearLayout RecyclerView.
  * Works for Both Vertical ~~and Horizontal layouts~~.
  * ~~Works with reversed layouts~~
  * Headers are cached and reused to ensure very little memory is utalised
  * Hides the floating view that obscures elements when not scrolling
  * Has swipe to refresh capability with xml attributes

### Header Example
[![animated gif demo](http://i.imgur.com/88dvq73.gif?1)](http://i.imgur.com/88dvq73.gif?1)

Download
---------
```
compile 'com.darkjaguar:dj-decor:0.3.7'
```

Usage
=====
Headers
--------
DJDecor Headers has 2 main classes: `DJDecorRecyclerView` and `DJHeaderDecorAdapter`.

The `DJHeaderDecorAdapter` is where the Header items will be identified, created and populated with data, it is highly recomended that you implement this interface on your current `RecyclerView.Adapter`. Please see the sample code for more details.

The `DJDecorRecyclverView` replaces your current RecyclerView in your XML layout.
Add the view yo your xml as follows:
```xml
<com.darkjaguar.dj_decor.header.DJDecorRecyclerView
        xmlns:djdecorrecyclerviewattributes="http://schemas.android.com/apk/res-auto"
        android:id="@+id/<your id>"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        djdecorrecyclerviewattributes:swipe_refresh="true"/>
```
Please note the djdecorrecyclerviewattributes field has a swipe_refresh value. If set to true the recycler view will have a swipe refresh layout added to it that can be accessed by calling `djRecyclerView.getSwipeRefreshLayout()`.

The actual `RecyclerView` can be accessed by calling
`djRecyclerView.getRecyclerView()`

If you add a `DJHeaderDecorAdapter` to the `DJDecorRecyclverView` headers will be added automagically.
```java
DJDecorRecyclerView djRecyclerView = (DJDecorRecyclerView) view.findViewById(R.id.my_dj_recycler);
MyAdapter adapter = new MyAdapter(); // extends RecyclerView.Adapter and implements DJHeaderDecorAdapter
djRecyclerView.getRecyclerView().setAdapter(adapter);
djRecyclerView.setHideFloatingView(true); // hides floating view if not scrolling (default true)
djRecyclerView.setHideDuration(300); // Sets how long it takes for the floating view to animate out in MS
djRecyclerView.setHideDelay(2000); // Sets how long after stopping scrolling before view animates out in MS

djRecyclerView.getSwipeRefreshLayout().setOnRefreshListener(...<your refresh listener>...);
```

Compatibility
-------------
API 14+

Changelog
==========
V0.3.7
------
* Added support for animating out the header that obscures view when not scrolling
* Added support for XML attributed swipe refresh layout integration
* Removed horizontal and reversed support until floating view animation is fixed for those orientations

V0.2.3
------
* Added Javadoc
* Fixed some spelling issues

V0.2.2
-------
* Fixed spelling issues
* Fixed caching (allowing for clearing of cache)
* Fixed layout width calculation

V0.2.0
------
* Added compatability for Horizontal layouts
* Added compatability for resersed layouts

V0.1.0
-------
* Initial release with only vertical layouts that are not reversed
