/**
 * This class implements the Door component of the sushi bar assignment
 * The Door corresponds to the Producer in the producer/consumer problem
 * The Door will work as the producer.
 * The door creates customers at random intervals and tries to put them in the waiting area if there is room.
 * If there is no more room in the waiting area the door should wait until notified that a customer left the waiting area.
 */

public class Door implements Runnable {

    private WaitingArea waitingArea;


    public Door(WaitingArea waitingArea) {
        this.waitingArea = waitingArea;

    }


    @Override
    public void run() {
        int id = 1;

        while (SushiBar.isOpen) {
            if (!waitingArea.isFull()) {


                Customer c = new Customer(id);

                this.waitingArea.enter(c);
                id += 1;
            }


            //Make costumers in random intervals
            try {
                Thread.sleep(SushiBar.doorWait);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        waitingArea.close();

    }

}