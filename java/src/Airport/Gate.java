package Airport;

import static Airport.Plane.runway;
import static Airport.Refuel.fuelTruck;
import java.util.Random;
import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Gate extends Thread{
    // Variable
    private long startTime;
    private int ID;
    private boolean isEmergency;
    private int gateNumber;
    // Create a Semaphore to store gates.
    // The size is fixed to 2 as there will be 2 planes maximum.
    private static final int NUM_GATES = 2;
    public static Semaphore gates = new Semaphore(NUM_GATES);

    
    Random random = new Random();
    
    public void run(){
        try {
            //Randomly sleep the thread in between 0 and 2 seconds
            Thread.sleep(random.nextInt(2000));
            if (isEmergency == true){
                emergencyLanding();
            }else{
                normalLanding();
            }
        } catch (InterruptedException ex) {
            Logger.getLogger(Gate.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    void normalLanding(){
        //Randomly sleep the thread in between 0.3 and 0.5 seonds
        int randomTime = random.nextInt(200) + 300;
        //Randomly generate 10 to 50 passengers
        int numPassengers = random.nextInt(41) + 10;
        int numDisembark = random.nextInt(41) + 10;
        //Store number of passengers for statistics
        Statistics.getPassengers(ID, numPassengers);
        try {
            //Assigning planes to gate
            if(ID == 1 || ID == 3 || ID == 5){
                gateNumber = 1;
            }else{
                gateNumber = 2;
            }
            //Release plane from the semaphore runway
            runway.release();
            
            //Display runway and gate availability
            printAvailStatus(0);
            printAvailStatus(1);
            
            //Airplane uses runway
            System.out.println("\n********** PLANE-" + ID + " ANNOUNCEMENT: RUNWAY AVAILABLE **********"); 
            Thread.sleep(randomTime);
            System.out.println("["+ java.time.LocalTime.now() + "]" + " PLANE-" + ID + " is using the runway for landing");
            
            //Add plane into the semaphore gates
            printAvailStatus(2);
            gates.acquire();
            System.out.println("\n********** PLANE-" + ID + " ANNOUNCEMENT: GATE AVAILABLE **********");
            System.out.println("PLANE-" + ID + " has been assigned to gate-" + gateNumber);
            System.out.println("PLANE-" + ID + " has successfully docked at the gate-" + gateNumber);
            
            //Passengers disembarked from plane
            System.out.println("\n---------- PLANE-" + ID + ": PASSENGER DISEMBARKMENT PROCESS ----------");
            for (int i=1; i<=(numDisembark); i++){
                //Passenger disembark in a random generated
                System.out.println("PLANE-" + ID + " Passenger-" + i + " is disembarking");
                Thread.sleep(random.nextInt(150) + 30);
            };
            System.out.println("========== PLANE-" + ID + " ALL PASSENGERS HAVE DISEMBARKED ==========");
            
            //Cabin crew cleaner clean the aircraft cabin
            CleanCabin t1 = new CleanCabin(ID, randomTime);
            t1.start();

            //Aircraft Refueller refuel aircraft
            //Display fuel truck availability
            printAvailStatus(3);
            Refuel t2 = new Refuel(ID, randomTime);
            t2.start();
            
            //Embark passengers to plane
            System.out.println("\n---------- PLANE-" + ID + ": PASSENGER EMBARKMENT PROCESS ----------");
            for (int i=1; i<=numPassengers; i++){
                System.out.println("PLANE-" + ID + " Passenger-" + i + " is embarking");
                Thread.sleep(random.nextInt(150) + 30);
            };
            System.out.println("========== PLANE-" + ID + " ALL PASSENGERS HAVE EMBARKED ==========");
            
            //Plane ready to dock off
            System.out.println("\n---------- PLANE-" + ID + ": TAKEOFF PROCESS ----------");
            System.out.println("PLANE-" + ID + " docked off from the gate-" + gateNumber);
            Thread.sleep(randomTime);
            
            //Release plane from the semaphore gates
            gates.release();
            System.out.println("PLANE-" + ID + " coast to runway for departure");
            Thread.sleep(randomTime);
            
            //Add plane into the semaphore runway
            printAvailStatus(1);
            runway.acquire();
            
            //Plane uses runway and departed
            System.out.println("PLANE-" + ID + " is using the runway for departure");
            Thread.sleep(randomTime);
            System.out.println("\n===============================================\n"
                    +"\t\t"+java.time.LocalTime.now()
                    +"\n\tPLANE-" + ID + " HAS SUCCESSFULLY DEPARTED"
                    +"\n===============================================\n" );
            
            //Release plane from the semaphore runway
            runway.release();
            
            //Get runtime of plane for statistics
            Statistics.getRunTime(ID,startTime);
            
        } catch (InterruptedException ex) {
            Logger.getLogger(Gate.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    void emergencyLanding(){
        //Randomly sleep the thread in between 0.2 and 0.5
        int randomTime = random.nextInt(200) + 300;
        //Randomly generate number of passengers
        int numPassengers = random.nextInt(41) + 10;
        int numDisembark = random.nextInt(41) + 10;
        //Store number of passengers for statistics
        Statistics.getPassengers(ID, numPassengers);
        try {
            //Assigning planes to gate and print available number of gates
            if(ID == 1 || ID == 3 || ID == 5){
                gateNumber = 1;
            }else{
                gateNumber = 2;
            }
            
            //Release plane from the semaphore runway
            runway.release();
            
            //Display runway and gate availability
            printAvailStatus(0);
            printAvailStatus(1);
            
            //Airplane uses runway
            System.out.println("\n!!!!!!!!!! PLANE-" + ID + " ANNOUNCEMENT: RUNWAY AVAILABLE !!!!!!!!!!"); 
            Thread.sleep(randomTime);
            System.out.println("["+ java.time.LocalTime.now() + "]" + " PLANE-" + ID + " is using the runway for landing");
            
            //Add plane into the semaphore gates
            printAvailStatus(2);
            gates.acquire();
            System.out.println("\n!!!!!!!!!! PLANE-" + ID + " ANNOUNCEMENT: GATES AVAILABLE !!!!!!!!!!");
            System.out.println("PLANE-" + ID + " has been assigned to gate-" + gateNumber);
            System.out.println("PLANE-" + ID + " has successfully docked at the gate-" + gateNumber);
                
            //Aircraft Refueller refuel the plane
            //Display fuel truck availability
            printAvailStatus(3);
            Refuel t2 = new Refuel(ID, randomTime);
            t2.start();

            //Passengers disembarked from plane
            System.out.println("\n---------- PLANE-" + ID + ": PASSENGER DISEMBARKMENT PROCESS ----------");
            for (int i=1; i<=(numDisembark); i++){
                //Passenger disembark in a random generated
                System.out.println("PLANE-" + ID + " Passenger-" + i + " is disembarking");
                Thread.sleep(randomTime);
            };
            System.out.println("========== PLANE-" + ID + " ALL PASSENGERS HAVE DISEMBARKED ==========");
            
            //Cabin crew cleaner clean the aircraft cabin
            CleanCabin t1 = new CleanCabin(ID, randomTime);
            t1.start();
            
            //Embark passengers to plane
            System.out.println("\n---------- PLANE-" + ID + ": PASSENGER EMBARKMENT PROCESS ----------");
            for (int i=1; i<=numPassengers; i++){
                //Passenger disembark in a random generated
                System.out.println("PLANE-" + ID + " Passenger-" + i + " is embarking");
                Thread.sleep(randomTime);
            };
            System.out.println("========== PLANE-" + ID + " ALL PASSENGERS HAVE EMBARKED ==========");
            
            //Plane ready to dock off
            System.out.println("\n---------- PLANE-" + ID + ": TAKEOFF PROCESS ----------");
            System.out.println("PLANE-" + ID + " docked off from gate-" + gateNumber);
            Thread.sleep(randomTime);
            
            //Release plane from the semaphore gates
            gates.release();
            System.out.println("PLANE-" + ID + " coast to runway for departure");
            Thread.sleep(randomTime);
            
            //Add plane into the semaphore runway
            printAvailStatus(1);
            runway.acquire();
            
            //Plane uses runway and departed
            System.out.println("PLANE-" + ID + " is using the runway for departure");
            Thread.sleep(randomTime);
            System.out.println("\n===============================================\n"
                    +"\t\t"+java.time.LocalTime.now()
                    +"\n\tPLANE-" + ID + " HAS SUCCESSFULLY DEPARTED"
                    +"\n===============================================\n" );
            
            //Release plane from the semaphore runway
            runway.release();
            
            //Get runtime of plane for statistics
            Statistics.getRunTime(ID,startTime);
            
        } catch (InterruptedException ex) {
            Logger.getLogger(Gate.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    void printAvailStatus(int mode) {
        //Print runway availability
        if (mode == 1){
            if (runway.availablePermits()==0){
                System.out.println("\t   No runway available...\n##### PLANE-" + ID +" IS WAITING FOR AVAILABLE RUNWAY #####");
            }else{
                System.out.println("\t   Clearing runway...");
            }
        //Print gates availability
        }else if (mode == 2){
            if (gates.availablePermits()==0){
                System.out.println("\t    No gate available...\n##### PLANE-" + ID +" IS WAITING FOR AVAILABLE GATE #####");
            }else{
                System.out.println("\t   Assigning available gate...");
            }
        }
        //Print fuel truck availability
        else if(mode == 3){
            if (fuelTruck.availablePermits()==0){
                System.out.println("\t    No fuel truck available...\n    ##### PLANE-" + ID +" IS WAITING FOR REFUEL #####");
            }else{
                System.out.println("\t   Allocating fuel truck...\n\t  [Fuel Truck Available]: " + fuelTruck.availablePermits());
            }
        //Print runway and gates availability
        }else{
            System.out.println("\n[Runway Available]:" + runway.availablePermits()+"\t[Gates Available]:" + gates.availablePermits());
        }
    }
    
    //Constructor to get parameters  
    Gate(int ID, boolean isEmergency, long startTime){
        this.ID = ID;
        this.isEmergency = isEmergency;
        this.startTime = startTime;
    }
    
    static class gates {
        public gates() {
        }
    }
}