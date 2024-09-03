package Airport;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author yamch
 */
public class CleanCabin extends Thread{
    private int ID;
    private int randomTime;
    
    public void run(){
        try {
            System.out.println("\n---------- PLANE-" + ID + ": CLEANING AIRCRAFT CABIN PROCESS ----------");
            System.out.println("PLANE-" + ID + " Cabin Cleaner Crew reporting for work");
            Thread.sleep(randomTime);
            System.out.println("PLANE-" + ID + " Cabin Cleaner Crew start cleaning the cabin");
            Thread.sleep(randomTime);
            System.out.println("PLANE-" + ID + " Cabin Cleaner Crew refilling supplies");
            Thread.sleep(randomTime);
            System.out.println("========== PLANE-" + ID + " AIRCRAFT CABIN DONE CLEANING ==========");
        } catch (InterruptedException ex) {
            Logger.getLogger(Plane.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    //Constructor to get parameters
    CleanCabin(int ID,int randomTime){
        this.ID = ID;
        this.randomTime = randomTime;
    }
}

    
