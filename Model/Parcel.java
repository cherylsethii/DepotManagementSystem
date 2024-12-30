package Model;

public class Parcel {
    private String parcelID;
    private double length;
    private double width;
    private double height;
    private int daysInDepot;
    private double weight;
    private boolean collected;

    public Parcel(String parcelID, double length, double width, double height, int daysInDepot, double weight) {
        this.parcelID = parcelID;
        this.length = length;
        this.width = width;
        this.height = height;
        this.daysInDepot = daysInDepot;
        this.weight = weight;
        this.collected = false;
    }

    public String getParcelID() {
        return parcelID;
    }

    public int getDaysInDepot() {
        return daysInDepot;
    }

    public double getWeight() {
        return weight;
    }

    public double getVolume() {
        return length * width * height;
    }

    public boolean isCollected() {
        return collected;
    }

    public void markCollected() {
        this.collected = true;
    }

    @Override
    public String toString() {
        return parcelID + " | " + daysInDepot + " days | " + weight + "kg | Dimensions: " + length + "x" + width + "x" + height;
    }
}
