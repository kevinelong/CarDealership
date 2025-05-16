import java.util.Date;

public class LeaseContract extends BusinessContract {
    private double expectedEndingValue;
    private double leaseFee;

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
        super(vehicle, date, customerName, customerEmail, isSold, totalPrice, monthlyPayment);
        this.expectedEndingValue = expectedEndingValue;
        this.leaseFee = leaseFee;
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
        return 0;
    }

    double getMonthlyPayment() {
        return 0;
    }
}

