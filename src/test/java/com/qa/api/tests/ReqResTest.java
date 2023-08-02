package com.qa.api.tests;

import org.testng.annotations.Test;

import com.qa.api.base.BaseTest;
import com.qa.api.constants.APIHttpStatus;

public class ReqResTest extends BaseTest{
	
	@Test
	public void getReqResUserTest() {
		prop.setProperty("baseUri", "https://reqres.in");
		restClient.get(REQRES_ENDPOINT, false, true)
			.then().log().all()
				.assertThat().statusCode(APIHttpStatus.OK_200.getCode());
	}

}
