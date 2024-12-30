package Controller;

import Model.Customer;
import Model.Log;
import Model.Parcel;
import Model.ParcelMap;

public class Worker {

    public static double calculateFee(Parcel parcel) {
        double baseFee = 5.0;
        baseFee += parcel.getWeight() * 0.5;
        baseFee += parcel.getDaysInDepot() * 0.2;
        return baseFee;
    }

    public static void processCustomer(Customer customer, ParcelMap parcelMap, Log log) {
        Parcel parcel = parcelMap.findParcelByID(customer.getParcelID());
        if (parcel != null && !parcel.isCollected()) {
            double fee = calculateFee(parcel);
            parcel.markCollected();
            log.addLog("Customer " + customer.getName() + " collected parcel " + parcel.getParcelID() + " with fee: $" + fee);
        } else {
            log.addLog("Parcel not found or already collected for " + customer.getName());
        }
    }
}
