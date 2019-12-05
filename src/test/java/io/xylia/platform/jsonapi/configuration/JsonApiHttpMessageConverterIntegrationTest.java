package io.xylia.platform.jsonapi.configuration;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

import io.xylia.platform.jsonapi.dummy.app.SpringBootDummyApp;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import io.xylia.platform.jsonapi.dummy.app.controllers.DummyUserController;
import io.restassured.RestAssured;

/**
 * Integration test of {@link JsonApiHttpMessageConverter}.
 * 
 *
 */
@ActiveProfiles("test")
@SpringBootTest(classes = SpringBootDummyApp.class, webEnvironment = WebEnvironment.RANDOM_PORT)
public class JsonApiHttpMessageConverterIntegrationTest {

  @LocalServerPort
  private Integer port = 0;

  @BeforeEach
  public void setBaseUri() {
    RestAssured.port = port;
    RestAssured.baseURI = "http://localhost";
  }

  @Test
  public void canReadUserDataTest() {
    // @formatter:off
    String user = "{\n" + 
            "    \"data\": {\n" + 
            "        \"type\": \"users\",\n" + 
            "        \"id\": \"1\",\n" + 
            "        \"attributes\": {\n" + 
            "        }\n" + 
            "    }\n" + 
            "}";
    
    given().
      accept(JsonApiHttpMessageConverter.APPLICATION_JSON_API_VALUE).
      contentType(JsonApiHttpMessageConverter.APPLICATION_JSON_API_VALUE).
      body(user).

    when().
      post(DummyUserController.RESOURCE + "/" + DummyUserController.REQUEST_1).

    then().
      statusCode(200).and().
      body("data.id", is("1"));
    // @formatter:on
  }

  @Test
  public void testGetUnknowResourceReturnNotFound() {
    // @formatter:off
    given().
      accept(JsonApiHttpMessageConverter.APPLICATION_JSON_API_VALUE).
      contentType(JsonApiHttpMessageConverter.APPLICATION_JSON_API_VALUE).

    when().
      get(DummyUserController.RESOURCE + "/" + DummyUserController.RESOURCE_NOT_FOUND_ID).

    then().
      statusCode(404);
    // @formatter:on
  }
}
