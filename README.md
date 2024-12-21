# Translization
To address this common problem, I've created a Kotlin Multiplatform (KMP) library that leverages AI to predict how text will be rendered on TextViews at runtime. This library helps you check your UI in real-time, without the need to test on multiple devices. By using this library, developers can ensure their app's Arabic/English content looks great, no matter the device or screen size.


#Currently on Android

#Sample Integration

libs.version.toml

[versions]
sharedAndroid = "1.0.9"

[libraries]
shared-android = { module = "io.github.shiraz990:shared-android", version.ref = "sharedAndroid" }

build.gradle.kts
implementation(libs.shared.android)

or

implementation("io.github.shiraz990:shared-android:1.0.9")

Code Usage

   fun translateStrings(inputString: String) {
        lifecycleScope.launch {

            HuggingFaceTranslator(HttpClient()).translateToArabic(inputString).let {
                response-> "Response : $response".also { binding.translatedText.text = it }
            }
        }
    }



