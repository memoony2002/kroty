# 🧭 كشاف (Kashaf)

تطبيق أندرويد لتسجيل واستكشاف **الاكتشافات** — أي مكان، فكرة، كتاب أو عنصر مميز
تم اكتشافه في رحلتك. يعرض التطبيق قائمة الاكتشافات، ويدعم البحث الفوري،
وإضافة اكتشاف جديد مع تفاصيله (الفئة، الموقع، التاريخ، الوصف)، وعرض التفاصيل
والحذف، مع حفظ البيانات محلياً على الجهاز.

## ✨ الميزات

- قائمة الاكتشافات مع بطاقات (Material 3) ودعم الوضع الليلي
- بحث فوري لفلترة العناوين، الفئات، الوصف والموقع
- إضافة اكتشافات جديدة عبر شاشة مخصصة مع اختيار الفئة من قائمة موحّدة
- شاشة تفاصيل لكل اكتشاف مع حذف
- حفظ البيانات محلياً (SharedPreferences بصيغة JSON)
- واجهة عربية بالكامل

## 🛠 بنية المشروع

```
kroty/
├── .github/workflows/android-build.yml   # سير عمل GitHub Actions لبناء APK
├── app/                                 # وحدة التطبيق
│   ├── src/main/java/com/kroty/kashaf/
│   │   ├── MainActivity.kt              # الشاشة الرئيسية (القائمة والبحث)
│   │   ├── DetailActivity.kt            # شاشة تفاصيل الاكتشاف
│   │   ├── AddActivity.kt               # شاشة إضافة اكتشاف جديد
│   │   ├── data/                        # model + store (SharedPreferences)
│   │   └── ui/                          # ViewModel + RecyclerView adapter
│   └── src/main/res/                    # واجهات، سمات، أيقونات، نصوص عربية
├── gradle/                              # Wrapper + version catalog
├── build.gradle.kts                     # ملفات Gradle (Kotlin DSL)
└── settings.gradle.kts
```

## 📋 المتطلبات

- JDK 17
- Android SDK (compileSdk 35، minSdk 24)
- Gradle Wrapper (مرفق) — لا حاجة لتثبيت Gradle مسبقاً

## 🚀 بناء التطبيق

باستخدام Gradle Wrapper:

```bash
./gradlew :app:assembleDebug
```

سيكون الناتج في: `app/build/outputs/apk/debug/app-debug.apk`

أو افتح المشروع مباشرةً في **Android Studio** (Ladybug أو أحدث) واضغط Run.

## 🤖 سير العمل التلقائي (GitHub Actions)

يتحقق سير العمل `Android Build` (`.github/workflows/android-build.yml`) من كل إيداع
ويقوم بـ:

1. إعداد JDK 17 و Android SDK
2. بناء APK الـ Debug (`./gradlew :app:assembleDebug`)
3. تشغيل الفحص البرمجي Lint (`./gradlew :app:lintDebug`)
4. رفع APK كartifact

يُشغَّل تلقائياً على كل `push` إلى `main` أو فروع `arena/**`، ويمكن تشغيله يدوياً
من علامة تبويب **Actions** → **Android Build** → **Run workflow**.

## 📦 المواصفات

| العنصر | القيمة |
|---|---|
| package | `com.kroty.kashaf` |
| الإصدار | 1.0.0 |
| minSdk / targetSdk | 24 / 35 |
| اللغة | Kotlin، واجهة عربية |
| واجهة المستخدم | Material 3 |
