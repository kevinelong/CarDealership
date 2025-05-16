import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;

class UserInterface{
    private Dealership dealership;
    private BufferedReader in;
    private DealershipFileManager manager;
    private ContractList contractList;

    UserInterface() {
        this.init();
    }

    private void init(){
        this.in = new BufferedReader(new InputStreamReader(System.in));
        this.manager = new DealershipFileManager();
        this.dealership = this.manager.getDealership();
//        this.contractManager = new ContractFileManager();
        this.contractList = new ContractList(); //this.contractManager.getContractList();
    }

    void display() {
        String command = "";
        while (!command.equalsIgnoreCase("x")) {
            System.out.println("""
                    MAIN MENU:
                        Search By:
                            a) all
                            p) price
                            m) make and model
                            y) year
                            c) color
                            o) odometer miles
                            t) type (e.g. sedan)

                        Vehicle: 
                            +) add
                            -) remove
                        
                        Contracts:
                            s) add sales
                            l) add lease
                            
                        x) e(x)it
                    """);
            command = null;
            try {
                command = in.readLine().toLowerCase().trim();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            switch (command) {
                case "a" -> processGetAllVehiclesRequest();
                case "p" -> processGetByPriceRequest();
                case "m" -> processGetByMakeModelRequest();
                case "y" -> processGetByYearRequest();
                case "c" -> processGetByColorRequest();
                case "o" -> processGetByMilesRequest();
                case "t" -> processGetByTypeRequest();
                case "+" -> processAddVehicleRequest();
                case "-" -> processRemoveVehiclesRequest();
                case "s" -> processSaleRequest();
                case "l" -> processLeaseRequest();
                case "x" -> System.out.println("Thanks!");
                default -> System.out.println("Invalid Command");
            }
        }
    }

    private void processRemoveVehiclesRequest() {
        int vin = getInt("VIN to Remove");
        Vehicle v = null;
        for(Vehicle candidate : dealership.getAllVehicles()){
            if(candidate.getVIN() == vin){
                v = candidate;
                break;
            }
        }
        if(v == null){
            System.out.println("No vehicle with VIN: " + vin);
            return;
        }
        this.dealership.removeVehicle(v);
    }

    private void processGetByTypeRequest() {
        var list = this.dealership.getVehiclesByType(
                getString("Vehicle Type e.g. Truck/Sedan/Roadster")
        );
        if(list.size() == 0){
            System.out.println("None Found.");
            return;
        }
        for(Vehicle v : list){
            System.out.println(v);
        }
    }

    private void processGetByColorRequest() {
        var list = this.dealership.getVehiclesByColor(
                getString("Color")
        );
        if(list.size() == 0){
            System.out.println("None Found.");
            return;
        }
        for(Vehicle v : list){
            System.out.println(v);
        }
    }

    private void processGetByMilesRequest() {
        var list = this.dealership.getVehiclesByMileage(
                getInt("Minimum"),
                getInt("Maximum")
        );
        if(list.size() == 0){
            System.out.println("None Found.");
            return;
        }
        for(Vehicle v : list){
            System.out.println(v);
        }
    }

    private void processSaleRequest() {
        Date date = new Date();
        String customerName = getString("Enter Customer Name");
        String customerEMail = getString("Enter Customer EMail");
        int vin = getInt("VIN");
        Vehicle vehicle = dealership.getVehicleByVIN(vin);
        double totalPrice = vehicle.getPrice();
//        All loans are at 4.25% for 48 months if the price is $10,000 or more
//• Otherwise they are at 5.25% for 24 month
        double rate = 0.0425 / 12;
        int payments = totalPrice >= 10000 ? 48 : 24;
        double monthlyPayment = totalPrice * (rate / (1 - Math.pow(1 + rate, -payments)));
        SalesContract sc = new SalesContract(date, vehicle, customerName, customerEMail, monthlyPayment);

        //DISPLAY CONTRACT
        System.out.println("TOTAL PRICE: " + sc.getTotalPrice());

        //CONFIRM CONTRACT
        System.out.println(sc);

        boolean confirm = getString("Continue (yes/no) :").equalsIgnoreCase("yes");
        if(!confirm){
            return;
        }
        this.contractList.addContract(sc);
    }
    private void processLeaseRequest() {
        Date date = new Date();
        String customerName = getString("Enter Customer Name");
        String customerEMail = getString("Enter Customer EMail");
        int vin = getInt("VIN");
        Vehicle vehicle = dealership.getVehicleByVIN(vin);

        double price = vehicle.getPrice();

        //Expected Ending Value (50% of the original price)
        double expectedEndingValue =  price / 2;

        //• Lease Fee (7% of the original price)
        double leaseFee = 0.07 * price;

        double totalPrice = expectedEndingValue + leaseFee;

        //• Monthly payment based on
        //• All leases are financed at 4.0% for 36 months
        int payments = 36;
        double rate = 0.04 / 12;
        double monthlyPayment = totalPrice * (rate / (1 - Math.pow(1 + rate, -payments)));

        LeaseContract sc = new LeaseContract(vehicle, date, customerName, customerEMail, false, totalPrice, expectedEndingValue, leaseFee, monthlyPayment);


        //DISPLAY CONTRACT
        System.out.println("TOTAL PRICE: " + sc.getTotalPrice());

        //CONFIRM CONTRACT
        System.out.println(sc);

        boolean confirm = getString("Continue (yes/no) :").equalsIgnoreCase("yes");
        if(!confirm){
            return;
        }
        this.contractList.addContract(sc);
    }
    private void processAddVehicleRequest() {
        //TODO Collect all info from user
        int vin = getInt("VIN");
        int year = getInt("Year");
        String make = getString("Make");
        String model = getString("Model");
        String vehicleType = getString("Vehicle (e.g. sedan):");
        String color = getString("Color");
        int odometer = getInt("Mileage");
        double price = getDouble("Price");
        var v = new Vehicle(
                vin,
                year,
                make,
                model,
                vehicleType,
                color,
                odometer,
                price
        );
        dealership.addVehicle(v);
    }

    void processGetAllVehiclesRequest(){
        var list = this.dealership.getAllVehicles();
        if(list.size() == 0){
            System.out.println("None Found.");
            return;
        }
        for(Vehicle v : list){
            System.out.println(v);
        }
    }
    int getInt(String name){
        System.out.printf("%s: ", name);
        Integer value = null;
        while(value==null) {
            try {
                value = Integer.parseInt(in.readLine().toLowerCase().trim().replace(",", ""));
            } catch (IOException e) {
                System.out.println("Try Again:");
            }
        }
        return value;
    }
    double getDouble(String name){
        System.out.printf("%s: ", name);
        Double value = null;
        while(value==null) {
            try {
                value = Double.parseDouble(in.readLine().toLowerCase().trim().replace(",", ""));
            } catch (IOException e) {
                System.out.println("Try Again:");
            }
        }
        return value;
    }
    String getString(String name){
        System.out.printf("%s: ", name);
        String value = null;
        while(value==null) {
            try {
                value = in.readLine();
            } catch (IOException e) {
                System.out.println("Try Again:");
                value = null;
                continue;
            }

            if(value.trim().isEmpty()){
                System.out.println("Try Again:");
                value = null;
            }
        }
        return value;
    }
    void processGetByPriceRequest(){
        var list = this.dealership.getVehiclesByPrice(
                getInt("Minimum"),
                getInt("Maximum")
        );

        if(list.size() == 0){
            System.out.println("None Found.");
        }

        for(Vehicle v : list){
            System.out.println(v);
        }
    }
    void processGetByYearRequest(){
        var list = this.dealership.getVehiclesByYear(
                getInt("Minimum Year"),
                getInt("Maximum Year")
        );

        if(list.size() == 0){
            System.out.println("None Found.");
        }

        for(Vehicle v : list){
            System.out.println(v);
        }
    }
    void processGetByMakeModelRequest(){
        var list = this.dealership.getVehiclesByMakeModel(
                getString("Make"),
                getString("Model")
        );

        if(list.size() == 0){
            System.out.println("None Found.");
        }

        for(Vehicle v : list){
            System.out.println(v);
        }
    }

}