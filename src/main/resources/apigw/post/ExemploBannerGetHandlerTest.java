package com.aws.lambda.exemplo.banner;


import static org.junit.Assert.assertEquals;

import org.junit.jupiter.params.ParameterizedTest;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.amazonaws.services.lambda.runtime.tests.annotations.Events;
import com.amazonaws.services.lambda.runtime.tests.annotations.HandlerParams;
import com.amazonaws.services.lambda.runtime.tests.annotations.Responses;

/**
 * A simple test harness for locally invoking your Lambda function handler.
 */
public class ExemploBannerGetHandlerTest {
	private Context createContext() {
		TestContext contextMock = new TestContext();

		contextMock.setFunctionName("get-ion-home-banner");

		return contextMock;
	}

	@ParameterizedTest
	@HandlerParams(
	   events = @Events(folder = "apigw/get/events/", type = APIGatewayProxyRequestEvent.class),
	   responses = @Responses(folder = "apigw/get/responses/", type = APIGatewayProxyResponseEvent.class)
	)
	public void testIonHomeBannerGetHandler(APIGatewayProxyRequestEvent event, APIGatewayProxyResponseEvent response) {
		ExemploBannerGetHandler handler = new ExemploBannerGetHandler();
		Context contextMock = createContext();
		
	    APIGatewayProxyResponseEvent result = handler.handleRequest(event, contextMock);
	    
	    assertEquals(result.getStatusCode(), response.getStatusCode());
	    assertEquals(result.getBody(), response.getBody());
	}
}
