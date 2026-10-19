package com.vercel.deploys;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class DeploysApplicationTests {

	@LocalServerPort
	private int port;

	@Test
	void deploymentEndpointReturnsStatus() throws Exception {
		var request = HttpRequest.newBuilder(URI.create("http://localhost:" + port + "/is-deployed"))
				.GET()
				.build();
		var response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

		assertEquals(200, response.statusCode());
		assertEquals("Server is running at remote :  vercel", response.body());
	}

	@Test
	void deploymentEndpointAllowsCorsPreflight() throws Exception {
		var request = HttpRequest.newBuilder(URI.create("http://localhost:" + port + "/is-deployed"))
				.header("Origin", "https://swagger.example")
				.header("Access-Control-Request-Method", "GET")
				.method("OPTIONS", HttpRequest.BodyPublishers.noBody())
				.build();
		var response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

		assertEquals(200, response.statusCode());
		assertEquals("https://swagger.example", response.headers().firstValue("Access-Control-Allow-Origin").orElse(null));
	}

	@Test
	void openApiUsesRelativeServerUrl() throws Exception {
		var request = HttpRequest.newBuilder(URI.create("http://localhost:" + port + "/v3/api-docs"))
				.GET()
				.build();
		var response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

		assertEquals(200, response.statusCode());
		org.junit.jupiter.api.Assertions.assertTrue(response.body().contains("\"url\":\"/\""));
	}

}
