package Model;

public class Customer {
    private String name;
    private String parcelID;

    public Customer(String name, String parcelID) {
        this.name = name;
        this.parcelID = parcelID;
    }

    public String getName() {
        return name;
    }

    public String getParcelID() {
        return parcelID;
    }

    @Override
    public String toString() {
        return name + " | Parcel ID: " + parcelID;
    }
}
