import java.util.Date;

public class LeaseContract extends BusinessContract {
    protected double expectedEndingValue;
    protected double leaseFee;

    LeaseContract(
            /* ******** BASE ********* */
            Vehicle vehicle,
            Date date,
            String customerName,
            String customerEmail,
            boolean isSold,
            double totalPrice,
            /* ******** NEW ********* */
            double expectedEndingValue,
            double leaseFee,
            double monthlyPayment
    ) {
        super(vehicle, date, customerName, customerEmail, true);
        this.totalPrice = totalPrice;
        this.expectedEndingValue = expectedEndingValue;
        this.leaseFee = leaseFee;
        this.monthlyPayment = monthlyPayment;
    }
    LeaseContract(
            /* ******** BASE ********* */
            Vehicle vehicle,
            Date date,
            String customerName,
            String customerEmail
    ) {
        super(vehicle, date, customerName, customerEmail, true);
        this.setTotalPrice( this.getTotalPrice());
        double originalPrice = vehicle.getPrice();
        this.expectedEndingValue = originalPrice / 2;
        this.leaseFee = originalPrice * 0.07;
        this.monthlyPayment = getMonthlyPayment();
    }
    public double getExpectedEndingValue(){
        return expectedEndingValue;
    }
    public double getLeaseFee(){
        return leaseFee;
    }
    /*
    A LeaseContract will include the following additional information:
• Expected Ending Value (50% of the original price)
• Lease Fee (7% of the original price)
• Monthly payment based on
• All leases are financed at 4.0% for 36 months
     */

    public String toString() {
        return super.toString() + String.format("""
                        Expected Ending Value: %.2f
                        Lease Fee:    %.2f
                        """,
                this.expectedEndingValue,
                this.leaseFee
        );
    }

    /*
A LeaseContract will include the following additional information:
• Expected Ending Value (50% of the original price)
• Lease Fee (7% of the original price)
• Monthly payment based on
• All leases are financed at 4.0% for 36 months
Methods will include a constructor and getters and setters for all fields except
total price and monthly payment. You should provide overrides for
getTotalPrice() and getMonthlyPayment() that will return computed
values based on the rules above
 */
    double getTotalPrice() {
        double price = vehicle.getPrice();

        //Expected Ending Value (50% of the original price)
        double expectedEndingValue =  price / 2;

        //• Lease Fee (7% of the original price)
        double leaseFee = 0.07 * price;

        return expectedEndingValue + leaseFee;
    }

    double getMonthlyPayment() {
        //• Monthly payment based on
        //• All leases are financed at 4.0% for 36 months
        int payments = 36;
        double rate = 0.04 / 12;
        return totalPrice * (rate / (1 - Math.pow(1 + rate, -payments)));
    }
}

