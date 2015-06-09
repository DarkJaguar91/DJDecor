Dark Jaguar Decorations
===========================
===========================
A RecyclerView decor library.
Allows the user to easily add decorations to their RecyclerView.

Currently supported Decorations:
* Header decoration for LinearLayout RecyclerView.
  * Works for Both Vertical and Horizontal layouts.
  * Works with reversed layouts
  * Headers are cached and reused to ensure very little memory is utalised
  

### Header Example
[![animated gif demo](http://i.imgur.com/88dvq73.gif?1)](http://i.imgur.com/88dvq73.gif?1)

Download
---------
```
compile 'com.darkjaguar:dj-decor:0.2.2'
```

Usage
=====
Headers
--------
DJDecor Headers has 2 main classes: `DJHeaderDecor` and `DJHeaderDecorAdapter`.

The `DJHeaderDecorAdapter` is where the Header items will be identified, created and populated with data, it is highly recomended that you implement this interface on your current `RecyclerView.Adapter`. Please see the sample code for more details.

The `DJHeaderDecor` takes in the `DJHeaderDecorAdapter` you have created in order to draw the headers.
It is given to the `RecyclerView` as an Item Decoration:
```java
SimpleAdapter adapter = new SimpleAdapter();
recyclerView.setAdapter(adapter);
DJHeaderDecor djHeaderDecor = new DJHeaderDecor(adapter);
recyclerView.addItemDecoration(djHeaderDecor);
```

Compatibility
-------------
API 14+

Changelog
==========
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
