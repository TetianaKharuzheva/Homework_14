package delivery.dto;

public class OrderDto {
    String status;
    int courierID;
    String customerName;
    String customerPhone;
    String comment;
    long id;

    public OrderDto(String customerName, String customerPhone, String comment) {
        this.status = "OPEN";
        this.courierID = 0;
        this.customerName = customerName;
        this.customerPhone = customerPhone;
        this.comment = comment;
        this.id = 0;
    }
}
