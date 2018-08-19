# Review Browser

This is an example project displaying reviews. An Android project
with a single screen, executing API calls to request reviews based on
user-defined filters, as well as displaying them in a list.

# Technologies & Libraries

* Kotlin
* ViewModel
* Dagger
* Retrofit
* RxJava
* JUnit
* Espresso

# Architecture

The codebase is split into three modules:

* common
* api
* app

### Module: common

The common module is a `Java library` module.

The common module contains code that is required in multiple modules.
It serves as a base-module providing the basic required interfaces, for
example the `SchedulerConfiguration` which will later-on provide defined
`Schedulers` for `io`, `computation` and `ui` operations.

### Module: api

The api module is an `Android library` module.

All code related to the api consumption is placed within this module.
For now, the module only contains a `ReviewApi` interface to be
consumed with `Retrofit` as well as a `ReviewRepository` interface.

The `ReviewRepository` interface has two implementations, one for live
data as well as one for mock data to be used during UI tests.

The api module also exposes `dagger modules` which can be included in
the main application's component. Depending on the build configuration,
the modules will expose the live data implementation of the data
repository, or a mocked repository variant.

### Module: app

The app module is the `Android application` module.

It contains the Dagger `Component` which combines `Modules` from the
different project-modules. Also contains the main `AndroidManifest`
as well as since it's just a single screen the UI.

The UI can be split into feature-modules once more complex screens and
flows emerge, at this point the `app` module would connect the dots.

# Configuration

* /config/api.properties
  * Contains server URI and configuration elements
* /config/version.properties
  * Contains a `MAJOR.MINOR.PATCH` version setup

# Build Configuration

### Build Flavors

#### internal

To be used during development. At some point it should provide a
development or staging environment.

#### mock

To be used within UI tests. Uses mock data-sources with prepared
data sets to verify the user interface's behavior.

#### production

To be used to access production servers and live implementations of
data-sources and everything else.

### Signing Configurations

Not set up.

# Unit Tests

Each component containing logic also has unit tests. The unit tests
are in the same module-scope as the component itself.

The `api` module contains unit tests for the `ReviewRepository` which
ensure that the API's responses are parsed correctly regarding the
`status` field in the response and the data's nullability.

# UI Tests

UI Tests are executed using the default Android test runner. The tests
are using espresso.

The tests are using mocked data sources (which are provided by the `api`
module). At the moment, only the `app` module contains UI thus it's
the only module also containing UI tests.

The execute the tests, execute the `connectedMockDebugAndroidTest`
gradle task. It is important to use the `mock` build flavor
for this, since this is the only configuration using the mocked data
sources.

# Next Steps & Todo

* Project: Make the design pretty / more user friendly
* Project: Make `city` and `tour` parameters for review browsing
dynamic
* Project: Layer of abstraction on top of the `ReviewViewModel` -
add an interface to define the data contract.
* Project: Add screen to show 3rd party libraries and licenses used
* Project: Add more documentation to code (interfaces / contracts)
* Project: Set-up signing configuration
* Project: Enable ProGuard
* Testing: Create `BaseTest` class with helper methods regarding
the Activity's lifecycle
* Testing: Enable taking screenshots of test cases
* Testing: Increase test coverage

# License

   Copyright 2018 Damian Burke

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
