# ♻️ Atık Türü Tanıma Sistemi

**BIM 423 Makine Görmesi Projesi** + **MTH-1 Yazılım Test Mühendisliği Ödevi**

İstanbul Sabahattin Zaim Üniversitesi  
Bilgisayar Mühendisliği

---

## 📋 Proje Hakkında

Bu proje, kampüs içerisinde karşılaşılan atıkların görsellerini analiz ederek, hangi geri dönüşüm kutusuna (kâğıt, cam, metal, organik, plastik) atılması gerektiğini belirleyen bir mobil uygulama ve API servisidir.

### Özellikler

- 📱 **React Native Mobil Uygulama**: Kamera ve galeri ile görsel yükleme
- 🧠 **AI/ML Sınıflandırma**: YOLOv8 tabanlı nesne tespiti
- 🔄 **RESTful API**: Express.js backend servisi
- 🐍 **Python ML Servisi**: Flask tabanlı model inference
- ✅ **Otomatik Test**: Rest Assured ile API testleri
- 🎯 **5 Atık Sınıfı**: Kağıt, Cam, Metal, Organik, Plastik

---

## 🏗️ Proje Mimarisi

```
┌─────────────────────┐
│  React Native App   │  (Mobil Uygulama)
│   (Expo/Android)    │
└──────────┬──────────┘
           │ HTTP/REST
           ↓
┌─────────────────────┐
│   Express.js API    │  (Backend - Port 3000)
│  (Dosya Yönetimi)   │
└──────────┬──────────┘
           │ HTTP
           ↓
┌─────────────────────┐
│   Python/Flask ML   │  (ML Service - Port 5000)
│    (YOLOv8 Model)   │
└─────────────────────┘
           ↑
           │ Test
┌─────────────────────┐
│   Rest Assured      │  (API Test Automation)
│    (JUnit 4)        │
└─────────────────────┘
```

---

## 📁 Proje Yapısı

```
waste-classification-project/
├── mobile-app/              # React Native uygulaması (Expo)
│   ├── src/
│   │   ├── screens/        # Ekran bileşenleri
│   │   ├── components/     # Yeniden kullanılabilir bileşenler
│   │   ├── services/       # API servisleri
│   │   └── constants/      # Sabitler
│   ├── App.js              # Ana uygulama dosyası
│   └── package.json
│
├── backend/                 # Express.js API servisi
│   ├── server.js           # Ana server dosyası
│   ├── uploads/            # Yüklenen görseller
│   ├── .env                # Çevre değişkenleri
│   └── package.json
│
├── ml-service/              # Python/Flask ML servisi
│   ├── app.py              # Ana Flask uygulaması
│   ├── models/             # Eğitilmiş modeller
│   ├── requirements.txt    # Python bağımlılıkları
│   └── train.py            # Model eğitim scripti
│
├── test-automation/         # Rest Assured testleri
│   ├── src/test/java/      # Test sınıfları
│   ├── pom.xml             # Maven konfigürasyonu
│   └── README.md
│
├── dataset/                 # Veri seti (kampüs fotoğrafları)
│   ├── paper/              # Kağıt atıklar (300+)
│   ├── glass/              # Cam atıklar (300+)
│   ├── metal/              # Metal atıklar (300+)
│   ├── organic/            # Organik atıklar (300+)
│   └── plastic/            # Plastik atıklar (300+)
│
└── README.md               # Bu dosya
```

---

## 🚀 Kurulum ve Çalıştırma

### Gereksinimler

- Node.js 16+ ve npm
- Python 3.8+
- Java 11+ ve Maven (testler için)
- Android Studio veya Expo Go uygulaması
- Git

### 1️⃣ Backend Servisi (Express.js)

```bash
cd backend
npm install
npm start
```

Backend: `http://localhost:3000`

### 2️⃣ ML Servisi (Python/Flask)

```bash
cd ml-service
pip install -r requirements.txt --break-system-packages
python app.py
```

ML Service: `http://localhost:5000`

### 3️⃣ Mobil Uygulama (React Native/Expo)

```bash
cd mobile-app

# API Base URL'i ayarla (kendi IP adresinle değiştir)
# Dosya: src/services/api.js
# const API_BASE_URL = 'http://192.168.1.XXX:3000';

npm install
npm start
```

Expo QR kodunu Expo Go uygulaması ile tarayarak çalıştırın.

### 4️⃣ API Testleri (Rest Assured)

```bash
cd test-automation

# Maven ile testleri çalıştır
mvn test

# Veya belirli bir test
mvn test -Dtest=WasteClassificationApiTest
```

---

## 🧪 Test Senaryoları

### API Test Coverage

1. **Health Check** (GET /health)
   - Status code: 200 ✅
   - Response body kontrolleri ✅
   - Response time: < 1s ✅

2. **Atık Türleri Listesi** (GET /api/waste-types)
   - Status code: 200 ✅
   - 5 atık sınıfı kontrolü ✅
   - Response time: < 2s ✅

3. **Atık Sınıflandırma** (POST /api/classify)
   - Multipart file upload ✅
   - Prediction response kontrolü ✅
   - Response time: < 5s ✅

4. **Hatalı İstek** (POST /api/classify - no image)
   - Status code: 400 ✅
   - Error message kontrolü ✅

---

## 📊 Veri Seti

### Veri Toplama Kuralları

- ✅ Kampüs içinde özgün fotoğraflar
- ✅ Her sınıf için minimum 300 görsel
- ✅ 5 ana sınıf: Kağıt, Cam, Metal, Organik, Plastik
- ❌ İnternetten hazır veri seti kullanımı yasak

### Veri Seti İstatistikleri

```
Toplam Görsel: 1500+
├── Kağıt Atık    : 300+
├── Cam Atık      : 300+
├── Metal Atık    : 300+
├── Organik Atık  : 300+
└── Plastik Atık  : 300+
```

---

## 🎯 API Endpoints

### Backend API (Port: 3000)

| Method | Endpoint           | Açıklama                    |
|--------|--------------------|-----------------------------|
| GET    | /health            | Servis sağlık kontrolü      |
| GET    | /api/waste-types   | Atık türleri listesi        |
| POST   | /api/classify      | Görsel sınıflandırma        |
| GET    | /api/stats         | Model istatistikleri        |

### ML Service API (Port: 5000)

| Method | Endpoint   | Açıklama                    |
|--------|------------|-----------------------------|
| GET    | /health    | ML servis kontrolü          |
| POST   | /predict   | Model inference             |
| POST   | /retrain   | Model yeniden eğitimi       |

---

## 🎨 Atık Türleri ve Renk Kodları

| Atık Türü | Kutu Rengi    | Hex Kodu  |
|-----------|---------------|-----------|
| 📄 Kağıt  | Mavi          | #3B82F6   |
| 🪟 Cam    | Yeşil         | #10B981   |
| 🔩 Metal  | Gri           | #6B7280   |
| 🍎 Organik| Kahverengi    | #92400E   |
| 🥤 Plastik| Sarı          | #F59E0B   |

---

## 🧠 Model Bilgileri

### Kullanılan Framework
- **YOLOv8n** (Pretrained)
- **Custom Dataset** ile fine-tuning

### Model Performans Metrikleri
- Precision: TBD
- Recall: TBD
- F1-Score: TBD
- mAP: TBD

*(Model eğitimi tamamlandığında güncellenecek)*

---

## 📱 Mobil Uygulama Ekran Görüntüleri

*(Ekran görüntüleri eklenecek)*

---

## 🤝 Katkıda Bulunanlar

- **Görkem** - 4. Sınıf Bilgisayar Mühendisliği Öğrencisi

---

## 📝 Lisans

Bu proje eğitim amaçlı geliştirilmiştir.

---

## 📞 İletişim

Sorularınız için: [email korunmuş]

---

## 🔧 Troubleshooting

### Backend bağlantı hatası
```bash
# Backend servisinin çalıştığını kontrol edin
curl http://localhost:3000/health
```

### ML servisi yanıt vermiyor
```bash
# Python servisini kontrol edin
curl http://localhost:5000/health
```

### Mobil uygulamada API hatası
- `src/services/api.js` dosyasında IP adresini kontrol edin
- Backend ve ML servislerinin çalıştığını doğrulayın
- Aynı ağda olduğunuzdan emin olun

---

## 📚 Referanslar

- [YOLOv8 Documentation](https://docs.ultralytics.com/)
- [React Native Documentation](https://reactnative.dev/)
- [Express.js Guide](https://expressjs.com/)
- [Rest Assured Documentation](https://rest-assured.io/)

---

**Son Güncelleme**: 11 Aralık 2024  
**Proje Durumu**: 🟢 Geliştirme Aşamasında
