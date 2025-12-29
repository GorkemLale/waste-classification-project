package com.wasteapp.test;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.http.ContentType;
import org.junit.BeforeClass;
import org.junit.Test;
import org.hamcrest.Matchers;

import java.io.File;
import java.util.concurrent.TimeUnit;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

/**
 * Waste Classification API Test Suite
 * 
 * MTH-1 Yazılım Test Mühendisliği Ödevi
 * Rest Assured kullanarak API otomatik regresyon testleri
 * 
 * Test Senaryoları:
 * 1. Health Check - GET isteği
 * 2. Atık Türleri Listesi - GET isteği
 * 3. Atık Sınıflandırma - POST isteği (görsel upload)
 * 4. Hatalı İstek Kontrolü - POST isteği (görsel olmadan)
 */
public class WasteClassificationApiTest {

    @BeforeClass
    public static void setup() {
        RestAssured.baseURI = TestConfig.BASE_URL;
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }

    /**
     * Test 1: Health Check Endpoint
     * GET /health
     * 
     * Kontroller:
     * - Status code: 200
     * - Response body: status = "ok"
     * - Response time: < 1 saniye
     */
    @Test
    public void testHealthCheck() {
        System.out.println("\n=== Test 1: Health Check ===");
        
        Response response = given()
            .when()
                .get(TestConfig.HEALTH_ENDPOINT)
            .then()
                .statusCode(TestConfig.SUCCESS_STATUS_CODE)
                .time(lessThan(TestConfig.MAX_HEALTH_RESPONSE_TIME), TimeUnit.MILLISECONDS)
                .body("status", equalTo("ok"))
                .body("message", notNullValue())
                .body("timestamp", notNullValue())
            .extract()
                .response();

        System.out.println("✓ Status Code: " + response.getStatusCode());
        System.out.println("✓ Response Time: " + response.getTime() + "ms");
        System.out.println("✓ Status: " + response.jsonPath().getString("status"));
        System.out.println("✓ Message: " + response.jsonPath().getString("message"));
    }

    /**
     * Test 2: Get Waste Types
     * GET /api/waste-types
     * 
     * Kontroller:
     * - Status code: 200
     * - Response body: success = true
     * - Response body: data array içeriyor (5 atık türü)
     * - Response time: < 2 saniye
     */
    @Test
    public void testGetWasteTypes() {
        System.out.println("\n=== Test 2: Get Waste Types ===");
        
        Response response = given()
            .when()
                .get(TestConfig.WASTE_TYPES_ENDPOINT)
            .then()
                .statusCode(TestConfig.SUCCESS_STATUS_CODE)
                .time(lessThan(TestConfig.MAX_GET_RESPONSE_TIME), TimeUnit.MILLISECONDS)
                .body("success", equalTo(true))
                .body("data", notNullValue())
                .body("data.size()", equalTo(5))
                .body("data[0].type", notNullValue())
                .body("data[0].name", notNullValue())
                .body("data[0].color", notNullValue())
            .extract()
                .response();

        System.out.println("✓ Status Code: " + response.getStatusCode());
        System.out.println("✓ Response Time: " + response.getTime() + "ms");
        System.out.println("✓ Waste Types Count: " + response.jsonPath().getList("data").size());
        
        // Tüm atık türlerini kontrol et
        for (String wasteClass : TestConfig.WASTE_CLASSES) {
            assertTrue("Atık türü bulunamadı: " + wasteClass,
                response.jsonPath().getString("data.type").contains(wasteClass));
        }
        System.out.println("✓ All waste types present");
    }

    /**
     * Test 3: Classify Waste - Valid Request
     * POST /api/classify
     * 
     * Kontroller:
     * - Status code: 200
     * - Response body: success = true
     * - Response body: predictions array içeriyor
     * - Request body: multipart/form-data (image file)
     * - Response time: < 5 saniye
     */
    @Test
    public void testClassifyWasteValidRequest() {
        System.out.println("\n=== Test 3: Classify Waste (Valid) ===");
        
        // Test görseli oluştur (basit bir placeholder)
        File testImage = createTestImage();
        
        if (testImage == null || !testImage.exists()) {
            System.out.println("⚠ Test görseli bulunamadı, test atlanıyor");
            return;
        }

        Response response = given()
                .multiPart("image", testImage)
                .contentType("multipart/form-data")
            .when()
                .post(TestConfig.CLASSIFY_ENDPOINT)
            .then()
                .statusCode(TestConfig.SUCCESS_STATUS_CODE)
                .time(lessThan(TestConfig.MAX_CLASSIFY_RESPONSE_TIME), TimeUnit.MILLISECONDS)
                .body("success", equalTo(true))
                .body("data", notNullValue())
                .body("data.imageUrl", notNullValue())
                .body("data.predictions", notNullValue())
            .extract()
                .response();

        System.out.println("✓ Status Code: " + response.getStatusCode());
        System.out.println("✓ Response Time: " + response.getTime() + "ms");
        System.out.println("✓ Image URL: " + response.jsonPath().getString("data.imageUrl"));
        System.out.println("✓ Predictions Count: " + 
            response.jsonPath().getList("data.predictions").size());
        
        // Cleanup
        if (testImage.exists()) {
            testImage.delete();
        }
    }

    /**
     * Test 4: Classify Waste - Invalid Request (No Image)
     * POST /api/classify
     * 
     * Kontroller:
     * - Status code: 400 (Bad Request)
     * - Response body: success = false
     * - Response body: error message içeriyor
     */
    @Test
    public void testClassifyWasteInvalidRequest() {
        System.out.println("\n=== Test 4: Classify Waste (Invalid - No Image) ===");
        
        Response response = given()
                .contentType("multipart/form-data")
            .when()
                .post(TestConfig.CLASSIFY_ENDPOINT)
            .then()
                .statusCode(TestConfig.BAD_REQUEST_STATUS_CODE)
                .body("success", equalTo(false))
                .body("error", notNullValue())
            .extract()
                .response();

        System.out.println("✓ Status Code: " + response.getStatusCode());
        System.out.println("✓ Error Message: " + response.jsonPath().getString("error"));
    }

    /**
     * Test 5: Get Statistics
     * GET /api/stats
     * 
     * Kontroller:
     * - Status code: 200
     * - Response body: success = true
     * - Response body: data.accuracy değeri var
     */
    @Test
    public void testGetStatistics() {
        System.out.println("\n=== Test 5: Get Statistics ===");
        
        Response response = given()
            .when()
                .get(TestConfig.STATS_ENDPOINT)
            .then()
                .statusCode(TestConfig.SUCCESS_STATUS_CODE)
                .time(lessThan(TestConfig.MAX_GET_RESPONSE_TIME), TimeUnit.MILLISECONDS)
                .body("success", equalTo(true))
                .body("data", notNullValue())
                .body("data.accuracy", notNullValue())
            .extract()
                .response();

        System.out.println("✓ Status Code: " + response.getStatusCode());
        System.out.println("✓ Response Time: " + response.getTime() + "ms");
        System.out.println("✓ Model Accuracy: " + 
            response.jsonPath().getDouble("data.accuracy"));
    }

    /**
     * Helper method: Test görseli oluştur
     */
    private File createTestImage() {
        try {
            // Basit bir test görseli oluştur
            File tempFile = File.createTempFile("test_waste_", ".jpg");
            
            // Gerçek bir görsel dosyası varsa onu kullan
            File existingImage = new File("src/test/resources/test-images/sample.jpg");
            if (existingImage.exists()) {
                return existingImage;
            }
            
            return tempFile;
        } catch (Exception e) {
            System.err.println("Test görseli oluşturulamadı: " + e.getMessage());
            return null;
        }
    }
}
