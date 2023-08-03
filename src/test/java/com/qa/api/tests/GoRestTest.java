package com.qa.api.tests;

import static org.hamcrest.Matchers.equalTo;

import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.api.base.BaseTest;
import com.qa.api.constants.APIHttpStatus;
import com.qa.api.pojo.User;
import com.qa.api.utils.StringUtils;

public class GoRestTest extends BaseTest{

	
	@Test
	public void getAllUsersTest() {
		restClient.get(GOREST_ENDPOINT, true, true)
			.then().log().all()
				.assertThat().statusCode(APIHttpStatus.OK_200.getCode());
	}
	
	@Test
	public void getUsersTest() {
		restClient.get(GOREST_ENDPOINT+4157155, true, true)
			.then().log().all()
				.assertThat().statusCode(APIHttpStatus.OK_200.getCode());
	}
	
	@Test
	public void getUsersWithQueryTest() {
		
		Map<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put("name", "naveen");
		queryMap.put("status", "active");
		restClient.get(GOREST_ENDPOINT, queryMap, null, true, true)
			.then().log().all()
				.assertThat().statusCode(APIHttpStatus.OK_200.getCode());
	}
	
	
	@DataProvider
	public Object[][] getUserData() {
		return new Object[][] {
			{"naveen","male","active"},
			{"tom","male","inactive"},
			{"lisa","female","active"},

		};
	}	
	
	@Test(dataProvider = "getUserData")
	public void createUserTest(String name, String gender, String status) {
		User user = new User(name, StringUtils.getRandomEmailId(), gender, status);
		Integer userId = restClient.post(GOREST_ENDPOINT, "json", user, true, true)
				.then().log().all()
					.assertThat()
						.statusCode(APIHttpStatus.CREATED_201.getCode())
							.and()
							.extract().path("id");
		
		System.out.println("user id: " + userId);
		
		restClient.get(GOREST_ENDPOINT+userId, true, true)
		.then().log().all()
			.assertThat().statusCode(APIHttpStatus.OK_200.getCode());
					
	}
	
	
	@Test
	public void createUserWithBuiderTest() {		
		User user  = User.builder().name("NaveenK").email(StringUtils.getRandomEmailId()).gender("male").status("active").build();
		
		Integer userId = restClient.post(GOREST_ENDPOINT, "json", user, true, true)
				.then().log().all()
					.assertThat()
						.statusCode(APIHttpStatus.CREATED_201.getCode())
							.and()
							.extract().path("id");
		
		System.out.println("user id: " + userId);
		
		restClient.get(GOREST_ENDPOINT+userId, true, true)
		.then().log().all()
			.assertThat().statusCode(APIHttpStatus.OK_200.getCode());
					
	}
	
	
	@Test
	public void updateUserTest() {
		//1. POST
		User user = new User("Naveen", StringUtils.getRandomEmailId(), "male", "active");
		Integer userId = restClient.post(GOREST_ENDPOINT, "json", user, true, true)
				.then().log().all(true)
					.assertThat()
						.statusCode(APIHttpStatus.CREATED_201.getCode())
							.and()
							.extract().path("id");
		
		System.out.println("user id: " + userId);
		
		//2. PUT
		user.setName("Naveen Automation");
		user.setStatus("inactive");
		restClient.put(GOREST_ENDPOINT+userId, "json", user, true, true)
					.then().log().all(true)
						.assertThat()
							.statusCode(APIHttpStatus.OK_200.getCode())
								.body("id", equalTo(userId));
		
		
		//3. GET
		restClient.get(GOREST_ENDPOINT+userId, true, true)
		.then().log().all(true)
			.assertThat().statusCode(APIHttpStatus.OK_200.getCode());
					
	}
	
	
	@Test
	public void deleteUserTest() {
		//1. POST
		User user = new User("Naveen", StringUtils.getRandomEmailId(), "male", "active");
		Integer userId = restClient.post(GOREST_ENDPOINT, "json", user, true, true)
				.then().log().all(true)
					.assertThat()
						.statusCode(APIHttpStatus.CREATED_201.getCode())
							.and()
							.extract().path("id");
		
		System.out.println("user id: " + userId);
		
		//2. DELETE
		restClient.delete(GOREST_ENDPOINT+userId, true, true)
					.then().log().all(true)
						.assertThat()
							.statusCode(APIHttpStatus.NO_CONTENT_204.getCode());
		
		
		//3. GET
		restClient.get(GOREST_ENDPOINT+userId, true, true)
		.then().log().all(true)
			.assertThat().statusCode(APIHttpStatus.NOT_FOUND_404.getCode()).body("message", equalTo("Resource not found"));
					
	}
	

}
