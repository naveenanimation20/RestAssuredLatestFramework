package com.qa.api.tests;

import org.testng.annotations.Test;

import com.qa.api.base.BaseTest;
import com.qa.api.constants.APIHttpStatus;

public class CiruitTest extends BaseTest{
	
	@Test
	public void getYearCircuitTest() {
		prop.setProperty("baseUri", "http://ergast.com");
		restClient.get(CIRCUIT_ENDPOINT, false, true)
			.then().log().all()
				.assertThat().statusCode(APIHttpStatus.OK_200.getCode());
	}

}
