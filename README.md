This is `RxJava` tutorial based on `RxJava2`

`RxJava` Anatomy, how `RxJava` is designed

   [Reference](https://blog.mindorks.com/rxjava-anatomy-what-is-rxjava-how-rxjava-is-designed-and-how-rxjava-works-d357b3aca586)

Module `operator` and Module `practice`

### `operator` module

**This module contains frequently used RxJava operators tutorial**

* Basic Usage: `Observable` subscribe `Observer`

* Thread Scheduler: how to change the event execute thread with `subscribeOn()` and `observeOn()`

* Create Observable: 
      1. create it with collection `create`,`just`,`fromArray`,`fromIterable`
      2. create it special one `empty`,`error`,`never`
      3. create it with time `defer`,`timer`,`interval`,`intervalRange`,`range`,`rangeLong`

* Transform Observable Type: `map`, `flatMap`, `concatMap`, `concatMap`, `buffer` 

* Combine Observable: `concat`, `concatDelayError`, `merge`, `mergeDelayError`, `startWith`

* Combine Observable onNext Event: `zip`, `combineLatest`, `reduce`, `collect`, (`count` get events total count)  
* Codition Boolean: `all`,`takeWhile`,`skipWhile`,`takeUntil`,`skipUntil`,`sequenceEqual`,`contains`,`isEmpty`,`amb`,`defaultIfEmpty`
* Back Pressure
    1. (Async) Flowable Subscriber work on different thread 
    2. (Sync) Flowable Subscriber work on same thread
    3. (Strategy) `error`, `missing`, `buffer`, `drop`, `latest`
    
* Filter 
    1. Event Condition Filter: `filter`, `ofType`, `skipIndex`, `skipTime`, `distinct`, `distinctUntilChanged`
    2. Event Position Filter: `firstElement`, `lastElement`, `elementAt`, `elementAtOrError`
    3. Event Size Filter: `take`, `takeLast`
    4. Event Time Filter: `throttleFirst`, `throttleLast`, `sample`, `throttleWithTimeOut`, `debounce`   
    
### `practice` moudle

**This module contains pratical situation in an Application, and how to get it with RxJava**

* How to fetch a network response with `Retrofit`, `Okhttp` and `RxJava`

* How to **bridge view's events** with `RxJava` 

* How to implement a **polling** request **easily** with `RxJava`

* How to implement a network request **failed retry** logic **easily** with `RxJava`

* How to implement a **nesting request** (register success login immediately) **exsily** with `RxJava`

* How to implement a **multiple level cache** logic easily with `RxJava`

* How to handle a **multiple data source** logic easily with `RxJava`

* How to **throttle events** triggered by the user easily with `RxJava` (user click button frequently but only invoke `onClick()` once in a time)

* How to throttle events and **handle the latest one** easily with `RxJava` (search input view tips) 




