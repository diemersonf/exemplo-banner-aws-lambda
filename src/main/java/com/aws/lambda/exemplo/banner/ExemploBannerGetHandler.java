package com.aws.lambda.exemplo.banner;

import java.util.HashMap;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class ExemploBannerGetHandler implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

	Gson gson = new GsonBuilder().setPrettyPrinting().create();
	
	@Override
	public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent input, Context context) {
		AmazonDynamoDB db = AmazonDynamoDBClientBuilder.defaultClient();
		DynamoDBMapper dynamoDBMapper = new DynamoDBMapper(db);
		
		APIGatewayProxyResponseEvent response = new APIGatewayProxyResponseEvent();

		try {
			Banner banner = null;
			
			HashMap<String, String> headers = new HashMap<String, String>();
			headers.put("Content-Type", "application/json");
			response.setHeaders(headers);
			response.setIsBase64Encoded(false);
			
			if (input.getHttpMethod().equals("GET")) {
				if (input.getQueryStringParameters().containsKey("bannername")) {
					banner = dynamoDBMapper.load(Banner.class, input.getQueryStringParameters().get("bannername"));
					
					if (banner != null) {
						response.setStatusCode(200);
						response.setBody(gson.toJson(banner));
					} else {
						response.setStatusCode(404);
						response.setBody(
								String.format("There isn't a banner with the name: %s", input.getQueryStringParameters().get("bannername")));
					}
				} else {
					response.setStatusCode(400);
					response.setBody("Invalid parameters! Check that the parameter name has been set correctly");
				}
				
			} else {
				response.setStatusCode(405);
			}
			return response;
			
		} catch (Exception ex) {
			response.setStatusCode(500);
			response.setBody(ex.toString());
			return response;
		}
	}
}
