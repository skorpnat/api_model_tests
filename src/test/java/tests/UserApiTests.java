package tests;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.ResponseSpecification;
import models.Response;
import models.UserSingleBodyPostModel;
import models.UserSinglePostResponseModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static specs.UserSpecs.*;

public class UserApiTests {

    @Test
    @DisplayName("Получение данных на одного пользователя из раздела data")
    public void testGetSingleUserData() {

        Response response = step("GET запрос из раздела data про пользователя", () ->
                given(userRequestSpec)
                        .when()
                        .get("2")
                        .then()
                        .spec(successfulResponse)
                        .extract().as(Response.class));

        step("Проверка полученных значений пользователя", () -> {
            assertThat(response.getData().getId()).isEqualTo(2);
            assertThat(response.getData().getEmail()).isEqualTo("janet.weaver@reqres.in");
            assertThat(response.getData().getFirstName()).isEqualTo("Janet");
            assertThat(response.getData().getLastName()).isEqualTo("Weaver");
        });
    }

    @Test
    @DisplayName("Получение данных на одного пользователя из раздела support")
    public void testGetUserSupportData() {

        Response responseSupport = step("GET запрос на данные из секции support пользователя", () ->
                given(userRequestSpec)
                        .when()
                        .get("3")
                        .then()
                        .spec(successfulResponse)
                        .extract().as(Response.class));
        step("Проверка полученных значений пользователя", () -> {
            assertThat(responseSupport.getData().getId()).isEqualTo(3);
            assertThat(responseSupport.getSupport().getText()).isEqualTo("To keep ReqRes free, contributions towards server costs are appreciated!");
            assertThat(responseSupport.getSupport().getUrl()).isEqualTo("https://reqres.in/#support-heading");
        });
    }

    @Test
    @DisplayName("Запрос данных на не существующего пользователя")
    public void testGetNotExistingUser() {
        step("GET запрос данных не существующего пользователя", () -> {
            given(userRequestSpec)
                    .when()
                    .get("15")
                    .then()
                    .spec(notFoundResponse);
        });
    }

    @Test
    @DisplayName("Создание нового пользователя")
    public void testPostNewUser() {

        UserSingleBodyPostModel singleUserBody = new UserSingleBodyPostModel();
        singleUserBody.setName("morpheus");
        singleUserBody.setJob("leader");

        UserSinglePostResponseModel response = step("POST запрос для создания нового пользователя", () ->
                given().spec(userRequestSpec)
                        .body(singleUserBody)
                        .when()
                        .post("")
                        .then()
                        .spec(createdResponse)
                        .extract().as(UserSinglePostResponseModel.class));

        step("Проверка параметром созданного успешно пользователя", () -> {
            assertThat(response.getName()).isEqualTo("morpheus");
            assertThat(response.getJob()).isEqualTo("leader");
            assertThat(response.getId()).isNotNull();
            assertThat(response.getCreatedAt()).isNotNull();
        });
    }

    @Test
    @DisplayName("Удаление пользователя")
    public void testDeleteUser() {
        step("DELETE запрос на удаление пользователя и проверка кода ответа", () -> {
            given(userRequestSpec)
                    .delete("2")
                    .then()
                    .spec(successfulDeleteResponse);
        });
    }

}