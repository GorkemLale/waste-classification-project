package com.wasteapp.test;

public class TestConfig {
    // API Base URLs
    public static final String BASE_URL = "http://localhost:3000";
    public static final String ML_SERVICE_URL = "http://localhost:5000";
    
    // API Endpoints
    public static final String HEALTH_ENDPOINT = "/health";
    public static final String CLASSIFY_ENDPOINT = "/api/classify";
    public static final String WASTE_TYPES_ENDPOINT = "/api/waste-types";
    public static final String STATS_ENDPOINT = "/api/stats";
    
    // Test Data Paths
    public static final String TEST_IMAGES_PATH = "src/test/resources/test-images/";
    
    // Expected Response Times (milliseconds)
    public static final long MAX_HEALTH_RESPONSE_TIME = 1000;
    public static final long MAX_CLASSIFY_RESPONSE_TIME = 5000;
    public static final long MAX_GET_RESPONSE_TIME = 2000;
    
    // Expected Values
    public static final int SUCCESS_STATUS_CODE = 200;
    public static final int BAD_REQUEST_STATUS_CODE = 400;
    public static final int SERVER_ERROR_STATUS_CODE = 500;
    
    public static final String[] WASTE_CLASSES = {
        "paper", "glass", "metal", "organic", "plastic"
    };
}
