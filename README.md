# Prince of Versions

![Bitrise](https://app.bitrise.io/app/5bc3f35c9c5f5b61/status.svg?token=XGiXvE7Yu6DXdL9wrEqiHQ&branch=dev)
[![Download](https://api.bintray.com/packages/infinum/android/prince-of-versions/images/download.svg)](https://bintray.com/infinum/android/prince-of-versions/_latestVersion)

Library checks for updates using configuration from remote or local resource.

## Getting via jcenter

```groovy
implementation 'co.infinum:prince-of-versions:3.0.0'
```

## Features

  * Load update configuration from **network** resource or from generic **stream** resource.
  * Accepts **custom loader** for loading update configuration resource.
  * Use predefined parser for parsing update configuration in **JSON format**.
  * Accept **custom parser** for parsing update configuration.
  * Accept **custom requirements** for different updates.
  * Make **asynchronous** update check and use **callback** for notifying result.
  * Supports **synchronous** update check.
  * Loading and verifying versions happens **outside of the UI thread**.
  * Use **thread pool** to cap concurrent resource usage.
  * Provides functionality to **cancel** verification once started.


### Default parser and JSON file

JSON configuration should follow a structure from bellow:

```json
{
	"android": [{
		"required_version": 10,
		"last_version_available":12,
		"notify_last_version_frequency":"ONCE",
		"requirements":{
		   "required_os_version":18
		},
		"meta":{
		   "key1":"value3"
		}
	},{
		"required_version": 10,
		"last_version_available":13,
		"notify_last_version_frequency":"ONCE",
		"requirements":{
		   "required_os_version":19
		},
		"meta":{
		   "key2":"value4"
		}
	}],
	"meta": {
		"key1": "value1",
		"key2": "value2"
	}
}
```
The most important part of the configuration for Android applications is <code>android</code> object. All properties in the object are optional.

As it is shown, the <code>android</code> (or additionally <code>android2</code> if there is a need to support old configuration with <android> key) property contains a JSON array of updates, where the library is parsing them one by one and selecting the first acceptable update. If there is no <code>requirement</code> field, that means the first update in an array, but usually, it just means the first update that satisfies all the conditions. 

Metadata from the selected update has an advantage over the default metadata, and when there is a metadata conflict, its resolved by overriding the same fields by values from the selected update. E.g. if the first update gets selected then the final metadata will have these keys: <code> key1: value3 </code> and <code> key2: value2 </code>. In case when there is no conflict of keys in metadata, then we are just merging default metadata with selected update metadata.

Property <code>required_version</code> specifies the mandatory version of application, eg. if application has version lower than <code>required_version</code> - mandatory update will be notified. Semantic of mandatory update is that application has to be updated before any further use. Because of that, if mandatory update exists it will be notified on each update check.

Property <code>last_version_available</code> defines a version of the latest optional update. If application has version
lower that <code>last_version_available</code> - optional update will be notified.  
Depending on <code>notification_type</code> property, application can be notified <code>ONCE</code> or <code>ALWAYS</code>. The library handles this for you, and if notification type is set to <code>ONCE</code>, it will notify you only first time for a specific version. In every following check the library would notify <code>onNoUpdate</code> for that specific version. This setting applies only for optional update and has no effect in case of mandatory update. Default value for this property is <code>ONCE</code>.

<code>requirements</code> propery contains an object of different update requirements. Default requirement is <code>required_os_version</code>. <code>required_os_version</code> represents the minimum Android version a device has to support to be able to update to last available version. Eg. If <code>required_os_version</code> is set to <code>18</code>, a device has to have at least Android version 18 or above to be able to receive an update. Apart from default <code>required_os_version</code>, <code>requirements</code> can contain a variety of different and customizable requirements specific for each update.

Key-value pairs under <code>meta</code> key are optional <code>String</code> metadata which any amount can be sent accompanying the required fields. 

## Examples

Full example application is available [here](https://github.com/infinum/Android-Prince-of-Versions/tree/dev/ExampleApp).

#### Most common usage - loading from network resource
1. Create a new instance of updater associated with an application context.
```java
PrinceOfVersions updater = new PrinceOfVersions(this);
```

2. Create a loader factory for loading from the network passing resource URL.
```java
Loader loader = new NetworkLoader("https://pastebin.com/raw/DL1cR4u4");
```

3. Create a concrete callback to get the update check results by implementing <code>UpdaterCallback</code> interface.
```java
UpdaterCallback callback = new UpdaterCallback() {
    @Override
    public void onSuccess(UpdateResult result) {
    }

    @Override
    public void onError(Throwable throwable) {
    }
};
```

4. Use the updater with previously created loader and callback. Call <code>checkForUpdates</code> method to start asynchronous update check.
```java
PrinceOfVersionsCancelable cancelable = updater.checkForUpdates(loaderFactory, callback);
```

5. To cancel the update check, call <code>cancel</code> method available in <code>PrinceOfVersionsCancelable</code> object.

#### UpdaterCall api
In version 3.0.0 a new UpdaterCall API has been introduced.

1. Create a new <code>PrinceOfVersionsCall</code> instance.
```java
PrinceOfVersionsCall call = updater.newCall("https://pastebin.com/raw/DL1cR4u4");

// If you have previously created a Loader instance, you can use it to create a PrinceOfVersionsCall instance.
PrinceOfVersionsCall call = updater.newCall(loader);
```

2. If you want to use call in an asynchronous manner - enqueue the call to be executed asynchronously, the result will be returned to your callback on the main thread.
```java
call.enqueue(new UpdaterCallback() {
    @Override
    public void onSuccess(UpdateResult result) {
    }
    
    @Override
    public void onError(Throwable throwable) {
    }
});
```

3. If you want to use the call in a synchronous manner call the <code>execute</code> method. It returns a <code>UpdateResult</code> object containing the version check results.
```java
try {
    UpdateResult result = call.execute();
    // result.getStatus() returns MANDATORY, OPTIONAL or NO_UPDATE
// result.getInfo() returns update info - the information extracted from the configuration and used to check if the update exists
    // result.getMetadata() returns metadata about the update
} catch (Throwable throwable) {
    // handle error
}
```

4. To cancel the update check, call <code>cancel</code> method in <code>PrinceOfVersionsCall</code> object.

5. Be aware that once a call has been executed it cannot be reused, you must create a new instance.

#### Writing tests

For testing purposes you can create your own Loader instance. For ease of use, StreamLoader object exists in the library. Here is an example of loading a JSON file from raw resource.

1. Create new instance of updater associated with application context.
```java
PrinceOfVersions updater = new PrinceOfVersions(this);
```

2. Create loader factory for creating stream loader by passing new input stream in its constructor.
```java
Loader loader = new StreamLoader(getResources().openRawResource(R.raw.update))
```

> **Note:**
> Be aware that once used input stream in <code>StreamLoader</code> is read and closed. For that purpose always create new stream for every update check.

3rd, 4th and 5th step are same as in previous example.

#### Writing tests

If you write tests with asynchronous version of update check included, you probably want all PrinceOfVersion's work to be executed on main test thread. You can do that by providing <code>Executor</code> instance in update check method. Library includes <code>PrinceOfVersionsDefaultExecutor</code> class for executing update check on separate thread and <code>SingleThreadExecutor</code> for executing update check immediately.
```java
princeOfVersions.checkForUpdates(executor, loader, callback);
```

### Multiple flavors
If your application has multiple product flavors (e.g. paid/free) you might need more than one JSON configuration file. If that is the case, do not forget to set a different URL for each flavor configuration.


### R8 / ProGuard

If you are using R8 or ProGuard add the options from
[this file](./prince-of-versions/prince-of-versions.pro).


### Contributing

Feedback and code contributions are very much welcome. Just make a pull request with a short description of your changes. By making contributions to this project you give permission for your code to be used under the same [license](https://github.com/infinum/Android-prince-of-versions/blob/dev/LICENCE).
