package com.qa.api.tests;

import org.testng.annotations.Test;

import com.qa.api.base.BaseTest;
import com.qa.api.constants.APIHttpStatus;
import com.qa.api.pojo.Product;
import com.qa.api.pojo.Product.Rating;

public class ProductAPITest extends BaseTest{
	
	@Test
	public void getProductTest() {
			restClient.get(PRODUCT_ENDPOINT, false, true)
				.then().log().all()
					.assertThat().statusCode(APIHttpStatus.OK_200.getCode());
		
	}
	
	
	@Test
	public void createProduct() {
		Rating rating = Rating.builder().rate(100.11).count(5).build();
		Product product = Product.builder().title("MacBook Pro").price(12.33).description("this is apple laptop")
					.image("https://i.pravatar.cc").rating(rating).build();
		restClient.post(PRODUCT_ENDPOINT, "json", product, false, true)
					.then()
					.assertThat().statusCode(APIHttpStatus.OK_200.getCode());
	}
	

}
