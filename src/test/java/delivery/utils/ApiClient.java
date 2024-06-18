package delivery.utils;

import com.google.gson.Gson;
import delivery.api.BaseSetupApi;
import delivery.dto.LoginDto;
import delivery.dto.OrderDto;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.http.HttpStatus;

import static io.restassured.RestAssured.given;

public class ApiClient extends BaseSetupApi {
    public static Response getOrders(RequestSpecification authorizedSpecWithToken) {

        return given()
                .spec(authorizedSpecWithToken)
                .log()
                .all()
                .get("orders")
                .then()
                .log()
                .all()
                .extract()
                .response();
    }

    public static Response getOrdersById(RequestSpecification authorizedSpecWithToken, String orderId) {

        return given()
                .spec(authorizedSpecWithToken)
                .log()
                .all()
                .get("orders/" + orderId)
                .then()
                .log()
                .all()
                .extract()
                .response();
    }

    public static Response createOrder(RequestSpecification spec) {
        Gson gson = new Gson();
        OrderDto requestOrder = new OrderDto("Yura", "111222", "not comment");

        return given()
                .spec(spec)
                .log()
                .all()
                .body(gson.toJson(requestOrder))
                .post("orders")
                .then()
                .log()
                .all()
                .extract()
                .response();
    }

    public static void deleteOrder(RequestSpecification spec, String orderId) {
        given()
                .spec(spec)
                .log()
                .all()
                .delete("orders/" + orderId)
                .then()
                .log()
                .all()
                .statusCode(HttpStatus.SC_OK);
    }


    public static String authorizeAndGetToken(String username, String password) {

        return given()
                .log()
                .all()
                .contentType(ContentType.JSON)
                .body(new Gson().toJson(new LoginDto(username, password)))
                .post("login/student")
                .then()
                .log()
                .all()
                .extract()
                .response()
                .asString();
    }

}