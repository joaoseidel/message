package io.joaoseidel.message.adapter.web;

import io.restassured.RestAssured;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;

import java.time.LocalDateTime;
import java.util.UUID;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MessageControllerTest {

  @LocalServerPort private int port;

  @Autowired private JdbcTemplate jdbcTemplate;

  @BeforeEach
  void setUp() {
    RestAssured.port = port;
    RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
  }

  @Test
  void givenAMessage_whenPost_thenReturnCreated() throws JSONException {
    RestAssured.given()
        .contentType(APPLICATION_JSON_VALUE)
        .body(
            new JSONObject()
                .put("target", "me@joaoseidel.io")
                .put("body", "Hello, World!")
                .put("type", "EMAIL")
                .put("scheduleDate", LocalDateTime.now().plusMinutes(2))
                .toString())
        .when()
        .post("/v1/messages")
        .then()
        .statusCode(HttpStatus.CREATED.value())
        .body(matchesJsonSchemaInClasspath("message_schema.json"));
  }

  @Test
  void givenAMessageWithoutTarget_whenPost_thenReturnBadRequest() throws JSONException {
    RestAssured.given()
        .contentType(APPLICATION_JSON_VALUE)
        .body(
            new JSONObject()
                .put("body", "Hello, World!")
                .put("type", "EMAIL")
                .put("scheduleDate", LocalDateTime.now().plusMinutes(2))
                .toString())
        .when()
        .post("/v1/messages")
        .then()
        .statusCode(HttpStatus.BAD_REQUEST.value())
        .body("message", equalTo("target: must not be blank"));
  }

  @Test
  void givenAMessageWithoutBody_whenPost_thenReturnBadRequest() throws JSONException {
    RestAssured.given()
        .contentType(APPLICATION_JSON_VALUE)
        .body(
            new JSONObject()
                .put("target", "me@joaoseidel.io")
                .put("type", "EMAIL")
                .put("scheduleDate", LocalDateTime.now().plusMinutes(2))
                .toString())
        .when()
        .post("/v1/messages")
        .then()
        .statusCode(HttpStatus.BAD_REQUEST.value())
        .body("message", equalTo("body: must not be blank"));
  }

  @Test
  void givenAMessageWithoutType_whenPost_thenReturnBadRequest() throws JSONException {
    RestAssured.given()
        .contentType(APPLICATION_JSON_VALUE)
        .body(
            new JSONObject()
                .put("target", "me@joaoseidel.io")
                .put("body", "Hello, World!")
                .put("scheduleDate", LocalDateTime.now().plusMinutes(2))
                .toString())
        .when()
        .post("/v1/messages")
        .then()
        .statusCode(HttpStatus.BAD_REQUEST.value())
        .body("message", equalTo("type: must not be null"));
  }

  @Test
  void givenAMessageWithoutScheduleDate_whenPost_thenReturnBadRequest() throws JSONException {
    RestAssured.given()
        .contentType(APPLICATION_JSON_VALUE)
        .body(
            new JSONObject()
                .put("target", "me@joaoseidel.io")
                .put("body", "Hello, World!")
                .put("type", "EMAIL")
                .toString())
        .when()
        .post("/v1/messages")
        .then()
        .statusCode(HttpStatus.BAD_REQUEST.value())
        .body("message", equalTo("scheduleDate: must not be null"));
  }

  @Test
  void givenAMessageWithoutAValidType_whenPost_thenReturnBadRequest() throws JSONException {
    RestAssured.given()
        .contentType(APPLICATION_JSON_VALUE)
        .body(
            new JSONObject()
                .put("target", "me@joaoseidel.io")
                .put("body", "Hello, World!")
                .put("scheduleDate", LocalDateTime.now().plusMinutes(2))
                .put("type", "invalid-type")
                .toString())
        .when()
        .post("/v1/messages")
        .then()
        .statusCode(HttpStatus.BAD_REQUEST.value())
        .body("message", equalTo("type: Invalid enum type."));
  }

  @Test
  void givenAMessageWithInvalidScheduleDate_whenPost_thenReturnBadRequest() throws JSONException {
    RestAssured.given()
        .contentType(APPLICATION_JSON_VALUE)
        .body(
            new JSONObject()
                .put("target", "me@joaoseidel.io")
                .put("body", "Hello, World!")
                .put("scheduleDate", LocalDateTime.now().minusMinutes(2))
                .put("type", "EMAIL")
                .toString())
        .when()
        .post("/v1/messages")
        .then()
        .statusCode(HttpStatus.BAD_REQUEST.value())
        .body("message", equalTo("Message does not have a valid schedule date!"));
  }

  @Test
  void givenAMessageCode_whenGet_thenReturnMessage() throws JSONException {
    var code =
        RestAssured.given()
            .contentType(APPLICATION_JSON_VALUE)
            .body(
                new JSONObject()
                    .put("target", "me@joaoseidel.io")
                    .put("body", "Hello, World!")
                    .put("type", "EMAIL")
                    .put("scheduleDate", LocalDateTime.now().plusMinutes(2))
                    .toString())
            .when()
            .post("/v1/messages")
            .then()
            .statusCode(HttpStatus.CREATED.value())
            .extract()
            .jsonPath()
            .getString("id");

    RestAssured.given()
        .pathParam("code", code)
        .when()
        .get("/v1/messages/{code}")
        .then()
        .statusCode(HttpStatus.OK.value())
        .body("id", equalTo(code))
        .body("target", equalTo("me@joaoseidel.io"))
        .body("body", equalTo("Hello, World!"))
        .body("type", equalTo("EMAIL"))
        .body("scheduleDate", notNullValue());
  }

  @Test
  void givenAMessageWithInvalidCodeFormat_whenGet_thenReturnBadRequest() {
    RestAssured.given()
        .pathParam("code", "invalid-format")
        .get("/v1/messages/{code}")
        .then()
        .statusCode(HttpStatus.BAD_REQUEST.value())
        .body("message", equalTo("code: Invalid code format."));
  }

  @Test
  void givenAnInvalidMessageCode_whenGet_thenReturnNotFound() {
    var code = UUID.randomUUID().toString();
    RestAssured.given()
        .pathParam("code", code)
        .get("/v1/messages/{code}", code)
        .then()
        .statusCode(HttpStatus.NOT_FOUND.value())
        .body("message", equalTo(String.format("Message with ID '%s' does not exist!", code)));
  }

  @Test
  void givenAMessageCode_whenDelete_thenReturnNoContent() throws JSONException {
    var code =
        RestAssured.given()
            .contentType(APPLICATION_JSON_VALUE)
            .body(
                new JSONObject()
                    .put("target", "me@joaoseidel.io")
                    .put("body", "Hello, World!")
                    .put("type", "EMAIL")
                    .put("scheduleDate", LocalDateTime.now().plusMinutes(2))
                    .toString())
            .when()
            .post("/v1/messages")
            .then()
            .statusCode(HttpStatus.CREATED.value())
            .extract()
            .jsonPath()
            .getString("id");

    RestAssured.given()
        .pathParam("code", code)
        .delete("/v1/messages/{code}")
        .then()
        .statusCode(HttpStatus.NO_CONTENT.value());
  }

  @Test
  void givenAnInvalidMessageCode_whenDelete_thenReturnBadRequest() {
    var code = UUID.randomUUID().toString();
    RestAssured.given()
        .pathParam("code", code)
        .delete("/v1/messages/{code}")
        .then()
        .statusCode(HttpStatus.NOT_FOUND.value())
        .body("message", equalTo(String.format("Message with ID '%s' does not exist!", code)));
  }

  @Test
  void givenAMessageWithInvalidCodeFormat_whenDelete_thenReturnBadRequest() {
    RestAssured.given()
        .pathParam("code", "invalid-format")
        .delete("/v1/messages/{code}")
        .then()
        .statusCode(HttpStatus.BAD_REQUEST.value())
        .body("message", equalTo("code: Invalid code format."));
  }

  @Test
  void givenAMessageThatWasAlreadySent_whenDelete_thenReturnBadRequest() throws JSONException {
    var code =
        RestAssured.given()
            .contentType(APPLICATION_JSON_VALUE)
            .body(
                new JSONObject()
                    .put("target", "me@joaoseidel.io")
                    .put("body", "Hello, World!")
                    .put("type", "EMAIL")
                    .put("scheduleDate", LocalDateTime.now().plusMinutes(2))
                    .toString())
            .when()
            .post("/v1/messages")
            .then()
            .statusCode(HttpStatus.CREATED.value())
            .extract()
            .jsonPath()
            .getString("id");

    jdbcTemplate.update("UPDATE message SET delivered = 'true' WHERE ID=?", code);

    RestAssured.given()
        .pathParam("code", code)
        .delete("/v1/messages/{code}", code)
        .then()
        .statusCode(HttpStatus.BAD_REQUEST.value())
        .body("message", equalTo(String.format("The message with ID %s was already sent!", code)));
  }
}
