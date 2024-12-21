# Translization KMP Library

As Android and iOS developers, we often face the challenge of displaying dynamic text in TextView elements, especially when the text length can vary. The problem intensifies when working with multilingual content, where translation and text rendering need to be handled efficiently at runtime. How do you ensure that text fits properly on a TextView while considering different languages, including right-to-left languages like Arabic?

To address this issue, I’ve developed a Kotlin Multiplatform (KMP) library that not only checks the rendering of text on TextViews but also translates text between English and Arabic in real-time. By leveraging the Helsinki-NLP/opus-mt-ar-en and Helsinki-NLP/opus-mt-en-ar models, this library provides seamless translation between Arabic and English, ensuring that text displays correctly and is contextually accurate.


AI Model Used:
The core of this library is built around an HuggingFace AI-powered model designed to predict text rendering behavior on TextViews. For this purpose, I’m using a lightweight machine learning HuggingFace model trained on large datasets.

The heart of this library combines two powerful AI models:

Helsinki-NLP/opus-mt-ar-en: A pre-trained translation model that translates Arabic text to English.
Helsinki-NLP/opus-mt-en-ar: A pre-trained translation model that translates English text to Arabic.
These models, provided by Helsinki-NLP, are designed for efficient translation and are integrated into the KMP library to handle text translation at runtime, making it easier to work with multilingual content. Additionally, the library uses a lightweight text prediction AI model to estimate how text will be rendered on TextViews, factoring in text length, styling, and screen size.


#Currently on Android
![libraryImp](https://github.com/user-attachments/assets/09c77448-99ce-4264-bf73-0d7132401d6b)

#Sample Integration
Code Usage

   fun translateStrings(inputString: String) {
        lifecycleScope.launch {

            HuggingFaceTranslator(HttpClient()).translateToArabic(inputString).let {
                response-> "Response : $response".also { binding.translatedText.text = it }
            }
        }
    }


Gradle Imports

implementation("io.github.shiraz990:shared-android:1.1.0")

or

libs.version.toml
[versions]
sharedAndroid = "1.1.0"
[libraries]
shared-android = { module = "io.github.shiraz990:shared-android", version.ref = "sharedAndroid" }
build.gradle.kts
implementation(libs.shared.android)






