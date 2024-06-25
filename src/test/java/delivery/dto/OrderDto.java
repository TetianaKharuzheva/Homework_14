package delivery.dto;

public class OrderDto {
    public String status;
    public int courierId;
    public String customerName;
    public String customerPhone;
    public String comment;
    public long id;

    public OrderDto(String customerName, String customerPhone, String comment) {
        this.status = "OPEN";
        this.courierId = 0;
        this.customerName = customerName;
        this.customerPhone = customerPhone;
        this.comment = comment;
        this.id = 0;
    }

    public OrderDto() {
        this.status = "OPEN";
        this.id = 0;
        this.courierId = 0;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public void setCustomerPhone(String customerPhone) {

        this.customerPhone = customerPhone;
    }

    public void setComment(String comment) {

        this.comment = comment;
    }
}
