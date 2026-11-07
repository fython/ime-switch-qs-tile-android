# 输入法切换快捷设置磁贴

[English](README.md)

这是一个用于打开系统输入法选择器的 Android 快捷设置磁贴。磁贴通过短暂的透明 Activity 获取输入焦点，再请求系统显示选择器。应用不会读取输入内容，也不会直接修改输入法设置，且没有桌面启动图标。

## 构建和安装

需要 JDK 17 或更高版本、Android SDK Platform 35 和 Build Tools 35.0.0。使用 Android Studio 打开项目并等待 Gradle 同步，或运行：

```sh
./gradlew assembleDebug
```

APK 位于 `app/build/outputs/apk/debug/app-debug.apk`。将其安装到 Android 7.0（API 24）或更高版本的设备。

GitHub Actions 也会在推送、拉取请求和手动运行时构建可安装的调试版 APK。可从工作流运行页面的构建产物中下载 `input-method-switch-debug-apk`。调试版使用开发签名密钥，仅供测试。

## 使用

展开快捷设置，点击编辑（铅笔）按钮，添加“切换输入法”磁贴。点击磁贴即可打开系统选择器。请先在系统设置中启用需要使用的输入法。选择器的显示形式和可用性可能因 Android 版本、设备厂商及锁屏状态而异。

应用没有桌面启动入口；如需卸载，请使用系统的应用设置。

## 许可证

本项目采用 [GNU 通用公共许可证 v3.0](LICENSE)。
