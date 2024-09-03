package Airport;

import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Plane extends Thread{
    private static final int NUM_RUNWAYS = 1;
    private int ID;
    
    // Create a Semaphore to store runway.
    // The size is fixed to 1 as there will only be 1 runway.
    public static Semaphore runway = new Semaphore(NUM_RUNWAYS);    

    public void run(){
        try {
            //3rd plane will required emergency landing
            if (ID==3){
                //Add plane into the semaphore runway
                runway.acquire();
                System.out.println("\n!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!\n"
                    + "\tEMERGENCY LANDING REQUIRED\n\t      "+java.time.LocalTime.now()
                    + "\n\tPLANE-" + ID + " REQUESTING TO LAND\n\t    **FUEL SHORTAGE**"
                    + "\n!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
            //Normal planes
            }else{
                //Add plane into the semaphore runway
                runway.acquire();
                System.out.println("\n***********************************************"
                    + "\n\t\t"+java.time.LocalTime.now()
                    + "\n\tPLANE-" + ID + " REQUESTING TO LAND"
                    + "\n***********************************************" );
            }
        } catch (InterruptedException ex) {
            Logger.getLogger(Plane.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    //Constructor to get parameters
    Plane(int ID){
        this.ID = ID;
    }
}
