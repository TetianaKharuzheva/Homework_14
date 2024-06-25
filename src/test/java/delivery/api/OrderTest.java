package delivery.api;

import delivery.dto.OrderDto;
import delivery.utils.ApiClient;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;

import static delivery.spec.Specifications.getAuthenticatedRequestSpecification;

public class OrderTest extends BaseSetupApi {


    //Lesson_15
    @Test
    void getOrderInformationAndCheckResponse() {
        Response response = ApiClient.getOrders(getAuthenticatedRequestSpecification(bearerToken));
        OrderDto[] responseArray = ApiClient.getOrdersAsArray(getAuthenticatedRequestSpecification(bearerToken));

        System.out.println("Orders we have before: ");
        for (int loopIndex = 0; loopIndex < responseArray.length; loopIndex++) {
            System.out.println("Iteration # " + loopIndex);
            System.out.println("Order id: " + responseArray[loopIndex].id);
        }

        //precondition we would like to delete all orders
        System.out.println("Delete all orders ");
        for (int loopIndex = 0; loopIndex < responseArray.length; loopIndex++) {
            System.out.println("Iteration # " + loopIndex);
            System.out.println("Delete order with id" + responseArray[loopIndex].id);
            ApiClient.deleteOrder(getAuthenticatedRequestSpecification(bearerToken), String.valueOf(responseArray[loopIndex].id));
        }

        //Creating new orders
        ApiClient.createOrder(getAuthenticatedRequestSpecification(bearerToken));
        ApiClient.createOrder(getAuthenticatedRequestSpecification(bearerToken));
        ApiClient.createOrder(getAuthenticatedRequestSpecification(bearerToken));
        ApiClient.createOrder(getAuthenticatedRequestSpecification(bearerToken));
        ApiClient.createOrder(getAuthenticatedRequestSpecification(bearerToken));

        //Calling all new orders
        OrderDto[] checkCreatedOrdersAfterArrayDeletion = ApiClient.getOrdersAsArray(getAuthenticatedRequestSpecification(bearerToken));
        softly.assertThat(checkCreatedOrdersAfterArrayDeletion.length).isEqualTo(5);


//        softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.SC_OK);
//        softly.assertThat(response.getContentType()).isEqualTo(ContentType.JSON.toString());

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
        softly.assertThat(response.getBody().asString()).isBlank();
        softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.SC_OK);
    }
}
