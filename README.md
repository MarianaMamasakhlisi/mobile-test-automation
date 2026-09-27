# Mobile Test Automation: Sauce Labs My Demo App

Android UI test suite for the [Sauce Labs My Demo App](https://github.com/saucelabs/my-demo-app-android), built with
Java, Maven, Appium (`java-client`) and TestNG, using the Page Object Model with Appium's `PageFactory`.

## Stack

- Java 17, Maven
- Appium `java-client` 9.x + UiAutomator2 driver
- TestNG, with a one-retry policy for transient emulator/session flakiness and `smoke`/full test groups
- Allure for HTML test reports
- GitHub Actions CI on every push and pull request

## Project layout

```
apps/                        APK under test (mda-2.3.0-27.apk)
src/main/java/mobiletests/
  config/                     Reads config.properties, allows -D overrides
  driver/                     Appium session lifecycle (DriverManager)
  pages/                      Page objects (PageFactory + @AndroidFindBy)
src/test/java/mobiletests/
  base/                       BaseTest: session setup/teardown, failure screenshots
  support/                    RetryAnalyzer + RetryTransformer (auto-retry on transient failures)
  tests/positive/             Happy-path TestNG test classes
  tests/negative/             Negative/error-path TestNG test classes
src/test/resources/
  testng.xml                  Suite definition (separate <test> blocks per group, retry listener)
  allure.properties           Allure results directory
.github/workflows/ci.yml      Runs the suite on every push and pull request
```

## Prerequisites

- JDK 17+
- Maven 3.8+
- Android SDK with an emulator image (or a real device). `ANDROID_HOME`/`ANDROID_SDK_ROOT` must be exported.
- Node.js + Appium 2.x, with the UiAutomator2 driver installed:
  ```bash
  npm install -g appium
  appium driver install uiautomator2
  ```
- (Optional, for a nicer local report) [Allure commandline](https://allurereport.org/docs/install/). The Maven
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

### Running a subset

The core scenarios (one per feature area) are tagged with the TestNG group `smoke`. Run just those:

```bash
mvn test -Dgroups=smoke
```

Omit `-Dgroups` to run the full suite, as `mvn clean test` does above.

### Reliability

A cold emulator or a UiAutomator2 hiccup can fail a test for reasons that have nothing to do with the app under
test. `RetryTransformer` (registered as a TestNG listener in `testng.xml`) applies a one-retry policy to every
test automatically, so a single transient failure doesn't fail the build; a test that fails twice in a row still
does.

## Continuous integration

`.github/workflows/ci.yml` boots an Android emulator on a GitHub-hosted runner (via
[reactivecircus/android-emulator-runner](https://github.com/ReactiveCircus/android-emulator-runner)) and runs the
full suite on every push and pull request against `main`, uploading the Allure results as a build artifact.
Emulator-based CI is inherently slower and more failure-prone than a local run against a warm emulator, so treat a
red run there as a first signal to check, not as equivalent to a local failure.

## Test report

Generate and open the HTML report after a run:

```bash
mvn allure:serve      # builds the report and opens it in a browser
# or, without a live server:
mvn allure:report      # writes target/site/allure-maven-plugin/index.html
```

A screenshot is attached automatically to any failing test.

## What's covered

**Authentication**
- Valid login (standard account and the visual-testing demo account) reaches the catalog and flips the drawer menu
  to "Log Out".
- Logout, both confirmed and cancelled from the native prompt.
- Locked-out account, empty login form, and username with no password: each rejected with its own inline message
  or by simply not proceeding.

**Navigation**
- Catalog to product details, the header cart icon, and back navigation to the catalog.
- Catalog sorting by name and by price.

**Cart and checkout**
- Adding a product, increasing quantity, and removing the only item.
- Re-adding the same product in a different color updates the existing cart line's quantity rather than adding a
  second one.
- The quantity selector clamps at 0, and Add to Cart disables itself there instead of silently doing nothing.
- All five required shipping fields (Full Name, Address Line 1, City, Zip Code, Country), tested one at a time,
  block submission with the app's own inline error message when left empty.
- A full checkout, from the shipping form through payment and order review to a placed order, after which the cart
  is confirmed empty.
- Resetting app state from the drawer menu clears the cart.

## App quirks the tests account for

- On this emulator image, the app shows a one-time "Android App Compatibility" system dialog on cold start. It's
  unrelated to the app itself, and `BaseTest` dismisses it before each test.
- Checkout requires an authenticated session; reaching the shipping form is only possible after logging in.
- Logging out shows a native confirmation dialog ("Are you sure you want to logout") before the session actually
  ends.
- Tapping a product's title text does nothing. Only the product image is wired up for navigation.
- The checkout and payment forms arrive pre-filled with valid sample data, but that data isn't recognised by the
  form's own validation until the field is actually edited. Submitting the defaults as-is fails on every field at
  once; the tests retype each field before submitting.
- The Country field's validation message is an unfinished string in the app itself. It never actually says
  "country". The test asserts the real (if incomplete) message.
