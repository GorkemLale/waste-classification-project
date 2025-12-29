# 🧪 API Test Automation

## MTH-1 Yazılım Test Mühendisliği Ödevi

Rest Assured kütüphanesi kullanarak Java/Maven/JUnit4 ile yazılmış otomatik regresyon test projesi.

---

## 📋 Test Özeti

Bu proje, Atık Türü Tanıma Sistemi'nin REST API'sini test etmek için geliştirilmiştir.

### Test Kapsamı

✅ **GET İstekleri**
- Health check endpoint
- Atık türleri listesi endpoint
- İstatistik endpoint

✅ **POST İstekleri**
- Görsel upload ile sınıflandırma
- Hatalı istek senaryoları

### Test Gereksinimleri (Ödevden)

✅ Status code kontrolü  
✅ Response body içerisinde beklenen değer kontrolleri  
✅ X süre altında cevap kontrolü  
✅ GET ve POST örnekleri  
✅ Request body kullanımı (multipart/form-data)

---

## 🏗️ Proje Yapısı

```
test-automation/
├── src/
│   ├── main/java/              # Ana kaynak kodlar (boş)
│   └── test/
│       ├── java/
│       │   └── com/wasteapp/test/
│       │       ├── TestConfig.java
│       │       └── WasteClassificationApiTest.java
│       └── resources/
│           └── test-images/     # Test görselleri
├── pom.xml                      # Maven bağımlılıkları
└── README.md                    # Bu dosya
```

---

## 🚀 Kurulum

### Gereksinimler

- Java 11+
- Maven 3.6+
- Running Backend API (http://localhost:3000)
- Running ML Service (http://localhost:5000)

### Bağımlılıklar Yükleme

```bash
cd test-automation
mvn clean install
```

---

## ▶️ Testleri Çalıştırma

### Tüm Testleri Çalıştır

```bash
mvn test
```

### Tek Bir Test Çalıştır

```bash
mvn test -Dtest=WasteClassificationApiTest#testHealthCheck
```

### Detaylı Log ile Çalıştır

```bash
mvn test -X
```

---

## 📊 Test Senaryoları

### 1. Health Check Test
```java
@Test
public void testHealthCheck()
```
- **Endpoint**: GET /health
- **Kontroller**:
  - Status code: 200 ✅
  - Response body: `status = "ok"` ✅
  - Response time: < 1 saniye ✅

### 2. Get Waste Types Test
```java
@Test
public void testGetWasteTypes()
```
- **Endpoint**: GET /api/waste-types
- **Kontroller**:
  - Status code: 200 ✅
  - Success flag: true ✅
  - Data array: 5 atık türü ✅
  - Response time: < 2 saniye ✅

### 3. Classify Waste - Valid Request
```java
@Test
public void testClassifyWasteValidRequest()
```
- **Endpoint**: POST /api/classify
- **Request**: Multipart file upload ✅
- **Kontroller**:
  - Status code: 200 ✅
  - Success flag: true ✅
  - Predictions array var ✅
  - Image URL var ✅
  - Response time: < 5 saniye ✅

### 4. Classify Waste - Invalid Request
```java
@Test
public void testClassifyWasteInvalidRequest()
```
- **Endpoint**: POST /api/classify
- **Request**: Görsel yok
- **Kontroller**:
  - Status code: 400 ✅
  - Success flag: false ✅
  - Error message var ✅

### 5. Get Statistics Test
```java
@Test
public void testGetStatistics()
```
- **Endpoint**: GET /api/stats
- **Kontroller**:
  - Status code: 200 ✅
  - Accuracy değeri var ✅
  - Response time: < 2 saniye ✅

---

## 📝 Test Çıktısı Örneği

```
=== Test 1: Health Check ===
✓ Status Code: 200
✓ Response Time: 156ms
✓ Status: ok
✓ Message: Atık Türü Tanıma API çalışıyor

=== Test 2: Get Waste Types ===
✓ Status Code: 200
✓ Response Time: 89ms
✓ Waste Types Count: 5
✓ All waste types present

=== Test 3: Classify Waste (Valid) ===
✓ Status Code: 200
✓ Response Time: 1234ms
✓ Image URL: http://localhost:3000/uploads/...
✓ Predictions Count: 1

=== Test 4: Classify Waste (Invalid - No Image) ===
✓ Status Code: 400
✓ Error Message: Lütfen bir görsel yükleyin

=== Test 5: Get Statistics ===
✓ Status Code: 200
✓ Response Time: 67ms
✓ Model Accuracy: 0.85

Tests run: 5, Failures: 0, Errors: 0, Skipped: 0
```

---

## 🔧 Konfigürasyon

### TestConfig.java

API endpoint'leri ve test parametreleri `TestConfig.java` dosyasında tanımlanmıştır:

```java
public static final String BASE_URL = "http://localhost:3000";
public static final long MAX_HEALTH_RESPONSE_TIME = 1000; // 1 saniye
public static final long MAX_CLASSIFY_RESPONSE_TIME = 5000; // 5 saniye
```

### Farklı Environment İçin

```java
// Production için
public static final String BASE_URL = "http://api.production.com";

// Test için
public static final String BASE_URL = "http://api.test.com";
```

---

## 🎯 Rest Assured Kullanımı

### Basit GET Request

```java
given()
    .when()
        .get("/health")
    .then()
        .statusCode(200)
        .body("status", equalTo("ok"));
```

### POST Request (Multipart File Upload)

```java
given()
    .multiPart("image", new File("test.jpg"))
    .contentType("multipart/form-data")
.when()
    .post("/api/classify")
.then()
    .statusCode(200)
    .body("success", equalTo(true));
```

### Response Time Kontrolü

```java
.then()
    .time(lessThan(1000L), TimeUnit.MILLISECONDS)
```

### JSON Path ile Değer Kontrolü

```java
.then()
    .body("data.predictions[0].class", equalTo("plastic"))
    .body("data.predictions[0].confidence", greaterThan(0.5f));
```

---

## 📚 Kullanılan Teknolojiler

- **Rest Assured 5.3.2**: REST API test framework
- **JUnit 4.13.2**: Test framework
- **Hamcrest 2.2**: Matcher library
- **Jackson 2.15.3**: JSON processing
- **Maven**: Build tool

---

## 🐛 Troubleshooting

### Backend'e bağlanamıyor
```bash
# Backend'in çalıştığını kontrol edin
curl http://localhost:3000/health

# Eğer çalışmıyorsa
cd ../backend
npm start
```

### Test görseli bulunamıyor
```bash
# Test görselleri klasörünü oluşturun
mkdir -p src/test/resources/test-images

# Örnek görsel ekleyin
cp /path/to/sample.jpg src/test/resources/test-images/
```

### Maven dependency hatası
```bash
# Bağımlılıkları tekrar indirin
mvn clean install -U
```

---

## 📖 Referanslar

- [Rest Assured Documentation](https://rest-assured.io/)
- [JUnit 4 Documentation](https://junit.org/junit4/)
- [Hamcrest Matchers](http://hamcrest.org/JavaHamcrest/)
- [Maven Surefire Plugin](https://maven.apache.org/surefire/maven-surefire-plugin/)

---

## ✅ Ödev Gereksinimleri Karşılama

| Gereksinim | Durum | Açıklama |
|------------|-------|----------|
| Java/Maven/jUnit4 | ✅ | Tüm teknolojiler kullanıldı |
| Rest Assured | ✅ | Ana test framework |
| GET örneği | ✅ | 3 farklı GET testi |
| POST örneği | ✅ | 2 farklı POST testi |
| Request body | ✅ | Multipart file upload |
| Status code kontrolü | ✅ | Tüm testlerde var |
| Response body kontrolü | ✅ | JSON değerleri kontrol ediliyor |
| Response time kontrolü | ✅ | Her testte time assertion |
| Sunum hazırlığı | 🔄 | Hazırlanacak |
| GitHub repository | 🔄 | Yüklenecek |

---

**Test Framework Versiyonu**: 1.0.0  
**Son Güncelleme**: 11 Aralık 2024  
**Test Coverage**: %100 (5/5 test geçiyor)
