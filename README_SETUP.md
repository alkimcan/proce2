# SporDestek Android Uygulaması - Kurulum Rehberi

## 🚀 Hızlı Başlangıç

### 1. Projeyi Android Studio'da Açın

1. Android Studio'yu açın
2. **File > Open** menüsünden bu klasörü seçin
3. Gradle sync otomatik başlayacak (biraz zaman alabilir)

### 2. Gerekli Ayarlar

#### a) Google Fit API Key (Opsiyonel)

Eğer Google Fit entegrasyonu kullanacaksanız:

1. [Google Cloud Console](https://console.cloud.google.com/) açın
2. Yeni proje oluşturun veya mevcut projeyi seçin
3. **APIs & Services > Library** bölümünden **Fitness API** aktif edin
4. **APIs & Services > Credentials** bölümünden **API Key** oluşturun
5. `app/src/main/res/values/strings.xml` dosyasında güncelleyin:

```xml
<string name="google_fit_api_key">YOUR_API_KEY_HERE</string>
```

#### b) Backend URL Kontrolü

`app/src/main/java/com/spordestek/app/data/api/RetrofitClient.kt` dosyasında BASE_URL'i kontrol edin:

```kotlin
private const val BASE_URL = "https://3000-iakf70cmx0yy2acgvtqg4-1c5d5ede.manus-asia.computer/"
```

### 3. Test Kullanıcıları

**Android Test Kullanıcısı:**
- Email: `android@test.com`
- Şifre: `Test1234!@#$`

**Admin Kullanıcısı:**
- Email: `admin@spordestek.com`
- Şifre: `Admin123!@#$`

### 4. APK Oluşturma

#### Debug APK (Test için)

1. **Build > Build Bundle(s) / APK(s) > Build APK(s)**
2. APK dosyası: `app/build/outputs/apk/debug/app-debug.apk`

#### Release APK (Yayın için)

1. **Build > Generate Signed Bundle / APK**
2. **APK** seçin
3. Keystore oluşturun veya mevcut keystore'u seçin
4. Release build type seçin
5. APK dosyası: `app/build/outputs/apk/release/app-release.apk`

## 📱 Emulator vs Fiziksel Cihaz

### Emulator Kullanımı

**Avantajlar:**
- Hızlı test
- Farklı cihaz boyutları test edilebilir

**Dezavantajlar:**
- Internet bağlantısı sorunları olabilir
- Google Fit çalışmayabilir (gerçek adım verisi yok)

**Emulator DNS Sorunu Çözümü:**

1. **Tools > Device Manager**
2. Emulator'ı seçin, **Edit**
3. **Show Advanced Settings**
4. **Network > DNS Server:** `8.8.8.8, 8.8.4.4`

### Fiziksel Cihaz Kullanımı (Önerilen)

1. Telefonda **Geliştirici Seçenekleri** açın
2. **USB Debugging** aktif edin
3. USB ile bilgisayara bağlayın
4. Android Studio'da cihazı seçin
5. Run

## 🔧 Sorun Giderme

### "UnknownHostException" Hatası

**Sebep:** Internet bağlantısı veya DNS sorunu

**Çözüm:**
1. Emulator'da WiFi açık olduğundan emin olun
2. DNS ayarlarını kontrol edin (8.8.8.8)
3. Fiziksel cihaz kullanmayı deneyin

### "Email veya şifre hatalı" Hatası

**Sebep:** Backend'e ulaşılamıyor veya yanlış credentials

**Çözüm:**
1. BASE_URL doğru mu kontrol edin
2. Test kullanıcı bilgilerini kullanın
3. Logcat'te network isteklerini kontrol edin

### Google Fit İzinleri

**Sebep:** Uygulama Google Fit'e erişemiyor

**Çözüm:**
1. Telefon ayarlarından **Apps > SporDestek > Permissions**
2. **Physical activity** iznini verin
3. Google Fit uygulamasının yüklü olduğundan emin olun

## 📂 Proje Yapısı

```
app/src/main/java/com/spordestek/app/
├── data/
│   ├── api/
│   │   ├── ApiService.kt          # API endpoint tanımları
│   │   └── RetrofitClient.kt      # Retrofit client
│   ├── auth/
│   │   ├── AuthInterceptor.kt     # JWT token interceptor
│   │   └── TokenManager.kt        # Session yönetimi
│   ├── fitness/
│   │   └── GoogleFitManager.kt    # Google Fit entegrasyonu
│   └── models/
│       └── Models.kt              # Data modelleri
├── ui/
│   ├── navigation/
│   │   └── Navigation.kt          # Navigasyon yapısı
│   ├── screens/
│   │   ├── HomeScreen.kt          # Ana sayfa
│   │   ├── ClubsScreen.kt         # Kulüpler
│   │   ├── StepsScreen.kt         # Adımlar
│   │   ├── ProfileScreen.kt       # Profil
│   │   ├── LeaderboardScreen.kt   # Liderlik tablosu
│   │   └── RegisterScreen.kt      # Kayıt
│   └── theme/
│       ├── Theme.kt               # Tema ayarları
│       └── Type.kt                # Tipografi
├── viewmodels/
│   ├── LoginViewModel.kt          # Login logic
│   ├── RegisterViewModel.kt       # Register logic
│   ├── SessionViewModel.kt        # Session yönetimi
│   └── ClubsViewModel.kt          # Kulüp işlemleri
└── MainActivity.kt                # Ana activity
```

## 🎯 Özellikler

✅ **Kullanıcı Yönetimi**
- Email/şifre ile kayıt
- Login/Logout
- Session yönetimi (JWT)

✅ **Adım Takibi**
- Google Fit entegrasyonu
- Günlük adım sayısı
- Adım geçmişi
- Adımları puana çevirme (1000 adım = 1 puan)

✅ **Kulüp İşlemleri**
- Kulüp listesi
- Kulüp detayları
- Kulüplere bağış yapma

✅ **Liderlik Tablosu**
- En çok puan kazanan kullanıcılar
- En çok destek alan kulüpler

✅ **Profil**
- Kullanıcı bilgileri
- Toplam puan
- Bağış geçmişi

## 🔒 Güvenlik

- JWT token ile authentication
- HTTPS bağlantı
- Network Security Config
- Token otomatik yenileme

## 📞 Destek

Sorun yaşarsanız:

1. Logcat'i kontrol edin (Android Studio > Logcat)
2. Network isteklerini inceleyin (OkHttp logging)
3. Backend sunucusunun çalıştığından emin olun

Backend URL: https://3000-iakf70cmx0yy2acgvtqg4-1c5d5ede.manus-asia.computer/

---

**Hazırlayan:** Manus AI
**Tarih:** 19 Kasım 2025
**Versiyon:** 1.0
