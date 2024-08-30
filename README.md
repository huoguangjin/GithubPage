# GithubPage [WIP]

This is a simple GitHub client APP developed with Jetpack Compose.

## Build and Run

**Requirements**
- Install Android Studio
- Install Android SDK (can be installed via Android Studio)
- Install **JDK 17**, set JAVA_HOME environment variable, or set `Build, Execution, Deployment | Build Tools | Gradle | Gradle JDK` in Android Studio settings.

**Clone this repository**
```shell
git clone https://github.com/huoguangjin/GithubPage.git
cd GithubPage
```

**Build in Android Studio**
- Open Android Studio.
- From the menu, select File > Open, then choose the project directory.
- Wait for Android Studio to sync Gradle configurations and download dependencies.
- Build the project using the menu option Build > Build Bundle(s) / APK(s).

**Build in Command Line**
- Run the following command to compile and build:
```shell
./gradlew build
```

To build an APK, use:
```shell
./gradlew assembleDebug
```

To build a release APK, use:
```shell
./gradlew assembleRelease
```

The final build artifacts can be found in `app/build/outputs/apk`. Install them using adb:
```shell
adb install app/build/outputs/apk/debug/app-debug.apk
```
