package delivery.api;

import delivery.utils.ApiClient;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;

import static delivery.spec.Specifications.getAuthenticatedRequestSpecification;

public class OrderTest extends BaseSetupApi {

    @Test
    void getOrderInformationAndCheckResponse() {

        Response response = ApiClient.getOrders(getAuthenticatedRequestSpecification(bearerToken));

        softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.SC_OK);
        softly.assertThat(response.getContentType()).isEqualTo(ContentType.JSON.toString());

    }

    @Test
    void createOrderAndCheckResponse() {

        Response response = ApiClient.createOrder(getAuthenticatedRequestSpecification(bearerToken));

        softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.SC_OK);
        softly.assertThat(response.getContentType()).isEqualTo(ContentType.JSON.toString());
        softly.assertThat(response.getBody().jsonPath().getString("id")).isNotEmpty();
        softly.assertThat(response.getBody().jsonPath().getString("status")).isEqualTo("OPEN");
        softly.assertThat(response.getBody().jsonPath().getString("customerName")).isNotEmpty();
    }

    // Homework_14
    @Test
    void deleteOrderAndCheckStatusCode() {
        Response responseCreateOrder = ApiClient.createOrder(getAuthenticatedRequestSpecification(bearerToken));
        String orderId = responseCreateOrder.getBody().jsonPath().getString("id");

        ApiClient.deleteOrder(getAuthenticatedRequestSpecification(bearerToken), orderId);
        Response response = ApiClient.getOrdersById(getAuthenticatedRequestSpecification(bearerToken), orderId);

        softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.SC_OK);
    }
}
