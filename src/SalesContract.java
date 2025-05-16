import java.util.Date;

public class SalesContract extends BusinessContract {
    private double salesTaxAmount = 0.05;
    private double recordingFee = 100.00;
    private double processingFee = 295.00;
    private boolean isFinanced = true; //the NO LOAN OPTION?
    SalesContract(
            /* ******** BASE ********* */
            Vehicle vehicle,
            Date date,
            String customerName,
            String customerEmail,
            boolean isSold,
            double totalPrice,
            /* ******** NEW ********* */
            double salesTaxAmount,
            double recordingFee,
            double processingFee,
            boolean isFinanced,
            double monthlyPayment
    ){
        super(vehicle, date, customerName, customerEmail, isSold, totalPrice, monthlyPayment);
        this.salesTaxAmount = salesTaxAmount;
        this.recordingFee = recordingFee;
        this.processingFee = processingFee;
        this.isFinanced = isFinanced;
    }

    public SalesContract(Date date, Vehicle vehicle, String customerName, String customerEmail, double monthlyPayment) {
        super(vehicle, date, customerName, customerEmail, false, 0, monthlyPayment);
        double price = vehicle.getPrice();

        // Sales Tax Amount (5%)
        double tax = 0.05 * price;

        //• Recording Fee ($100)
        double fee = 100;

        //• Processing fee ($295 for vehicles under $10,000 and $495 for all others
        double processingFee = price < 10000 ? 295.00 : 495.00;

        //TOTAL PRICE
        double totalPrice = price + tax + fee + processingFee;

        this.setTotalPrice(totalPrice);
    }


    public String toString(){
        return super.toString() + String.format("""
                Sales Tax Amount: %.2f
                Recording Fee:    %.2f
                Processing Fee:   %.2f
                Is Financed:      %s
                """,
                this.salesTaxAmount,
                this.recordingFee,
                this.processingFee,
                this.isFinanced ? "YES" : "NO"
        );
    }
    public double getSalesTaxAmount(){
        return this.salesTaxAmount;
    }
    public double getRecordingFee(){
        return this.recordingFee;
    }
    public double getProcessingFee(){
        return this.processingFee;
    }
    public boolean isFinanced(){
        return this.isFinanced;
    }

    /* Monthly payment (if financed) based on:
        • All loans are at 4.25% for 48 months if the price is $10,000 or more
        • Otherwise they are at 5.25% for 24 month
    */
    public double getMonthlyPayment(){
        return 0.0; //STUB
    }

    /* It is possible that getMonthlyPayment() would return
        0 if they chose the NO loan option.
     */
    public double getTotalPrice(){
        return 0.0; //STUB
    }
}

