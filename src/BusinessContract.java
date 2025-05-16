import java.util.Date;

// abstract - CANT CREATE *NEW* INSTANCES DIRECTLY, ONLY OF ITS DESCENDANTS
public abstract class BusinessContract {
    protected Date date;
    protected String customerName;
    protected String customerEmail;
    protected boolean isSold;
    protected double totalPrice;
    protected double monthlyPayment;
    protected Vehicle vehicle;

    BusinessContract(
            Vehicle vehicle,
            Date date,
            String customerName,
            String customerEmail,
            boolean isSold
    ) {
        this.vehicle = vehicle;
        this.date = date;
        this.customerName = customerName;
        this.customerEmail = customerEmail;
        this.isSold = isSold;
        this.totalPrice = getTotalPrice();
        this.monthlyPayment = getMonthlyPayment();
    }

    public String toString() {
        return String.format("""
                        Date:    %s
                        Name:    %s
                        Email:   %s
                        Sold:    %s
                        Total:   %.2f
                        Monthly: %.2f
                        """,
                this.date,
                this.customerName,
                this.customerEmail,
                this.isSold ? "YES" : "NO",
                this.totalPrice,
                this.monthlyPayment
        );
    }

    public Date getDate() {
        return this.date;
    }

    public String getCustomerName() {
        return this.customerName;
    }

    public String getCustomerEmail() {
        return this.customerEmail;
    }

    public Vehicle getVehicle() {
        return this.vehicle;
    }

    public boolean isSold() {
        return this.isSold;
    }

    abstract double getTotalPrice();

    abstract double getMonthlyPayment();

    protected void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

//    public static void main(String[] args) {
//        //TEST FOR THIS CLASS ONLY
//        BusinessContract bc = new BusinessContract(
//                new Date(),
//                "Kevin Ernest Long",
//                "kevinelong@gmail.com",
//                true,
//                10000.00,
//                500.00
//        );
//        System.out.println(bc);
//    }
}
