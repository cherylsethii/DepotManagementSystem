package Model;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class QueueOfCustomers {
    private Queue<Customer> customerQueue;

    public QueueOfCustomers() {
        this.customerQueue = new LinkedList<>();
    }

    public void addCustomer(Customer customer) {
        customerQueue.add(customer);
    }

    public void createNewCustomer(String name, String parcelId) {
        addCustomer(new Customer(name, parcelId));
        Log.getInstance().addLog("New customer added: " + name + " | Parcel ID: " + parcelId);
    }

    public boolean removeCustomer(String name, String parcelId) {
        for (Customer customer : customerQueue) {
            if (customer.getName().equalsIgnoreCase(name) && customer.getParcelID().equalsIgnoreCase(parcelId)) {
                customerQueue.remove(customer);
                Log.getInstance().addLog("Customer removed: " + name + " | Parcel ID: " + parcelId);
                return true;
            }
        }
        return false; // Customer not found
    }


    public Customer processNextCustomer() {
        return customerQueue.poll();
    }

    public boolean isEmpty() {
        return customerQueue.isEmpty();
    }

    public List<Customer> getAllCustomers() {
        return new LinkedList<>(customerQueue);
    }

    @Override
    public String toString() {
        return "Queue: " + customerQueue;
    }
}
