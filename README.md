# Mobile Test Automation — Sauce Labs My Demo App

Android UI test suite for the [Sauce Labs My Demo App](https://github.com/saucelabs/my-demo-app-android), built with
Java, Maven, Appium (`java-client`) and TestNG, using the Page Object Model with Appium's `PageFactory`.

## Stack

- Java 17, Maven
- Appium `java-client` 9.x + UiAutomator2 driver
- TestNG
- Allure for HTML test reports

## Project layout

```
apps/                        APK under test (mda-2.3.0-27.apk)
src/main/java/mobiletests/
  config/                     Reads config.properties, allows -D overrides
  driver/                     Appium session lifecycle (DriverManager)
  pages/                      Page objects (PageFactory + @AndroidFindBy)
src/test/java/mobiletests/
  base/                       BaseTest: session setup/teardown, failure screenshots
  tests/positive/             Happy-path TestNG test classes
  tests/negative/             Negative/error-path TestNG test classes
src/test/resources/
  testng.xml                  Suite definition (separate <test> blocks per group)
  allure.properties           Allure results directory
```

## Prerequisites

- JDK 17+
- Maven 3.8+
- Android SDK with an emulator image (or a real device) — `ANDROID_HOME`/`ANDROID_SDK_ROOT` must be exported
- Node.js + Appium 2.x, with the UiAutomator2 driver installed:
  ```bash
  npm install -g appium
  appium driver install uiautomator2
  ```
- (Optional, for a nicer local report) [Allure commandline](https://allurereport.org/docs/install/) — the Maven
  plugin can also generate the report without it.

The APK is already checked into `apps/mda-2.3.0-27.apk`. To use a different build, download it from the
[releases page](https://github.com/saucelabs/my-demo-app-android/releases) and update `appPath` in
`src/main/resources/config.properties`.

## Setup

```bash
# start an emulator (name must match config.properties -> deviceName)
emulator -avd Pixel_10

# in another terminal, start the Appium server
export ANDROID_HOME=~/Library/Android/sdk
export ANDROID_SDK_ROOT=$ANDROID_HOME
appium
```

`config.properties` assumes `deviceName=Pixel_10` and the default Appium URL `http://127.0.0.1:4723/`. Override any
value at runtime without editing the file, e.g.:

```bash
mvn test -DdeviceName=Pixel_7 -DappiumServerUrl=http://127.0.0.1:4723/
```

## Running the tests

```bash
mvn clean test
```

This installs the app fresh on the emulator, runs the suite defined in `src/test/resources/testng.xml`, and writes
raw Allure results to `target/allure-results` on every run.

## Test report

Generate and open the HTML report after a run:

```bash
mvn allure:serve      # builds the report and opens it in a browser
# or, without a live server:
mvn allure:report      # writes target/site/allure-maven-plugin/index.html
```

A screenshot is attached automatically to any failing test.

## What's covered

- **Login** — valid credentials reach the catalog and flip the drawer menu to "Log Out"; the locked-out demo account
  is rejected with its inline error message.
- **Logout** — confirming the native logout prompt returns the user to the Login screen.
- **Navigation** — tapping a product opens its details screen; the header cart icon opens the cart.
- **Cart** — adding a product updates the header badge and cart contents; quantity changes carry through; removing
  the only item empties the cart.
- **Checkout validation** — submitting the shipping form with a required field (City / Full Name) blanked out is
  rejected with the app's own inline error message, instead of advancing to payment.

## App quirks the tests account for

- On this emulator image, the app shows a one-time "Android App Compatibility" system dialog on cold start
  (unrelated to the app itself); `BaseTest` dismisses it before each test.
- Checkout requires an authenticated session — reaching the shipping form is only possible after logging in.
- Logging out shows a native confirmation dialog ("Are you sure you want to logout") before the session actually
  ends.
- Tapping a product's title text does nothing; only the product image is wired up for navigation.
