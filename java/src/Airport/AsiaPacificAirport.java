package Airport;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AsiaPacificAirport {
    public static void main(String[] args) {
        Random rand = new Random();
        Gate gates;
        
        //To avoid having too many planes waiting
        //Maximum 3 threads will be active to process tasks
        ExecutorService executor = Executors.newFixedThreadPool(3);
        
        System.out.println("-------------- WELCOME TO ASIA PACIFIC AIRPORT --------------");
        // Generate 6 planes at random intervals.
        for(int i = 1; i <= 6; i++) {
            try {
                // Wait between 0 to 3 seconds to generate a new plane.
                Thread.sleep(rand.nextInt(3000));
                
                //Start plane thread i
                Plane plane = new Plane(i);
                executor.submit(plane);
                
                //Get start time
                long startTime = System.currentTimeMillis();
                if (i == 3){
                    Executors.newFixedThreadPool(3);
                    //Assign emergency plane through parameter
                    gates = new Gate(i,true,startTime);
                    gates.setPriority(Thread.MAX_PRIORITY);
                }
                else{
                    Executors.newFixedThreadPool(2);
                    //Assign plane
                    gates = new Gate(i,false,startTime);
                    plane.setPriority(Thread.MIN_PRIORITY);
                }
                //Start gates thread
                executor.submit(gates);
                
                if (i == 6){
                    //Reject new tasks
                    executor.shutdown();
                    while (!executor.isTerminated()){
                        // Wait for all tasks to complete
                    }
                    //Generate statistics report
                    Statistics.getStatisticalReport();
                }
                
            } catch (InterruptedException ex) {
                Logger.getLogger(AsiaPacificAirport.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        System.out.println("---------------- END ----------------");
    }
}