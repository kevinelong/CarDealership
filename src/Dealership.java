import java.util.ArrayList;
import java.util.List;

class Dealership {
    private String name;
    private String address;
    private String phone;
    private ArrayList<Vehicle> inventory;

    //CONSTRUCTOR
    Dealership(String name, String address, String phone) {
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.inventory = new ArrayList<Vehicle>();
    }

    public String toString() {
        return String.format(
                "DEALERSHIP NAME: '%s' #CARS: %d\n",
                this.name, this.inventory.size()
        );
    }

    //METHODS
    public Vehicle getVehicleByVIN(int vin) {
        for (Vehicle v : inventory) {
            if (v.getVIN() == vin) {
                return v;
            }
        }
        return null;
    }

    public List<Vehicle> getVehiclesByPrice(double min, double max) {
        ArrayList<Vehicle> matches = new ArrayList<>();
        for (Vehicle v : inventory) {
            if (v.getPrice() >= min && v.getPrice() <= max) {
                matches.add(v);
            }
        }
        return matches;
    }

    public List<Vehicle> getVehiclesByMakeModel(String make, String model) {
        ArrayList<Vehicle> matches = new ArrayList<>();
        for (Vehicle v : inventory) {
            if (v.getMake().equalsIgnoreCase(make) && v.getModel().equalsIgnoreCase(model)) {
                matches.add(v);
            }
        }
        return matches;
    }

    public List<Vehicle> getVehiclesByYear(int min, int max) {
        ArrayList<Vehicle> matches = new ArrayList<>();
        for (Vehicle v : inventory) {
            if (v.getYear() >= min && v.getYear() <= max) {
                matches.add(v);
            }
        }
        return matches;
    }

    public List<Vehicle> getVehiclesByColor(String color) {
        ArrayList<Vehicle> matches = new ArrayList<>();
        for (Vehicle v : inventory) {
            if (v.getColor().equalsIgnoreCase(color)) {
                matches.add(v);
            }
        }
        return matches;
    }

    public List<Vehicle> getVehiclesByMileage(int min, int max) {
        ArrayList<Vehicle> matches = new ArrayList<>();
        for (Vehicle v : inventory) {
            if (v.getMiles() >= min && v.getMiles() <= max) {
                matches.add(v);
            }
        }
        return matches;
    }

    public List<Vehicle> getVehiclesByType(String vehicleType) {
        ArrayList<Vehicle> matches = new ArrayList<>();
        for (Vehicle v : inventory) {
            if (v.getType().equalsIgnoreCase(vehicleType)) {
                matches.add(v);
            }
        }
        return matches;
    }

    public List<Vehicle> getAllVehicles() {
        return inventory;
    }

    Vehicle addVehicle(Vehicle v) {
        inventory.add(v);
        return v;
    }

    void removeVehicle(Vehicle v) {
        inventory.remove(v);
    }

    public static void main(String[] args) {
        var d = new Dealership("XYZ MOTORS", "", "");

        //ADD TO INVENTORY
        d.addVehicle(new Vehicle(
                123,
                1999,
                "MAZDA",
                "MX-5",
                "ROADSTER",
                "WHITE",
                128_000,
                5_000
        ));

        d.addVehicle(new Vehicle(
                456,
                2008,
                "HYUNDAI",
                "ELANTRA GT",
                "HATCHBACK",
                "BLACK",
                111_000,
                10_000
        ));

        //SHOW DEALERSHIP
        System.out.println(d);

        //SHOW INVENTORY
//        var matches = d.getVehiclesByColor("WHITE");
//        var matches = d.getAllVehicles();
//        var matches = d.getVehiclesByYear(2000, 2025);
//        var matches = d.getVehiclesByType("ROADSTER");
//        var matches = d.getVehiclesByPrice(4000,6000);
//        var matches = d.getVehiclesByMileage(1,111_000);
        var matches = d.getVehiclesByMakeModel("MAZDA", "MX-5");
        if (matches.size() == 0) {
            System.out.println("NO MATCHES");
        } else {
            for (Vehicle v : matches) {
                System.out.println(v);
            }
        }
    }

    public String getName() {
        return this.name;
    }

    public String getAddress() {
        return this.address;
    }

    public String getPhone() {
        return this.phone;
    }
}