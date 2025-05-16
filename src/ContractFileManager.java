import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Date;

public class ContractFileManager {
    //methods;

    public ArrayList<BusinessContract> load() {
        //heterogeneous has more than one kind of contract
        ArrayList<BusinessContract> list = new ArrayList<>();

        //loop through lines
        String line = ""; //read this
        BusinessContract bc = parseContract(line);
        list.add(bc);
        //end loop

        return list;
    } //read

    public void save(ArrayList<BusinessContract> list) {
        for (BusinessContract c : list) {
            System.out.println("SAVING: " + c);
            String line = getContractString(c);
            System.out.println(line);
            //TODO WRITE TO FILE
        }
    } //write

    BusinessContract parseContract(String line) {
        int MIN_LENGTH = 16; // length of the smaller of the two record types.

        int CONTRACT_TYPE = 0; // LEASE|
        int CONTRACT_DATE = 1; // 20210928|
        int CUSTOMER_NAME = 2; // Zachary Westly|
        int CUSTOMER_EMAIL = 3; // zach@texas.com|

        /* ** VEHICLE ** */
        int VIN = 4;// 37846|
        int YEAR = 5;// 2021|
        int MAKE = 6;// Chevrolet|
        int MODEL = 7;// Silverado|
        int TYPE = 8;// truck|
        int COLOR = 9;// Black|
        int MILES = 10;// 2750|
        int PRICE = 11;// 31995.00|

        /* LEASE VARIATION */
        int EXPECTED_ENDING_VALUE = 12; // 15997.50|
        int LEASE_FEE = 13; // 2239.65|
        int COMBINED = 14; // 18337.15|
        int MONTHLY_LEASE = 15; // 541.3 //Rounded down? Math.floor()

        /* SALES VARIATION */
        int SALES_TAX_AMOUNT = 12; //49.75|
        int RECORDING_FEE = 13;// 100.00|
        int PROCESSING_FEE = 14; //processingFee 295.00|
        int TOTAL_AMOUNT = 15; // 1439.75| 11 12 13 14 together
        int IS_FINANCED = 16; //NO|
        int MONTHLY_PAYMENT = 17; //0.00

        /* BEGIN PARSING */
        String[] parts = line.split("\\|");

        if (parts.length < MIN_LENGTH) {
            System.out.println("ERROR IN LINE:" + line);
            return null;
        }

        Vehicle vehicle = new Vehicle(
                Integer.parseInt(parts[VIN]),
                Integer.parseInt(parts[YEAR]),
                parts[MAKE],
                parts[MODEL],
                parts[TYPE],
                parts[COLOR],
                Integer.parseInt(parts[MILES]),
                Double.parseDouble(parts[PRICE])
        );

        String contractType = parts[CONTRACT_TYPE];
        System.out.println(contractType);

        Date date = new Date();
        try {
            LocalDate ld = LocalDate.parse(parts[CONTRACT_DATE], DateTimeFormatter.ofPattern("yyyyMMdd"));
            date = java.util.Date.from(ld.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
        } catch (DateTimeParseException e) {
            System.out.println("Error parsing date: " + e.getMessage());
        }

        String customerName = parts[CUSTOMER_NAME];
        String customerEmail = parts[CUSTOMER_EMAIL];

        boolean isSold = true;

        if(contractType.equalsIgnoreCase("SALE")) {
            //SALES SPECIFIC
            double totalPrice = Double.parseDouble(parts[TOTAL_AMOUNT]);
            double salesTaxAmount = Double.parseDouble(parts[SALES_TAX_AMOUNT]);
            double recordingFee = Double.parseDouble(parts[RECORDING_FEE]);
            double processingFee = Double.parseDouble(parts[PROCESSING_FEE]);
            boolean isFinanced = parts[IS_FINANCED].equalsIgnoreCase("YES");

            return new SalesContract(
                    vehicle,
                    date,
                    customerName,
                    customerEmail,
                    isSold,
                    totalPrice,
                    salesTaxAmount,
                    recordingFee,
                    processingFee,
                    isFinanced,
                    0
            );
        } else if(contractType.equalsIgnoreCase("LEASE")) {
            //LEASE SPECIFIC
            double expectedValue = Double.parseDouble(parts[EXPECTED_ENDING_VALUE]);
            double fee = Double.parseDouble(parts[LEASE_FEE]);
            double combined = Double.parseDouble(parts[COMBINED]);
            double monthly = Double.parseDouble(parts[MONTHLY_LEASE]);

            return new LeaseContract(
                    vehicle,
                    date,
                    customerName,
                    customerEmail,
                    isSold,
                    combined,
                    expectedValue,
                    fee,
                    monthly
            );
        }

        return null;
    }

    String getContractString(BusinessContract bc) {
        //TODO Glue with PIPES ||||
        return "";
    }

    public static void main(String[] args) {
        // test
        ContractFileManager cfm = new ContractFileManager();

        BusinessContract bc1 = cfm.parseContract("SALE|20210928|Dana Wyatt|dana@texas.com|10112|1993|Ford|Explorer|SUV|Red|525123|995.00|49.75|100.00|295.00|1439.75|NO|0.00");
        System.out.println(bc1);

        BusinessContract bc2 = cfm.parseContract("LEASE|20210928|Zachary Westly|zach@texas.com|37846|2021|Chevrolet|Silverado|truck|Black|2750|31995.00|15997.50|2239.65|18337.15|541.3");
        System.out.println(bc2);

//        ArrayList<BusinessContract> list = cfm.load();
//        for(BusinessContract bc : list){
//            System.out.println(bc);
//        }
    }
}
