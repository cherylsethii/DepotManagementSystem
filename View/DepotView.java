package View;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class DepotView extends JFrame {
    private JTextArea parcelListArea;
    private JTextArea customerQueueArea;
    private JTextArea currentParcelArea;
    private JButton processNextButton;
    private JButton addCustomerButton;
    private JButton removeCustomerButton;
    private JButton removeParcelButton;
    private JButton addParcelButton;
    private JButton searchParcelButton;
    private JComboBox<String> sortOrderComboBox;


    public DepotView() {
        setTitle("Depot Management System");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Parcels Panel
        JPanel parcelPanel = new JPanel(new BorderLayout());
        parcelPanel.setBorder(BorderFactory.createTitledBorder("Parcels in Depot"));
        parcelListArea = new JTextArea(10, 30);
        parcelListArea.setEditable(false);
        parcelPanel.add(new JScrollPane(parcelListArea), BorderLayout.CENTER);

        // Customer Queue Panel
        JPanel customerPanel = new JPanel(new BorderLayout());
        customerPanel.setBorder(BorderFactory.createTitledBorder("Customer Queue"));
        customerQueueArea = new JTextArea(10, 30);
        customerQueueArea.setEditable(false);
        customerPanel.add(new JScrollPane(customerQueueArea), BorderLayout.CENTER);

        // Current Parcel Panel
        JPanel currentParcelPanel = new JPanel(new BorderLayout());
        currentParcelPanel.setBorder(BorderFactory.createTitledBorder("Current Parcel Being Processed"));
        currentParcelArea = new JTextArea(5, 30);
        currentParcelArea.setEditable(false);
        currentParcelPanel.add(new JScrollPane(currentParcelArea), BorderLayout.CENTER);

        // Controls Panel
        JPanel controlPanel = new JPanel();
        processNextButton = new JButton("Process Next Customer");
        controlPanel.add(processNextButton);
        addCustomerButton = new JButton("Add Customer");
        controlPanel.add(addCustomerButton);
        removeCustomerButton = new JButton("Remove Customer");
        controlPanel.add(removeCustomerButton);
        removeParcelButton = new JButton("Remove Parcel");
        controlPanel.add(removeParcelButton);
        addParcelButton = new JButton("Add Parcel");
        controlPanel.add(addParcelButton);
        searchParcelButton = new JButton("Search Parcel");
        controlPanel.add(searchParcelButton);


        // Create and configure the Sort Order Panel
        JPanel sortOrderPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        sortOrderPanel.setBorder(BorderFactory.createTitledBorder("Sort Parcels"));
        // Add label and combo box for sorting
        JLabel sortLabel = new JLabel("Sort by Days in Depot:");
        sortOrderComboBox = new JComboBox<>(new String[]{"Ascending", "Descending"});
        // Add components to the panel
        sortOrderPanel.add(sortLabel);
        sortOrderPanel.add(sortOrderComboBox);
        // Add the Sort Order Panel to the bottom of the parcel panel
        parcelPanel.add(sortOrderPanel, BorderLayout.SOUTH);

        // Add Panels to Frame
        add(parcelPanel, BorderLayout.WEST);
        add(customerPanel, BorderLayout.EAST);
        add(currentParcelPanel, BorderLayout.CENTER);
        add(controlPanel, BorderLayout.SOUTH);
    }

    public JComboBox<String> getSortOrderComboBox() {
        return sortOrderComboBox;
    }

    public void updateParcelList(String parcels) {
        parcelListArea.setText(parcels);
    }

    public void updateCustomerQueue(String customers) {
        customerQueueArea.setText(customers);
    }

    public void updateCurrentParcel(String parcel) {
        currentParcelArea.setText(parcel);
    }

    public JButton getProcessNextButton() {
        return processNextButton;
    }

    public JButton getAddCustomerButton() {
        return addCustomerButton;
    }

    public JButton getRemoveCustomerButton() {
        return removeCustomerButton;
    }

    public JButton getRemoveParcelButton() {
        return removeParcelButton;
    }
    public JButton getAddParcelButton() {
        return addParcelButton;
    }

    public JButton getSearchParcelButton() {
        return searchParcelButton;
    }

}
