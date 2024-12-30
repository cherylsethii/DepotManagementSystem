package Model;

import java.util.HashMap;
import java.util.Map;

public class ParcelMap {
    private Map<String, Parcel> parcels;

    public ParcelMap() {
        this.parcels = new HashMap<>();
    }


    public void addParcel(Parcel parcel) {
        parcels.put(parcel.getParcelID(), parcel);
    }

    // Add a new parcel using parameters
    public void addNewParcel(String id, double length, double width, double height, int daysInDepot, double weight) {
        Parcel newParcel = new Parcel(id, length, width, height, daysInDepot, weight);
        addParcel(newParcel);
        Log.getInstance().addLog(String.format("New parcel added: ID = %s | Dimensions = %.2fx%.2fx%.2f | Weight = %.2fkg | Days in Depot = %d",
                id, length, width, height, weight, daysInDepot));
    }

    // Remove a parcel by ID
    public boolean removeParcel(String parcelID) {
        if (parcels.containsKey(parcelID)) {
            parcels.remove(parcelID);
            return true;
        }
        return false; // Parcel not found
    }

    public Parcel findParcelByID(String id) {
        return parcels.get(id);
    }

    public Map<String, Parcel> getParcels() {
        return parcels;
    }
}
