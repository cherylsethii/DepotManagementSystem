package Controller;

import Model.*;
import View.DepotView;

import java.io.FileNotFoundException;
import java.io.IOException;

public class DepotApp {
    public static void main(String[] args) throws FileNotFoundException {
        ParcelMap parcelMap = new ParcelMap();
        QueueOfCustomers customerQueue = new QueueOfCustomers();
        Log log = Log.getInstance();

        Manager.loadParcels(parcelMap, "Parcels.csv");
        Manager.loadCustomers(customerQueue, "Custs.csv");

        // Initialize GUI
        DepotView view = new DepotView();
        new DepotController(parcelMap, customerQueue, log, view);

        // Display the GUI
        view.setVisible(true);

        // Add shutdown hook to write log to a file when the program exits
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                Log.getInstance().writeLogToFile("Report.txt");
                System.out.println("Log written to Report.txt");
            } catch (IOException e) {
                System.err.println("Failed to write log to file: " + e.getMessage());
            }
        }));
    }
}
