package com.qa.api.restclient;

import static io.restassured.RestAssured.given;

import java.util.Map;
import java.util.Properties;

import com.qa.api.constants.APIHttpStatus;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class RestClient {
	
    private String baseUri;
//    private static final String BEARER_TOKEN = Configuration.getProperty("token");
    private boolean isAuthorizationHeaderAdded = false;
    
    private RequestSpecBuilder specBuilder;
    
    Properties prop;
    
    public RestClient(Properties prop, String baseURI) {
    	specBuilder = new RequestSpecBuilder();
    	this.prop = prop;
    	this.baseUri = baseURI;
    }

    private void setRequestContentType(String contentType) {
    	switch (contentType) {
		case "json":
			specBuilder.setContentType(ContentType.JSON);
			break;
		case "xml":
			specBuilder.setContentType(ContentType.XML);
			break;
		case "text":
			specBuilder.setContentType(ContentType.TEXT);
			break;	
		default:
			break;
		}
    	
    }
    
    public void resetAuthorizationHeaderFlag() {
        isAuthorizationHeaderAdded = false;
    }
    
    public void addAuthorizationHeader() {
    	// Check if Authorization header is already set before adding it again
        if (!isAuthorizationHeaderAdded) {
            specBuilder.addHeader("Authorization", "Bearer " + prop.getProperty("token"));
            isAuthorizationHeaderAdded = true;
        }
    }
    
    private RequestSpecification createRequestSpec(boolean includeAuth) {
        specBuilder
           .setBaseUri(baseUri);
        if(includeAuth) {addAuthorizationHeader();}
		return specBuilder.build();
   }
    
    private RequestSpecification createRequestSpec(Map<String, String> headersMap, boolean includeAuth) {
		specBuilder
            .setBaseUri(baseUri);
        if(includeAuth) {addAuthorizationHeader();}
        
        if(headersMap!=null) {
        	specBuilder.addHeaders(headersMap);
        }
        
        return specBuilder.build();
    }
    
    private RequestSpecification createRequestSpec(Map<String, String> headersMap, Map<String, Object> queryParams, boolean includeAuth) {
		specBuilder
            .setBaseUri(baseUri);
        if(includeAuth) {addAuthorizationHeader();}
        
        if(headersMap!=null) {
        	specBuilder.addHeaders(headersMap);
        }
        if(queryParams!=null) {
        	specBuilder.addQueryParams(queryParams);
        }
        
        return specBuilder.build();
    }
	
	private RequestSpecification createRequestSpec(Object requestBody, String contentType, boolean includeAuth) {
		specBuilder
            .setBaseUri(baseUri)
            .setContentType(ContentType.JSON);
		
        if(includeAuth) {addAuthorizationHeader();}

        setRequestContentType(contentType);    

        if (requestBody != null) {
            specBuilder.setBody(requestBody);
        }

        return specBuilder.build();
    }
	
    
    private RequestSpecification createRequestSpec(Object requestBody, String contentType, Map<String, String> headersMap, boolean includeAuth) {
    	specBuilder
            .setBaseUri(baseUri)
            .setContentType(ContentType.JSON)
        	.addHeaders(headersMap);
    	
        if(includeAuth) {addAuthorizationHeader();}
        
 		setRequestContentType(contentType);
 		
        if (requestBody != null) {
            specBuilder.setBody(requestBody);
        }

        return specBuilder.build();
    }
	
	
    public Response get(String serviceUrl, boolean includeAuth, boolean log) {
    	
    	if(log) {
        	return RestAssured.given(createRequestSpec(includeAuth)).log().all()
        		.when()
        			.get(serviceUrl);
    	}
    	return RestAssured.given(createRequestSpec(includeAuth)).when().get(serviceUrl);
    }
        
    
    public Response get(String serviceUrl, Map<String, String> headersMap, boolean includeAuth, boolean log) {
    	
    	if(log) {
            return RestAssured.given(createRequestSpec(headersMap, includeAuth)).log().all()
        		.when()
        			.get(serviceUrl);
    	}
    	return RestAssured.given(createRequestSpec(headersMap, includeAuth)).when().get(serviceUrl);    	
    }
    
    
    
    public Response get(String serviceUrl, Map<String, Object> queryParams, Map<String, String> headersMap, boolean includeAuth, boolean log) {
    	
    	if(log) {
        	return RestAssured.given(createRequestSpec(headersMap, queryParams, includeAuth)).log().all()
        		.when()
        			.get(serviceUrl);
    	}
    	return RestAssured.given(createRequestSpec(headersMap, queryParams, includeAuth)).when().get(serviceUrl);
    }
    

    
    public Response post(String serviceUrl, String contentType, Object requestBody, boolean includeAuth, boolean log) {
    	
    	if(log) {
    		return RestAssured.given(createRequestSpec(requestBody, contentType, includeAuth)).log().all()
    			.when()
    				.post(serviceUrl);
    	}
    	return RestAssured.given(createRequestSpec(requestBody, contentType, includeAuth)).when().post(serviceUrl);    
    	
    }
    
    
    public Response post(String serviceUrl, String contentType, Object requestBody, Map<String, String> headersMap, boolean includeAuth, boolean log) {
        
        if(log) {
        	return RestAssured.given(createRequestSpec(requestBody, contentType, headersMap, includeAuth)).log().all()
    			.when()
    				.post(serviceUrl);
    	}
    	return RestAssured.given(createRequestSpec(requestBody, contentType, headersMap, includeAuth)).when().post(serviceUrl); 
        
    }

    public Response put(String serviceUrl, String contentType, Object requestBody, boolean includeAuth, boolean log) {
    	
        if(log) {
        	return RestAssured.given(createRequestSpec(requestBody, contentType, includeAuth)).log().all()
    			.when()
    				.put(serviceUrl);
    	}
    	return RestAssured.given(createRequestSpec(requestBody, contentType, includeAuth)).when().put(serviceUrl); 
    }
    
    
    public Response put(String serviceUrl, String contentType, Object requestBody, Map<String, String> headersMap, boolean includeAuth, boolean log) {
    	if(log) {
    		return RestAssured.given(createRequestSpec(requestBody, contentType, headersMap, includeAuth)).log().all()
    			.when()
    				.put(serviceUrl);
    	}
    	return RestAssured.given(createRequestSpec(requestBody, contentType, headersMap, includeAuth)).when().put(serviceUrl);
    
    }

    public Response patch(String serviceUrl, String contentType, Object requestBody, boolean includeAuth, boolean log) {
    	
        if(log) {
        	return RestAssured.given(createRequestSpec(requestBody, contentType, includeAuth)).log().all()
    			.when()
    				.patch(serviceUrl);
    	}
    	return RestAssured.given(createRequestSpec(requestBody, contentType, includeAuth)).when().put(serviceUrl); 
    }
    
    
    public Response patch(String serviceUrl, String contentType, Object requestBody, Map<String, String> headersMap, boolean includeAuth, boolean log) {
    	if(log) {
    		return	RestAssured.given(createRequestSpec(requestBody, contentType, headersMap, includeAuth)).log().all()
    			.when()
    				.patch(serviceUrl);
    	}
    	return RestAssured.given(createRequestSpec(requestBody, contentType, headersMap, includeAuth)).when().put(serviceUrl);
    
    }

    public Response delete(String serviceUrl, boolean includeAuth, boolean log) {
    	if(log){
    		return RestAssured.given(createRequestSpec(includeAuth)).log().all()
    			.when()
    				.delete(serviceUrl);
    	}
    	return RestAssured.given(createRequestSpec(includeAuth)).when().delete(serviceUrl);   
    }
    
    
    
    public String getAccessToken(String serviceURL, 
			String clientCredentials, String clientId, String clientSecret) {
				RestAssured.baseURI = baseUri;
				Response response = given()
				.contentType(ContentType.URLENC)
				.formParam("grant_type", clientCredentials)
				.formParam("client_id", clientId)
				.formParam("client_secret", clientSecret)
				.when()
				.post(serviceURL);
				
				response.then()
				.assertThat()
				    .statusCode(APIHttpStatus.OK_200.getCode());
				
				String accessToken = response.getBody().jsonPath().getString("access_token");
				System.out.println("access token : " + accessToken);
				return accessToken;
}
	
}
