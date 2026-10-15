# Ucchash I&SC Billing System Automation (Android starter project)

This Android Studio project wraps the existing website:
https://ictisc-ivoice.netlify.app/

## Features included
- Android WebView wrapper
- JavaScript and DOM storage enabled for the existing web app
- Back navigation
- External links open outside the app (Netlify subdomains remain in-app)
- DownloadManager integration for website downloads (PDF/Word/Excel, depending on what the website serves)
- Basic offline retry screen
- ICTISC + Ucchash-inspired placeholder splash/logo vector

## Build APK / AAB
1. Install Android Studio (current stable) and a compatible JDK.
2. Open this folder in Android Studio.
3. Allow Gradle sync; install Android SDK 35 if prompted.
4. For a test APK: Build > Build Bundle(s) / APK(s) > Build APK(s).
5. For Play Store: Build > Generate Signed Bundle / APK > Android App Bundle (AAB). Create and safely store your signing key.

## Before Play Store release
- Replace the placeholder vector with the approved official ICTISC + Ucchash logo and proper launcher icon assets.
- Verify the site's privacy policy and add its URL in Play Console.
- Test invoice generation, file downloads, PDF viewing/printing and all web flows on physical devices.
- Complete Play Console Data safety, app access, content rating and store listing declarations honestly.
- Google Play approval is not guaranteed; a basic WebView wrapper may not meet minimum functionality requirements. Consider a TWA/PWA or more app-specific value if required.
- This starter is not a signed APK/AAB; those must be built and signed locally in Android Studio.

## Build using only an Android phone
See `PHONE_ONLY_BUILD_GUIDE_BN.md`. A GitHub Actions workflow is included at `.github/workflows/build-android.yml` to build a debug APK and an unsigned release AAB in the cloud.
