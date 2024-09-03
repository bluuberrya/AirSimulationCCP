package Airport;

import java.text.DecimalFormat;

public class Statistics {
//Create array to store data
static double[] runtimes = new double[6];
static int[] passengers = new int[6];
    
    //Get number of passengers of each plane
    static int[] getPassengers(int ID,int passenger) {
        //Store into passengers array
        passengers[ID-1] = passenger;
        return passengers;
    }
    
    //Get runtime of each plane and store into runtimes array
    static double[] getRunTime(int ID,long startTime) {
        //Calculate runtime and convert to seconds
        long endTime = System.currentTimeMillis();
        long runtimeInMillis = endTime - startTime;
        double runTime = runtimeInMillis / 1000.0;
        //Store into runtimes array
        runtimes[ID-1] = runTime;
        return runtimes;
    }
    
    //Generate statistical report
    static void getStatisticalReport() {
        //Calculate number of passengers
        int sumPass = 0;
        for (int i = 0; i < passengers.length; i++) {
            sumPass += passengers[i];
        }
        int averagePass = sumPass/passengers.length;
        
        //Calculate average, minimum, and maximum runtime
        //Convert double to 2 decimal points
        DecimalFormat df = new DecimalFormat("#.##");
        double sum = 0;
        double min = Double.MAX_VALUE;
        double max = Double.MIN_VALUE;
        for (int i = 0; i < runtimes.length; i++) {
            sum += runtimes[i];
            if (runtimes[i] < min) {
                min = runtimes[i];
            }
            if (runtimes[i] > max) {
                max = runtimes[i];
            }
        }
        double average = sum / runtimes.length;
        
        //Display statistics report
        System.out.println("------------- Statistics -------------");
        
        //Number of passenger
        System.out.println("\t      Passenger");
        for (int i=0;i<6;i++){
            System.out.println("\t     PLANE-" + (i+1) + ": " + passengers[i]);
        }
        System.out.println("\t   Total\t: "  + sumPass);
        System.out.println("\t  Average\t: "  + averagePass);
        
        //Runtime of each planes
        System.out.println("\n\t\t  ----\n\t    Runtime(second)");
        for (int i=0;i<6;i++){
            System.out.println("\t     PLANE-" + (i+1) + ": " + Double.valueOf(df.format(runtimes[i])) + "s");
        }
        System.out.println("\t   Total\t: "  + Double.valueOf(df.format(sum)));
        System.out.println("\t  Average\t: " 
                + Double.valueOf(df.format(average))+ "s");
        System.out.println("\t  Minimum\t: " 
                + Double.valueOf(df.format(min)) + "s");
        System.out.println("\t  Maximum\t: " 
                + Double.valueOf(df.format(max)) + "s");
    }
}
