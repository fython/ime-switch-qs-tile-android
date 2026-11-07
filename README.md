# Input Method Switch Quick Settings Tile

[简体中文](README.zh-CN.md)

An Android app that adds a Quick Settings tile for opening the system input method picker. It uses a short-lived transparent Activity to obtain input focus before requesting the picker. The app does not read typed text or change input method settings directly, and it has no launcher icon.

## Build and install

You need JDK 17 or later, Android SDK Platform 35, and Build Tools 35.0.0. Open the project in Android Studio and let Gradle sync, or run:

```sh
./gradlew assembleDebug
```

The APK is written to `app/build/outputs/apk/debug/app-debug.apk`. Install it on a device running Android 7.0 (API 24) or later.

GitHub Actions also builds an installable debug APK on pushes, pull requests, and manual runs. Download `input-method-switch-debug-apk` from the workflow run’s artifacts. The debug APK uses a development signing key and is intended for testing.

## Use

Expand Quick Settings, tap the edit (pencil) button, and add the input method switch tile. Tap the tile to open the system picker. Enable the input methods you want to use in system settings first. The picker's appearance and availability may vary by Android version, device manufacturer, and lock-screen state.

The app has no launcher entry. To uninstall it, use the system's app settings.

## License

This project is licensed under the [GNU General Public License v3.0](LICENSE).
