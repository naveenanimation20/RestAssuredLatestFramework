package com.qa.api.restclient;

import static io.restassured.RestAssured.given;

import com.qa.api.constants.APIHttpStatus;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class AccessTokenProvider{

	    public static String getAccessToken(String baseURI, String serviceURL, 
	    					String clientCredentials, String clientId, String clientSecret) {
	        RestAssured.baseURI = baseURI;
	        Response response = given()
	            .contentType(ContentType.URLENC)
	            .formParam("grant_type", "clientCredentials")
	            .formParam("client_id", clientId)
	            .formParam("client_secret", clientSecret)
	        .when()
	            .post(serviceURL);

	        response.then()
	            .assertThat()
	                .statusCode(APIHttpStatus.OK_200.getCode());

	        return response.getBody().jsonPath().getString("access_token");
	    }
	}

	
	
	


