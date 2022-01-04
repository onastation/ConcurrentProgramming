/**
 * To see what is happening in the Sushi Bar you should print out the following messages at the time they happen:
 * - Customer #ID is now waiting.
 * - Customer #ID is now fetched.
 * - Customer #ID is now eating.
 * - Customer #ID is now leaving.
 * - ***** NO MORE CUSTOMERS - THE SHOP IS CLOSED NOW. *****
 * In order to print these messages you are to use the provided write method in the SushiBar class.
 * A call to the method may look something like this:
 * 𝑆𝑢𝑠h𝑖𝐵𝑎𝑟.𝑤𝑟𝑖𝑡𝑒(𝑇h𝑟𝑒𝑎𝑑.𝑐𝑢𝑟𝑟𝑒𝑛𝑡𝑇h𝑟𝑒𝑎𝑑().𝑔𝑒𝑡𝑁𝑎𝑚𝑒() + “:𝐶𝑢𝑠𝑡𝑜𝑚𝑒𝑟” + 𝑐𝑢𝑠𝑡𝑜𝑚𝑒𝑟𝑁𝑜 + “𝑖𝑠 𝑛𝑜𝑤 𝑐𝑟𝑒𝑎𝑡𝑒𝑑. ”);
 * Please stick to this format and only print out statistics and the messages listed above.
 * Note: The last message should be used when all customers have left the Sushi Bar.
 **/

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SushiBar {

    //SushiBar settings
    private static int waitingAreaCapacity = 15;
    private static int waitressCount = 8;
    private static int duration = 4;
    public static int maxOrder = 10;
    public static int waitressWait = 50; // Used to calculate the time the waitress spends before taking the order
    public static int customerWait = 2000; // Used to calculate the time the customer spends eating
    public static int doorWait = 100; // Used to calculate the interval at which the door tries to create a customer
    public static boolean isOpen = true;

    //Creating log file
    private static File log;
    private static String path = "./";

    //Variables related to statistics
    public static SynchronizedInteger customerCounter;
    public static SynchronizedInteger servedOrders;
    public static SynchronizedInteger takeawayOrders;
    public static SynchronizedInteger totalOrders;


    public static void main(String[] args) {
        log = new File(path + "log.txt");

        //Initializing shared variables for counting number of orders
        customerCounter = new SynchronizedInteger(0);
        totalOrders = new SynchronizedInteger(0);
        servedOrders = new SynchronizedInteger(0);
        takeawayOrders = new SynchronizedInteger(0);

        // Start the Clock
        Clock clock = new Clock(SushiBar.duration);

        // Initialize buffer
        WaitingArea waitingArea = new WaitingArea(waitingAreaCapacity);

        // Initialize the threads
        Door door = new Door(waitingArea);

        List<Waitress> createWaitresses = new ArrayList<>();
        for (int i = 0; i < waitressCount; i++) {
            createWaitresses.add(new Waitress(waitingArea));
        }

        List<Waitress> waitresses = createWaitresses;
        List<Thread> threads = new ArrayList<>();


        Thread threadDoor = new Thread(door);
        threadDoor.start();

        for (int i = 0; i < waitressCount; i++) {
            threads.add(new Thread(waitresses.get(i)));
            threads.get(i).start();
        }

        try {

            threadDoor.join();

        } catch (InterruptedException e) {

            e.printStackTrace();
        }

        // Join threads
        for (int i = 0; i < waitressCount; i++) {
            try {
                threads.get(i).join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }


    }

    //Writes actions in the log file and console
    public static void write(String str) {
        try {
            FileWriter fw = new FileWriter(log.getAbsoluteFile(), true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(Clock.getTime() + ", " + str + "\n");
            bw.close();
            System.out.println(Clock.getTime() + ", " + str);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
