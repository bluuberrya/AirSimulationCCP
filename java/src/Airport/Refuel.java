package Airport;

import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Refuel extends Thread{
    private static final int FUEL_TRUCK = 1;
    public static Semaphore fuelTruck = new Semaphore(FUEL_TRUCK);
    private int ID;
    private int randomTime;
    
    public void run(){
        try {
            System.out.println("\n---------- PLANE-" + ID + ": REFUELLING AIRCRAFT PROCESS ----------");
            System.out.println("PLANE-" + ID + " Aircraft Refueller reporting for work");
            Thread.sleep(randomTime);
            fuelTruck.acquire();
            System.out.println("PLANE-" + ID + " Aircraft Refueller is using the fuel truck");
            Thread.sleep(randomTime);
            System.out.println("PLANE-" + ID + " Aircraft Refueller is fueling the plane");
            Thread.sleep(randomTime);
            System.out.println("PLANE-" + ID + " Aircraft Refueller is returning the fueling truck");
            Thread.sleep(randomTime);
            System.out.println("========== PLANE-" + ID + " AIRCRAFT DONE REFUELLING ==========");
            fuelTruck.release();
        } catch (InterruptedException ex) {
            Logger.getLogger(Plane.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    //Constructor to get parameters
    Refuel(int ID,int randomTime){
        this.ID = ID;
        this.randomTime = randomTime;
    }
}

    
