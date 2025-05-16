
public class Vehicle {
    private int vin; //NOT A STRING?
    private int year;
    private String make;
    private String model;
    private String vehicleType;
    private String color;
    private int odometer;
    private double price;

    Vehicle(int vin, int year, String make, String model, String vehicleType, String color, int odometer, double price) {
        this.vin = vin;
        this.year = year;
        this.make = make;
        this.model = model;
        this.vehicleType = vehicleType;
        this.color = color;
        this.odometer = odometer;
        this.price = price;
    }

    Vehicle(int vin) { //OVERLOADED CONSTRUCTOR
        this.vin = vin; //assign value from parameter to property
    }

    public String toString() {
        return String.format("""
                VIN: '%d'
                YEAR: '%d'
                MAKE: '%s'
                MODEL: '%s'
                COLOR: '%s'
                TYPE: '%s'
                PRICE: '$%.2f'
                MILES: '%d'
                """,
                this.vin, this.year, this.make, this.model, this.color, this.vehicleType, this.price, this.odometer);
    }

    public int getVIN() {
        return this.vin;
    }

    public int getYear() {
        return this.year;
    }

    public String getMake() {
        return this.make;
    }

    public String getModel() {
        return this.model;
    }

    public String getType() {
        return this.vehicleType;
    }

    public String getColor() {
        return this.color;
    }

    public int getMiles() {
        return this.odometer;
    }

    public double getPrice() {
        return this.price;
    }

//    public static void main(String[] args) {
//        Vehicle v = new Vehicle(123, 1999, "MAZDA", "MX-5", "ROADSTER", "WHITE", 128_000, 5_000);
//        Vehicle v2 = new Vehicle(456, 2008, "HYUNDAI", "ELANTRA GT", "ROADSTER", "BLANK", 111_000, 10_000);
//        System.out.println(v);
//        System.out.println(v2);
//    }
}