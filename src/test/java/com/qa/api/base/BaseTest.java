package com.qa.api.base;

import java.util.Properties;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;

import com.qa.api.propconfig.Configuration;
import com.qa.api.restclient.RestClient;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;

public class BaseTest {

	protected static final String GOREST_ENDPOINT = "/public/v2/users/";
	protected static final String CIRCUIT_ENDPOINT = "api/f1/2017/circuits.json";
	protected static final String REQRES_ENDPOINT = "/api/users/2";

	protected RestClient restClient;
	protected Properties prop;
	protected Configuration config;

	@Parameters({ "baseURI" })
	@BeforeTest
	public void setup(String baseURI) {
		RestAssured.filters(new AllureRestAssured());
		config = new Configuration();
		prop = config.initProp();
		// prop.setProperty("baseURI", baseURI);
		restClient = new RestClient(prop, baseURI);

	}

}

