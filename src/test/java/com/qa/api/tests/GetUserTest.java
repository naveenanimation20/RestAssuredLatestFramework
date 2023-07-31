package com.qa.api.tests;

import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.api.constants.APIHttpStatus;
import com.qa.api.pojo.User;
import com.qa.api.restclient.RestClient;
import com.qa.api.utils.StringUtils;

import static org.hamcrest.Matchers.*;

public class GetUserTest {
	RestClient restClient;
	
	@BeforeTest
	public void setup() {
		restClient = new RestClient();
//		String token = System.getProperty("token");
//		System.out.println("========toke ====" + token);
//		Configuration.setProperty("token", token);
	}
	
	
	@Test
	public void getAllUsersTest() {
		restClient.get("/public/v2/users/", true)
			.then().log().all()
				.assertThat().statusCode(APIHttpStatus.OK_200.getCode());
	}
	
	@Test
	public void getUsersTest() {
		restClient.get("/public/v2/users/4092187", true)
			.then().log().all()
				.assertThat().statusCode(APIHttpStatus.OK_200.getCode());
	}
	
	@Test
	public void getUsersWithQueryTest() {
		
		Map<String, String> queryMap = new HashMap<String, String>();
		queryMap.put("name", "naveen");
		queryMap.put("status", "active");
		restClient.get("/public/v2/users/", queryMap, null, true)
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
		Integer userId = restClient.post("/public/v2/users/", "json", user, true)
				.then().log().all()
					.assertThat()
						.statusCode(APIHttpStatus.CREATED_201.getCode())
							.and()
							.extract().path("id");
		
		System.out.println("user id: " + userId);
		
		restClient.get("/public/v2/users/"+userId, true)
		.then().log().all()
			.assertThat().statusCode(APIHttpStatus.OK_200.getCode());
					
	}
	
	
	@Test
	public void createUserWithBuiderTest() {		
		User user  = User.builder().name("NaveenK").email(StringUtils.getRandomEmailId()).gender("male").status("active").build();
		
		Integer userId = restClient.post("/public/v2/users/", "json", user, true)
				.then().log().all()
					.assertThat()
						.statusCode(APIHttpStatus.CREATED_201.getCode())
							.and()
							.extract().path("id");
		
		System.out.println("user id: " + userId);
		
		restClient.get("/public/v2/users/"+userId, true)
		.then().log().all()
			.assertThat().statusCode(APIHttpStatus.OK_200.getCode());
					
	}
	
	
	@Test
	public void updateUserTest() {
		//1. POST
		User user = new User("Naveen", StringUtils.getRandomEmailId(), "male", "active");
		Integer userId = restClient.post("/public/v2/users/", "json", user, true)
				.then().log().all(true)
					.assertThat()
						.statusCode(APIHttpStatus.CREATED_201.getCode())
							.and()
							.extract().path("id");
		
		System.out.println("user id: " + userId);
		
		//2. PUT
		user.setName("Naveen Automation");
		user.setStatus("inactive");
		restClient.put("/public/v2/users/"+userId, "json", user, true)
					.then().log().all(true)
						.assertThat()
							.statusCode(APIHttpStatus.OK_200.getCode())
								.body("id", equalTo(userId));
		
		
		//3. GET
		restClient.get("/public/v2/users/"+userId, true)
		.then().log().all(true)
			.assertThat().statusCode(APIHttpStatus.OK_200.getCode());
					
	}
	
	
	@Test
	public void deleteUserTest() {
		//1. POST
		User user = new User("Naveen", StringUtils.getRandomEmailId(), "male", "active");
		Integer userId = restClient.post("/public/v2/users/", "json", user, true)
				.then().log().all(true)
					.assertThat()
						.statusCode(APIHttpStatus.CREATED_201.getCode())
							.and()
							.extract().path("id");
		
		System.out.println("user id: " + userId);
		
		//2. DELETE
		restClient.delete("/public/v2/users/"+userId, true)
					.then().log().all(true)
						.assertThat()
							.statusCode(APIHttpStatus.NO_CONTENT_204.getCode());
		
		
		//3. GET
		restClient.get("/public/v2/users/"+userId, true)
		.then().log().all(true)
			.assertThat().statusCode(APIHttpStatus.NOT_FOUND_404.getCode()).body("message", equalTo("Resource not found"));
					
	}
	

}
