package Controller;

import Model.*;
import java.io.*;
import java.util.Scanner;

public class Manager {

    public static void main(String[] args) throws IOException {
        ParcelMap parcelMap = new ParcelMap();
        QueueOfCustomers customerQueue = new QueueOfCustomers();
        Log log = Log.getInstance();

        loadParcels(parcelMap, "Parcels.csv");
        loadCustomers(customerQueue, "Custs.csv");

        while (!customerQueue.isEmpty()) {
            Customer customer = customerQueue.processNextCustomer();
            Worker.processCustomer(customer, parcelMap, log);
        }

        writeReport(log, parcelMap);
    }

    static void loadParcels(ParcelMap parcelMap, String fileName) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File(fileName));
        while (scanner.hasNextLine()) {
            String[] data = scanner.nextLine().split(",");
            parcelMap.addParcel(new Parcel(data[0], Integer.parseInt(data[1]), Integer.parseInt(data[2]),
                    Integer.parseInt(data[3]), Integer.parseInt(data[4]), Double.parseDouble(data[5])));
        }
        scanner.close();
    }

    static void loadCustomers(QueueOfCustomers customerQueue, String fileName) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File(fileName));
        while (scanner.hasNextLine()) {
            String[] data = scanner.nextLine().split(",");
            customerQueue.addCustomer(new Customer(data[0], data[1]));
        }
        scanner.close();
    }

    private static void writeReport(Log log, ParcelMap parcelMap) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter("Report.txt"));
        writer.write("Log Entries:\n" + log.getLogs());
        writer.write("\nUncollected Parcels:\n");
        for (Parcel parcel : parcelMap.getParcels().values()) {
            if (!parcel.isCollected()) {
                writer.write(parcel.toString() + "\n");
            }
        }
        writer.close();
    }
}
