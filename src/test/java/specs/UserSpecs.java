package specs;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.with;
import static helpers.CustomAllureListener.withCustomTemplates;
import static io.restassured.filter.log.LogDetail.*;

import io.restassured.specification.ResponseSpecification;

public class UserSpecs {
    public static RequestSpecification userRequestSpec = with()
            .filter(withCustomTemplates())
            .log().all()
            .contentType(ContentType.JSON)
            .baseUri("https://reqres.in/api/users/");

    public static ResponseSpecification successfulResponse = new ResponseSpecBuilder()
            .expectStatusCode(200)
            .log(STATUS)
            .log(BODY)
            .build();

    public static ResponseSpecification createdResponse = new ResponseSpecBuilder()
            .expectStatusCode(201)
            .log(ALL)
            .build();

    public static ResponseSpecification notFoundResponse = new ResponseSpecBuilder()
            .expectStatusCode(404)
            .log(ALL)
            .build();

    public static ResponseSpecification successfulDeleteResponse = new ResponseSpecBuilder()
            .expectStatusCode(204)
            .log(ALL)
            .build();
}
