package Controller;

import Model.*;
import View.DepotView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class DepotController {
    private ParcelMap parcelMap;
    private QueueOfCustomers customerQueue;
    private Log log;
    private DepotView view;

    public DepotController(ParcelMap parcelMap, QueueOfCustomers customerQueue, Log log, DepotView view) {
        this.parcelMap = parcelMap;
        this.customerQueue = customerQueue;
        this.log = log;
        this.view = view;

        // Update UI initially
        updateView();

        // Add button listener
        this.view.getProcessNextButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                processNextCustomer();
            }
        });

        this.view.getAddCustomerButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addNewCustomer();
            }
        });

        this.view.getRemoveCustomerButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removeCustomer();
            }
        });

        this.view.getRemoveParcelButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removeParcel();
            }
        });

        this.view.getAddParcelButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addNewParcel();
            }
        });

        this.view.getSearchParcelButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchParcel();
            }
        });
        view.getSortOrderComboBox().addActionListener(e -> sortParcels());
    }

    private void sortParcels() {
        // Determine the sort order
        String sortOrder = (String) view.getSortOrderComboBox().getSelectedItem();
        boolean isAscending = "Ascending".equals(sortOrder);

        // Retrieve and sort parcels
        List<Parcel> parcels = new ArrayList<>(parcelMap.getParcels().values());
        parcels.sort(isAscending ?
                Comparator.comparingInt(Parcel::getDaysInDepot) :
                Comparator.comparingInt(Parcel::getDaysInDepot).reversed());

        // Update the display
        String parcelDisplay = parcels.stream()
                .map(Parcel::toString)
                .reduce((a, b) -> a + "\n" + b)
                .orElse("No parcels available.");
        view.updateParcelList(parcelDisplay);
    }

    private void searchParcel() {
        String id = JOptionPane.showInputDialog(view, "Enter Parcel ID to search:").strip(); // Prompt user for Parcel ID
        System.out.println("Searching for Parcel ID: " + id); // Debugging line
        Parcel parcel = parcelMap.findParcelByID(id);
        if (parcel != null) {
            JOptionPane.showMessageDialog(view, parcel.toString(), "Parcel Found", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(view, "Parcel not found.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void addNewCustomer() {
        String name = JOptionPane.showInputDialog(view, "Enter Customer Name:", "Add New Customer", JOptionPane.QUESTION_MESSAGE);
        if (name == null || name.trim().isEmpty()) {
            JOptionPane.showMessageDialog(view, "Customer name cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String parcelId = JOptionPane.showInputDialog(view, "Enter Parcel ID:", "Add New Customer", JOptionPane.QUESTION_MESSAGE);
        if (parcelId == null || parcelId.trim().isEmpty()) {
            JOptionPane.showMessageDialog(view, "Parcel ID cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        customerQueue.createNewCustomer(name, parcelId);
        log.addLog("New customer added: " + name + " with Parcel ID: " + parcelId);
        JOptionPane.showMessageDialog(view, "Customer successfully added.", "Success", JOptionPane.INFORMATION_MESSAGE);
        updateView();
    }

    private void addNewParcel() {
        try {
            String id = JOptionPane.showInputDialog(view, "Enter Parcel ID:", "Add New Parcel", JOptionPane.QUESTION_MESSAGE);
            if (id == null || id.trim().isEmpty()) {
                JOptionPane.showMessageDialog(view, "Parcel ID cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String lengthInput = JOptionPane.showInputDialog(view, "Enter Length:", "Add New Parcel", JOptionPane.QUESTION_MESSAGE);
            double length = validateDouble(lengthInput, "Length");

            String widthInput = JOptionPane.showInputDialog(view, "Enter Width:", "Add New Parcel", JOptionPane.QUESTION_MESSAGE);
            double width = validateDouble(widthInput, "Width");

            String heightInput = JOptionPane.showInputDialog(view, "Enter Height:", "Add New Parcel", JOptionPane.QUESTION_MESSAGE);
            double height = validateDouble(heightInput, "Height");

            String daysInput = JOptionPane.showInputDialog(view, "Enter Days in Depot:", "Add New Parcel", JOptionPane.QUESTION_MESSAGE);
            int daysInDepot = validateInteger(daysInput, "Days in Depot");

            String weightInput = JOptionPane.showInputDialog(view, "Enter Weight:", "Add New Parcel", JOptionPane.QUESTION_MESSAGE);
            double weight = validateDouble(weightInput, "Weight");

            parcelMap.addNewParcel(id, length, width, height, daysInDepot, weight);
            JOptionPane.showMessageDialog(view, "Parcel successfully added.", "Success", JOptionPane.INFORMATION_MESSAGE);
            updateView();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(view, "An error occurred while adding the parcel. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private double validateDouble(String input, String fieldName) throws Exception {
        if (input == null || input.trim().isEmpty()) {
            throw new Exception(fieldName + " cannot be empty.");
        }
        try {
            return Double.parseDouble(input);
        } catch (NumberFormatException e) {
            throw new Exception(fieldName + " must be a valid number.");
        }
    }

    private int validateInteger(String input, String fieldName) throws Exception {
        if (input == null || input.trim().isEmpty()) {
            throw new Exception(fieldName + " cannot be empty.");
        }
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            throw new Exception(fieldName + " must be a valid integer.");
        }
    }


    private void updateView() {
        StringBuilder parcelList = new StringBuilder();
        for (Parcel parcel : parcelMap.getParcels().values()) {
            if (!parcel.isCollected()) {
                parcelList.append(parcel).append("\n");
            }
        }
        view.updateParcelList(parcelList.toString());

        StringBuilder customerQueueList = new StringBuilder();
        for (Customer customer : customerQueue.getAllCustomers()) {
            customerQueueList.append(customer).append("\n");
        }
        view.updateCustomerQueue(customerQueueList.toString());
    }

    private void processNextCustomer() {
        // Check if the queue is empty
        if (customerQueue.isEmpty()) {
            JOptionPane.showMessageDialog(view, "No more customers in the queue.");
            return;
        }

        // Display a list of customers for selection
        Object[] customers = customerQueue.getAllCustomers().toArray();
        Customer selectedCustomer = (Customer) JOptionPane.showInputDialog(
                view,
                "Select a customer to process:",
                "Process Customer",
                JOptionPane.PLAIN_MESSAGE,
                null,
                customers,
                customers.length > 0 ? customers[0] : null
        );

        // Check if a customer was selected
        if (selectedCustomer == null) {
            JOptionPane.showMessageDialog(view, "No customer selected.");
            return;
        }

        // Process the selected customer
        Parcel parcel = parcelMap.findParcelByID(selectedCustomer.getParcelID());

        if (parcel == null) {
            log.addLog("Customer " + selectedCustomer.getName() + " attempted to collect a non-existent parcel: " + selectedCustomer.getParcelID());
            view.updateCurrentParcel("Error: Parcel not found.");
        } else if (parcel.isCollected()) {
            log.addLog("Customer " + selectedCustomer.getName() + " attempted to collect an already collected parcel: " + selectedCustomer.getParcelID());
            view.updateCurrentParcel("Error: Parcel already collected.");
        } else {
            double fee = Worker.calculateFee(parcel);
            parcel.markCollected();
            log.addLog("Customer " + selectedCustomer.getName() + " collected parcel " + parcel.getParcelID() + " with fee $" + fee);
            view.updateCurrentParcel("Customer: " + selectedCustomer.getName() + "\nParcel: " + parcel.getParcelID() + "\nFee: $" + fee);
        }

        updateView();
    }

    private void removeCustomer() {
        String name = JOptionPane.showInputDialog(view, "Enter Customer Name:", "Remove Customer", JOptionPane.QUESTION_MESSAGE);
        if (name == null || name.trim().isEmpty()) {
            JOptionPane.showMessageDialog(view, "Customer name cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String parcelId = JOptionPane.showInputDialog(view, "Enter Parcel ID:", "Remove Customer", JOptionPane.QUESTION_MESSAGE);
        if (parcelId == null || parcelId.trim().isEmpty()) {
            JOptionPane.showMessageDialog(view, "Parcel ID cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        boolean removed = customerQueue.removeCustomer(name, parcelId);
        if (removed) {
            log.addLog("Customer removed: " + name + " with Parcel ID: " + parcelId);
            JOptionPane.showMessageDialog(view, "Customer removed successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(view, "Customer not found.", "Error", JOptionPane.ERROR_MESSAGE);
        }
        updateView();
    }

    private void removeParcel() {

        String parcelId = JOptionPane.showInputDialog(view, "Enter Parcel ID:", "Remove Customer", JOptionPane.QUESTION_MESSAGE);
        if (parcelId == null || parcelId.trim().isEmpty()) {
            JOptionPane.showMessageDialog(view, "Parcel ID cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        boolean removed = parcelMap.removeParcel(parcelId);
        if (removed) {
            log.addLog("Parcel removed with Parcel ID: " + parcelId);
            JOptionPane.showMessageDialog(view, "Parcel removed successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(view, "Parcel not found.", "Error", JOptionPane.ERROR_MESSAGE);
        }
        updateView();
    }

}
