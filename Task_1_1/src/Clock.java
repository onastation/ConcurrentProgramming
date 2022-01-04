/**
 * The clock in the bar serves two purposes:
 * - It will alert the door of the closing time, so the door will generate no more customers.
 * - The clock keeps track of the current time of simulation, which will be used for logging.
 * You will not have to implement the clock yourself; instead you must use the provided class: Clock.java.
 * This clock sets the isOpen variable to false when the simulation time ends.
 * The only thing you need to do is to initiate the clock (new Clock(duration)), and use the isOpen variable properly.
 */

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;


public class Clock {
    Timer timer;


    public Clock(int seconds) {
        timer = new Timer();  //At this line a new Thread will be created
        timer.schedule(new RemindTask(), seconds * 1000); //delay in milliseconds
    }

    class RemindTask extends TimerTask {
        public void run() {
            SushiBar.isOpen = false; //prevents creating new customers.
            timer.cancel();
        }
    }

    public static String getTime() {
        // get current time
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss:SS");
        return sdf.format(cal.getTime());
    }
}
