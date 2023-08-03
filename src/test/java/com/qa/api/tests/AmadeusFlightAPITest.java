package com.qa.api.tests;

import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.qa.api.base.BaseTest;
import com.qa.api.propconfig.Configuration;
import com.qa.api.restclient.RestClient;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class AmadeusFlightAPITest extends BaseTest{
	
	private String accessToken;
	
	@Parameters({"baseURI", "clientId", "clientSecret", "clientCredentials"})
    @BeforeClass
	public void setup(String baseURI, String clientId, String clientSecret, String clientCredentials) {
		config = new Configuration();
		prop = config.initProp();
		restClient = new RestClient(prop, baseURI);
		accessToken = restClient.getAccessToken(AMADEUS_AUTH_ENDPOINT, clientCredentials, clientId, clientSecret);

	}
	
	
	@Test
	public void getFlightInfoTest() {
		//2. get flight info: GET
		Map<String, Object> queryParams = new HashMap<String, Object>();
		queryParams.put("origin", "PAR");
		queryParams.put("maxPrice", 200);
		
		Map<String, String> headersMap = new HashMap<String, String>();
		headersMap.put("Authorization", "Bearer "+ accessToken);

		
		Response flightDataResponse = restClient.get(FLIGHT_DESTINATIONS, queryParams, headersMap, false, true)
				.then().log().all()
				.assertThat()
				.statusCode(200)
					.and()
						.extract()
							.response();
		
				JsonPath js = flightDataResponse.jsonPath();
				String type = js.get("data[0].type");
				System.out.println(type);//flight-destination
		
		
	}
	

}
