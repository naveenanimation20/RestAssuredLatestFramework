package com.qa.api.restclient;

import java.util.Map;


import com.qa.api.propconfig.Configuration;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class RestClient {
	
    private static final String BASE_URI = Configuration.getProperty("baseUri");
    private static final String BEARER_TOKEN = Configuration.getProperty("token");
    private boolean isAuthorizationHeaderAdded = false;

    
    private static RequestSpecBuilder specBuilder;
    static {
    	specBuilder = new RequestSpecBuilder();
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
            specBuilder.addHeader("Authorization", "Bearer " + BEARER_TOKEN);
            isAuthorizationHeaderAdded = true;
        }
    }
    
    private RequestSpecification createRequestSpec() {
        specBuilder
           .setBaseUri(BASE_URI);
        addAuthorizationHeader();
		return specBuilder.build();
   }
    
    private RequestSpecification createRequestSpec(Map<String, String> headersMap) {
		specBuilder
            .setBaseUri(BASE_URI);
		addAuthorizationHeader();
        
        if(headersMap!=null) {
        	specBuilder.addHeaders(headersMap);
        }
        
        return specBuilder.build();
    }
    
    private RequestSpecification createRequestSpec(Map<String, String> headersMap, Map<String, String> queryParams) {
		specBuilder
            .setBaseUri(BASE_URI);
		addAuthorizationHeader();
        
        if(headersMap!=null) {
        	specBuilder.addHeaders(headersMap);
        }
        if(queryParams!=null) {
        	specBuilder.addQueryParams(queryParams);
        }
        
        return specBuilder.build();
    }
	
	private RequestSpecification createRequestSpec(Object requestBody, String contentType) {
		specBuilder
            .setBaseUri(BASE_URI)
            .setContentType(ContentType.JSON);
		
		addAuthorizationHeader();
		setRequestContentType(contentType);    

        if (requestBody != null) {
            specBuilder.setBody(requestBody);
        }

        return specBuilder.build();
    }
	
    
    private RequestSpecification createRequestSpec(Object requestBody, String contentType, Map<String, String> headersMap) {
    	specBuilder
            .setBaseUri(BASE_URI)
            .setContentType(ContentType.JSON)
        	.addHeaders(headersMap);
    	addAuthorizationHeader();
 		setRequestContentType(contentType);
 		
        if (requestBody != null) {
            specBuilder.setBody(requestBody);
        }

        return specBuilder.build();
    }
	
	
    public Response get(String serviceUrl, boolean log) {
    	
    	if(log) {
        	return RestAssured.given(createRequestSpec()).log().all()
        		.when()
        			.get(serviceUrl);
    	}
    	return RestAssured.given(createRequestSpec()).when().get(serviceUrl);
    }
        
    
    public Response get(String serviceUrl, Map<String, String> headersMap, boolean log) {
    	
    	if(log) {
            return RestAssured.given(createRequestSpec(headersMap)).log().all()
        		.when()
        			.get(serviceUrl);
    	}
    	return RestAssured.given(createRequestSpec(headersMap)).when().get(serviceUrl);    	
    }
    
    
    
    public Response get(String serviceUrl, Map<String, String> queryParams, Map<String, String> headersMap, boolean log) {
    	
    	if(log) {
        	return RestAssured.given(createRequestSpec(headersMap, queryParams)).log().all()
        		.when()
        			.get(serviceUrl);
    	}
    	return RestAssured.given(createRequestSpec(headersMap, queryParams)).when().get(serviceUrl);
    }
    

    
    public Response post(String serviceUrl, String contentType, Object requestBody, boolean log) {
    	
    	if(log) {
    		return RestAssured.given(createRequestSpec(requestBody, contentType)).log().all()
    			.when()
    				.post(serviceUrl);
    	}
    	return RestAssured.given(createRequestSpec(requestBody, contentType)).when().post(serviceUrl);    
    	
    }
    
    
    public Response post(String serviceUrl, String contentType, Object requestBody, Map<String, String> headersMap, boolean log) {
        
        if(log) {
        	return RestAssured.given(createRequestSpec(requestBody, contentType, headersMap)).log().all()
    			.when()
    				.post(serviceUrl);
    	}
    	return RestAssured.given(createRequestSpec(requestBody, contentType, headersMap)).when().post(serviceUrl); 
        
    }

    public Response put(String serviceUrl, String contentType, Object requestBody, boolean log) {
    	
        if(log) {
        	return RestAssured.given(createRequestSpec(requestBody, contentType)).log().all()
    			.when()
    				.put(serviceUrl);
    	}
    	return RestAssured.given(createRequestSpec(requestBody, contentType)).when().put(serviceUrl); 
    }
    
    
    public Response put(String serviceUrl, String contentType, Object requestBody, Map<String, String> headersMap, boolean log) {
    	if(log) {
    		return RestAssured.given(createRequestSpec(requestBody, contentType, headersMap)).log().all()
    			.when()
    				.put(serviceUrl);
    	}
    	return RestAssured.given(createRequestSpec(requestBody, contentType, headersMap)).when().put(serviceUrl);
    
    }

    public Response patch(String serviceUrl, String contentType, Object requestBody, boolean log) {
    	
        if(log) {
        	return RestAssured.given(createRequestSpec(requestBody, contentType)).log().all()
    			.when()
    				.patch(serviceUrl);
    	}
    	return RestAssured.given(createRequestSpec(requestBody, contentType)).when().put(serviceUrl); 
    }
    
    
    public Response patch(String serviceUrl, String contentType, Object requestBody, Map<String, String> headersMap, boolean log) {
    	if(log) {
    		return	RestAssured.given(createRequestSpec(requestBody, contentType, headersMap)).log().all()
    			.when()
    				.patch(serviceUrl);
    	}
    	return RestAssured.given(createRequestSpec(requestBody, contentType, headersMap)).when().put(serviceUrl);
    
    }

    public Response delete(String serviceUrl, boolean log) {
    	if(log){
    		return RestAssured.given(createRequestSpec()).log().all()
    			.when()
    				.delete(serviceUrl);
    	}
    	return RestAssured.given(createRequestSpec()).when().delete(serviceUrl);   
    }
    
	
}
