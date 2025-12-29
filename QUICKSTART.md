# 🚀 Hızlı Başlangıç Kılavuzu

## Projeyi İlk Kez Çalıştırma

### 1. Proje Dosyalarını İndir

Projeyi `/home/claude/waste-classification-project` klasöründen `/mnt/user-data/outputs` klasörüne kopyala:

```bash
cp -r /home/claude/waste-classification-project /mnt/user-data/outputs/
```

### 2. Backend Servisi Başlat

**Terminal 1:**
```bash
cd waste-classification-project/backend
npm install
npm start
```

✅ Backend çalışıyor: http://localhost:3000

### 3. ML Servisi Başlat

**Terminal 2:**
```bash
cd waste-classification-project/ml-service
pip3 install -r requirements.txt
python3 app.py
```

✅ ML Service çalışıyor: http://localhost:5000

### 4. Mobil Uygulamayı Başlat

**Terminal 3:**
```bash
cd waste-classification-project/mobile-app

# ÖNEMLİ: IP adresini güncelle!
# Dosya: src/services/api.js
# const API_BASE_URL = 'http://192.168.1.XXX:3000';

npm install
npm start
```

- QR kodu Expo Go uygulaması ile tara
- Veya Android emulator'de çalıştır

### 5. API Testlerini Çalıştır

**Terminal 4:**
```bash
cd waste-classification-project/test-automation
mvn test
```

---

## 📱 Mobil Uygulama Kurulumu

### Android

1. Google Play Store'dan **Expo Go** indir
2. Bilgisayarınızda `npm start` komutu çalıştır
3. QR kodu Expo Go ile tara
4. Uygulama açılsın

### iOS

1. App Store'dan **Expo Go** indir
2. Kamera ile QR kodu tara
3. Expo Go'da aç

---

## 🔧 IP Adresi Ayarlama

Mobil uygulama backend'e bağlanabilmesi için IP adresini güncellemelisin:

### IP Adresini Öğren

**Windows:**
```cmd
ipconfig
```

**Mac/Linux:**
```bash
ifconfig
```

**Örnek çıktı:**
```
IPv4 Address: 192.168.1.105
```

### IP Adresini Güncelle

**Dosya:** `mobile-app/src/services/api.js`

```javascript
// Değiştir:
const API_BASE_URL = 'http://192.168.1.100:3000';

// Şununla:
const API_BASE_URL = 'http://192.168.1.105:3000'; // Kendi IP'n
```

---

## ✅ Servis Kontrolü

### Backend Kontrolü
```bash
curl http://localhost:3000/health
```

Beklenen çıktı:
```json
{
  "status": "ok",
  "message": "Atık Türü Tanıma API çalışıyor",
  "timestamp": "2024-12-11T..."
}
```

### ML Service Kontrolü
```bash
curl http://localhost:5000/health
```

Beklenen çıktı:
```json
{
  "status": "ok",
  "message": "ML Servisi çalışıyor",
  "model_loaded": false,
  "timestamp": 1702300000.0
}
```

---

## 📊 Test Çalıştırma

### Tüm Testler
```bash
cd test-automation
mvn test
```

### Tek Test
```bash
mvn test -Dtest=WasteClassificationApiTest#testHealthCheck
```

---

## 🎯 Sonraki Adımlar

### 1. Veri Seti Toplama
- Kampüste atık fotoğrafları çek
- Her sınıftan 300+ görsel topla
- `dataset/` klasörüne ekle

### 2. Model Eğitimi
- YOLOv8 modelini kendi verilerinle eğit
- `ml-service/train.py` scripti kullan

### 3. Model Entegrasyonu
- Eğitilmiş modeli `ml-service/models/` klasörüne koy
- ML servisini yeniden başlat

### 4. Gerçek Test
- Mobil uygulamadan fotoğraf çek
- Sınıflandırma sonuçlarını gör
- API testlerini çalıştır

---

## 🐛 Yaygın Hatalar ve Çözümler

### "Cannot connect to backend"
- Backend servisinin çalıştığını kontrol et
- IP adresinin doğru olduğunu kontrol et
- Firewall ayarlarını kontrol et

### "ML service not responding"
- Python servisinin çalıştığını kontrol et
- Port 5000'in kullanılmadığını kontrol et
- Python bağımlılıklarını kontrol et

### "Module not found" (Python)
```bash
pip3 install ultralytics flask flask-cors pillow
```

### "Package not found" (Node.js)
```bash
npm install
```

### Maven hatası
```bash
mvn clean install -U
```

---

## 📞 Yardım

Sorunlarla karşılaşırsan:
1. README.md dosyalarını oku
2. Terminal çıktılarını kontrol et
3. Servislerin çalıştığını doğrula
4. IP adreslerini kontrol et

---

## 🎉 Başarı!

Tüm servisler çalışıyorsa:
- ✅ Backend: http://localhost:3000
- ✅ ML Service: http://localhost:5000
- ✅ Mobil App: Expo Go'da açık
- ✅ Tests: Geçiyor

Artık atık fotoğrafları çekip sınıflandırabilirsin! 🚀
