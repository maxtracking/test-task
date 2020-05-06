# Test task

## What I've done there:
* Created simple MVVM-based project with ViewModel dealing with data retrieval + LiveData; RxJava & Koin to illustrate the usage; More or less Clean-ish architecture approach overall
* All the dependencies listed in gradle/libraries.gradle and then used in app/build.gradle
* Organised classes in a way that passes for small projects but they would be grouped per feature or into modules for more complex projects
* For main screen, added list and grid adapters so in theory we can display data both ways
* For details - opted in for the simplest activity with ConstraintLayout but could use CoordinatorLayout for fancier effects OR some fragment with shared data, whatever :)
* For the email sending - I grab a bitmap from the ImageView for simplicity. On API 26 I had to implement FileProvider to share URI to the file
* Added simple Unit test for one use-case

## What I didn't bother to do but could :) :
* Didn't bother with Lifecycle events (or configuration changes for that matter)
* Didn't bother with hardcoded dimens; in general I'd give them meaningful names and put to dimens.xml
* Not much error handling visually, just dumping to logcat; UC has some example of error handling + global handler for Rx errors is set
* Didn't handle configuration changes
* Didn't bother to take care about signing, ProGuard/R8 etc, just made enough to be able to run the app from Android Studio
* Didn't bother to add Espresso tests (or with Kakao)
