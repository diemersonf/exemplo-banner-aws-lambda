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

public class ExemploBannerPostHandler implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

	Gson gson = new GsonBuilder().setPrettyPrinting().create();
	
	@Override
	public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent input, Context context) {
		AmazonDynamoDB db = AmazonDynamoDBClientBuilder.defaultClient();
		DynamoDBMapper dynamoDBMapper = new DynamoDBMapper(db);
		
		APIGatewayProxyResponseEvent response = new APIGatewayProxyResponseEvent();

		try {
			Banner banner = null;
			Banner bannerSalvo = null;
			
			HashMap<String, String> headers = new HashMap<String, String>();
			headers.put("Content-Type", "application/json");
			response.setHeaders(headers);			
			response.setIsBase64Encoded(false);

			if (input.getHttpMethod().equals("POST")) {
				if (!input.getBody().isEmpty()) {
					banner = gson.fromJson(input.getBody(), Banner.class);
					dynamoDBMapper.save(banner);
					bannerSalvo = dynamoDBMapper.load(Banner.class, banner.getNome());
					
					response.setStatusCode(201);
					response.setBody(gson.toJson(input.getBody()));
					response.setBody(gson.toJson(bannerSalvo));
				} else {
					response.setStatusCode(400);
					response.setBody("Invalid request! Check that the data has been post correctly.");
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