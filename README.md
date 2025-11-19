# SporDestek Android Uygulaması

**Amatör Spor Kulüplerini Destekleme Platformu - Native Android (Kotlin)**

---

## 📱 Proje Hakkında

SporDestek, kullanıcıların günlük attıkları adımları puana çevirerek amatör spor kulüplerine bağış yapmalarını sağlayan bir mobil uygulamadır. Bu proje, HelpSteps benzeri bir sistem olarak tasarlanmıştır ve Native Android (Kotlin) ile geliştirilmiştir.

**Temel Özellikler:**
- ✅ Google Fit entegrasyonu ile otomatik adım sayımı
- ✅ Manuel adım girişi
- ✅ Adımları puana çevirme (1000 adım = 1 puan)
- ✅ Kulüplere bağış yapma
- ✅ Liderlik tablosu
- ✅ Kullanıcı profili ve bağış geçmişi
- ✅ Material Design 3 UI

---

## 🛠️ Teknoloji Stack

- **Dil:** Kotlin
- **UI Framework:** Jetpack Compose
- **Minimum SDK:** 24 (Android 7.0)
- **Target SDK:** 34 (Android 14)
- **Networking:** Retrofit 2 + OkHttp 3
- **Image Loading:** Coil
- **Google Fit API:** Play Services Fitness 21.1.0
- **Architecture:** MVVM (hazır değil, TODO olarak bırakıldı)

---

## 📋 Gereksinimler

### Yazılım Gereksinimleri
- **Android Studio:** Hedgehog (2023.1.1) veya daha yeni
- **JDK:** 17 veya daha yeni
- **Gradle:** 8.2 (otomatik indirilir)

### API Anahtarları
1. **Google Fit API Key** (Zorunlu)
   - [Google Cloud Console](https://console.cloud.google.com/) üzerinden proje oluşturun
   - Fitness API'yi etkinleştirin
   - OAuth 2.0 Client ID oluşturun (Android tipi)
   - SHA-1 fingerprint'inizi ekleyin

2. **Backend API URL** (Mevcut)
   - Varsayılan: `https://3000-iakf70cmx0yy2acgvtqg4-1c5d5ede.manus-asia.computer`
   - `app/src/main/res/values/strings.xml` dosyasından değiştirilebilir

---

## 🚀 Kurulum Adımları

### 1. Projeyi Android Studio'da Açın

```bash
# Projeyi klonlayın veya ZIP'ten çıkartın
cd spordestek-android

# Android Studio'da açın
# File > Open > spordestek-android klasörünü seçin
```

### 2. Google Fit API Key Ekleyin

`app/src/main/res/values/strings.xml` dosyasını açın ve Google Fit API key'inizi ekleyin:

```xml
<string name="google_fit_api_key">YOUR_GOOGLE_FIT_API_KEY</string>
```

### 3. SHA-1 Fingerprint Alın

Android Studio'da Terminal'i açın ve şu komutu çalıştırın:

```bash
# Debug key için
./gradlew signingReport

# Veya manuel olarak
keytool -list -v -keystore ~/.android/debug.keystore -alias androiddebugkey -storepass android -keypass android
```

SHA-1 fingerprint'i kopyalayın ve Google Cloud Console'da OAuth Client ID'nize ekleyin.

### 4. Gradle Sync

Android Studio'da:
- **File > Sync Project with Gradle Files**

### 5. Uygulamayı Çalıştırın

- Fiziksel bir Android cihaz veya emulator bağlayın
- **Run > Run 'app'** veya Shift+F10

---

## 📦 APK Oluşturma

### Debug APK

```bash
./gradlew assembleDebug
```

APK dosyası: `app/build/outputs/apk/debug/app-debug.apk`

### Release APK (İmzalı)

1. **Keystore Oluşturun:**

```bash
keytool -genkey -v -keystore spordestek-release-key.jks -keyalg RSA -keysize 2048 -validity 10000 -alias spordestek
```

2. **app/build.gradle.kts** dosyasına signing config ekleyin:

```kotlin
android {
    signingConfigs {
        create("release") {
            storeFile = file("../spordestek-release-key.jks")
            storePassword = "YOUR_STORE_PASSWORD"
            keyAlias = "spordestek"
            keyPassword = "YOUR_KEY_PASSWORD"
        }
    }
    
    buildTypes {
        release {
            signingConfig = signingConfigs.getByName("release")
            // ...
        }
    }
}
```

3. **Release APK Oluşturun:**

```bash
./gradlew assembleRelease
```

APK dosyası: `app/build/outputs/apk/release/app-release.apk`

---

## 🔧 Yapılandırma

### Backend API URL Değiştirme

`app/src/main/res/values/strings.xml`:

```xml
<string name="api_base_url">https://your-backend-url.com</string>
```

### Uygulama Adı ve Logosu

- **Uygulama Adı:** `app/src/main/res/values/strings.xml` > `app_name`
- **Uygulama İkonu:** `app/src/main/res/mipmap-*/ic_launcher.png` dosyalarını değiştirin

---

## 📱 Google Fit İzinleri

Uygulama ilk çalıştırıldığında, Google Fit izinleri isteyecektir:

1. **Activity Recognition** - Adım sayımı için
2. **Fitness API** - Google Fit verilerine erişim için

Kullanıcı bu izinleri vermezse, manuel adım girişi kullanılabilir.

---

## 🐛 Bilinen Sorunlar ve TODO'lar

### Tamamlanmamış Özellikler

- [ ] **ViewModels ve Repository Pattern** - Şu anda UI ekranları statik veri gösteriyor
- [ ] **API Entegrasyonu** - Retrofit servisleri hazır ama ekranlara bağlanmamış
- [ ] **Google Fit Senkronizasyonu** - GoogleFitManager hazır ama UI'a entegre edilmemiş
- [ ] **Kullanıcı Kimlik Doğrulama** - Login/logout fonksiyonları eksik
- [ ] **Veri Persistence** - DataStore veya Room Database entegrasyonu yok
- [ ] **Hata Yönetimi** - Try-catch blokları ve kullanıcı bildirimleri eksik
- [ ] **Loading States** - API çağrıları sırasında loading göstergeleri eksik
- [ ] **Image Upload** - Kulüp logosu yükleme fonksiyonu eksik

### Önerilen İyileştirmeler

1. **MVVM Architecture Implementasyonu**
   - ViewModel'ler oluşturun
   - Repository pattern ekleyin
   - UseCase'ler tanımlayın

2. **State Management**
   - StateFlow veya LiveData kullanın
   - UI state'leri yönetin

3. **Offline Support**
   - Room Database ekleyin
   - Offline-first yaklaşımı uygulayın

4. **Testing**
   - Unit testler yazın
   - UI testleri ekleyin

---

## 📚 Proje Yapısı

```
spordestek-android/
├── app/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/spordestek/app/
│   │   │   │   ├── data/
│   │   │   │   │   ├── api/          # Retrofit servisleri
│   │   │   │   │   ├── fitness/      # Google Fit Manager
│   │   │   │   │   └── models/       # Data modelleri
│   │   │   │   ├── ui/
│   │   │   │   │   ├── navigation/   # Navigasyon yapısı
│   │   │   │   │   ├── screens/      # Compose ekranları
│   │   │   │   │   └── theme/        # Material Theme
│   │   │   │   └── MainActivity.kt
│   │   │   ├── res/
│   │   │   │   ├── values/           # strings, colors, themes
│   │   │   │   └── xml/              # backup rules
│   │   │   └── AndroidManifest.xml
│   │   └── build.gradle.kts
│   └── proguard-rules.pro
├── gradle/
│   └── wrapper/
├── build.gradle.kts
├── settings.gradle.kts
└── README.md
```

---

## 🤝 Katkıda Bulunma

Bu proje, Manus AI tarafından oluşturulmuş bir başlangıç şablonudur. Aşağıdaki adımları tamamlayarak projeyi geliştirebilirsiniz:

1. ViewModels ve Repository pattern ekleyin
2. API çağrılarını ekranlara bağlayın
3. Google Fit senkronizasyonunu aktif hale getirin
4. Hata yönetimi ve loading states ekleyin
5. Unit ve UI testleri yazın

---

## 📄 Lisans

Bu proje, SporDestek platformunun bir parçasıdır ve özel kullanım içindir.

---

## 📞 Destek

Sorularınız için:
- **Backend API:** Mevcut web sitesi ile aynı backend kullanılmaktadır
- **Google Fit:** [Google Fit API Dokümantasyonu](https://developers.google.com/fit)
- **Jetpack Compose:** [Android Developers](https://developer.android.com/jetpack/compose)

---

**Not:** Bu uygulama, temel yapı ve UI ekranları ile birlikte teslim edilmiştir. Tam fonksiyonellik için yukarıdaki TODO'ların tamamlanması gerekmektedir.
