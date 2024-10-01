package tests;


import io.qameta.allure.Owner;
import io.qameta.allure.Severity;
import models.CreateUserBodyModel;
import models.CreateUserResponseModel;
import models.UpdateUserBodyModel;
import models.UpdateUserResponseModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static io.qameta.allure.Allure.step;
import static io.qameta.allure.SeverityLevel.BLOCKER;
import static io.qameta.allure.SeverityLevel.NORMAL;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static specs.ReqresSpecs.*;

@Owner("AlyonaCh")
@Tag("Api")
public class ReqresInTest extends TestBase {

    @Test
    @DisplayName("проверка массива на null")
    @Tag("regression")
    @Severity(NORMAL)
    void listDataNotEmptyTest() {
        step("запрос на получение данных", () ->
                given(requestRegres)
                        .get("/unknown")
                        .then()
                        .spec(responseStatus200)
                        .body("data", is(notNullValue())));
    }

    @Test
    @DisplayName("проверка значения id в массиве")
    @Tag("smoke")
    @Severity(NORMAL)
    void listDataIdTest() {
        step("запрос данных", () ->
                given(requestRegres)
                        .get("/unknown")
                        .then()
                        .spec(responseStatus200))
                .body("data[0].id", is(1));
    }

    @Test
    @DisplayName("негативный тест для проверки 415 статуса")
    @Tag("regression")
    @Severity(BLOCKER)
    void unSuccessfulCreate415Test() {
        step("не полный запрос", () ->
                given(requestRegres415)
                        .post("/users")
                        .then()
                        .spec(responseStatus415));
    }

    @Test
    @DisplayName("создание пользователя")
    @Tag("regression")
    @Severity(NORMAL)
    void successfulCreateUserTest() {

        CreateUserBodyModel authData = new CreateUserBodyModel();
        authData.setName("morpheus");
        authData.setJob("leader");

        CreateUserResponseModel response =
                step("отправка запроса на создание пользователя", () ->
                        given(requestRegres)
                                .body(authData)
                                .when()
                                .post("/users")
                                .then()
                                .spec(responseCreateUserSpec)
                                .extract().as(CreateUserResponseModel.class));
        step("проверка имени пользователя", () ->
                assertThat(response.getName()).isEqualTo("morpheus"));
        step("проверка должности пользователя", () ->
                assertThat(response.getJob()).isEqualTo("leader"));
    }

    @Test
    @DisplayName("негативный текст получение данных пользователя")
    @Tag("smoke")
    @Severity(BLOCKER)
    void singleUserNotFoundTest() {
        step("запрос на не зарегистрированного пользователя", () ->
                given(requestRegres)
                        .get("/users/23")
                        .then()
                        .spec(responseStatus404));
    }

    @Test
    @DisplayName("запрос на удаление пользователя")
    @Tag("smoke")
    @Severity(BLOCKER)
    void deleteUserLoginTest() {
        step("запрос на удаление пользователя", () ->
                given(requestRegres)
                        .delete("/users/2")
                        .then()
                        .spec(responseStatus204));
    }

    @Test
    @DisplayName("запрос на изменения поля job")
    @Tag("regression")
    @Severity(NORMAL)
    void updateUserdataTest() {

        UpdateUserBodyModel userData = new UpdateUserBodyModel();
        userData.setName("morpheus");
        userData.setJob("zion resident");

        UpdateUserResponseModel updateUserResponse =
                step("отправка запроса на изменения поля job", () ->
                        given(requestRegres)
                                .body(userData)
                                .when()
                                .put("/users/2")
                                .then()
                                .spec(responseStatus200)
                                .extract().as(UpdateUserResponseModel.class));
        step("проверка имени пользователя", () ->
                assertThat(updateUserResponse.getName()).isEqualTo("morpheus"));
        step("проверка должности пользователя", () ->
                assertThat(updateUserResponse.getJob()).isEqualTo("zion resident"));
    }
}