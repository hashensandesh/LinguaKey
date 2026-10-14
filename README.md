# LinguaKey Android starter
This starter includes a native Android input method (IME), Translate Mode toggle, language picker, paste-to-translate UI, and a few sample phrase matches.

Important: this is an early prototype, not a complete translator. Arbitrary text translation and 100+ languages are NOT implemented until a real translation service is connected. Sample phrase matching is only for testing the keyboard workflow.

Build requirements: Android SDK, JDK 17, Gradle 8.9+ compatible with Android Gradle Plugin 8.7.3. Build with `gradle assembleDebug`; APK output is `app/build/outputs/apk/debug/app-debug.apk`.

Phone-only path: use a browser-based cloud coding/build environment that supports Android SDK and Gradle, or a trusted Android cloud-build workflow. Download the APK to your phone, install it, open LinguaKey, enable it in Android keyboard settings, then choose it from the keyboard picker.

Next: connect a translation provider through a backend (never hardcode secret API keys in the APK), implement debounced live suggestions, improve Singlish transliteration, test privacy/security, then build a separate iOS Custom Keyboard Extension in Xcode.
