/**
 * This class implements a customer, which is used for holding data and update the statistics
 * Also, the customer should be able to order food, when he/she is fetched from the waiting area by a waitress.
 * After ordering, the customer takes some time eating.
 * Note that the customer should have minimal functionality,
 * leaving the adding and removing of customers in the waiting area to the producer (door) and consumers (waitresses).
 */
public class Customer {

    private int id;
    private int totalOrders;
    private int takeaway;
    private int eaten;

    public Customer(int id) {
        this.id = id;

    }

    public synchronized void order() {
        totalOrders = (int) (Math.random() * (SushiBar.maxOrder + 1));
        takeaway = (int) (Math.random() * totalOrders);
        eaten = totalOrders - takeaway;

        SushiBar.totalOrders.add(totalOrders);
        SushiBar.servedOrders.add(eaten);
        SushiBar.takeawayOrders.add(takeaway);

    }


    public int getCustomerID() {

        return this.id;
    }

}
