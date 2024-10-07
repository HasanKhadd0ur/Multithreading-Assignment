package primeFindingTests;

import junit.framework.TestCase;
import multithreading.primesFinder.abstraction.PrimesFindStrategy;
import multithreading.primesFinder.coordinators.ThreadsCoordinator;
import multithreading.primesFinder.services.PrimeNumberService;
import multithreading.primesFinder.strategies.SieveOfAtkinStrategy;
import multithreading.primesFinder.strategies.SieveOfEratosthenesStrategy;
import multithreading.primesFinder.strategies.TrivialPrimesFindStrategy;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

import java.util.*;

public class ThreadsCoordinatorTest extends TestCase {

    private PrimesFindStrategy strategy ;
    private PrimeNumberService primeNumberService ;
    private List<Integer> primes;
    private List<PrimesFindStrategy> primesFindStrategies;

    private  ThreadsCoordinator threadsCoordinator ;

    private int [] threadsNumber ;
    private int [] intervals;


    @BeforeEach
    public void setUp(){

        // define the number of threads
        threadsNumber = new int[] {1,2,4,8,16,64,100};

        // define the intervals for searching on primes
        intervals = new int[] {1000,10000,400000,4000000};

        // setup out strategies
        primesFindStrategies = new ArrayList<>();

        // add sieve of eratosthenes strategy
        primesFindStrategies.add(new SieveOfEratosthenesStrategy());

        // add sieve of aktin strategy
        primesFindStrategies.add(new SieveOfAtkinStrategy());

        // add the trivial strategy
        primesFindStrategies.add(new TrivialPrimesFindStrategy());

    }




    @Test
    public void testThreadCoordinatorWork() {

        // define a service and strategy just to check if the coordinator works
        strategy =new SieveOfAtkinStrategy();
        primeNumberService = new PrimeNumberService(strategy);

        // create the thread coordinator
        threadsCoordinator  = new ThreadsCoordinator(primeNumberService,1,10,4);

        // assign the work
        threadsCoordinator.assignWork();

        // start the work
        threadsCoordinator.startWork();

        // get the result
        primes=threadsCoordinator.getResult();
        // check that the result contains the expected prime numbers
        assertEquals(Arrays.asList(2, 3, 5, 7), primes);
    }

    @Test
    public void testImpactOfThreadsCount(){

        System.out.println("+++ Measure The Impact Of The Number of Threads on Performance +++");

        // create a vari interval for thread count
        threadsNumber= new int[] {1,2,4,6,8,16,32,65,100,200,300,500,600};

        // define the strategy
        strategy = new SieveOfAtkinStrategy();
        PrimeNumberService service = new PrimeNumberService(strategy);

        // loop over the threads number and test execution time
        for(int numberOfThreads : threadsNumber){

            long executionTime =runPerformanceTest(service,3000000,numberOfThreads);

            System.out.println("Taken time for "+numberOfThreads+"\t Thread is : "+executionTime +"\t ms");

        }
    }

    @Test
    public void testTrivialStrategy(){
        runTestForStrategy(new TrivialPrimesFindStrategy(),"Trivial Strategy");
    }
    @Test
    public void testSieveOfEratosthenesStrategy(){
        runTestForStrategy(new SieveOfEratosthenesStrategy(),"Eratosthenes Strategy");
    }
    @Test
    public void testSieveOfAtkinStrategy(){
        runTestForStrategy(new SieveOfAtkinStrategy(),"Atkin Strategy");
    }

    // helper function to run performance test for a specified strategy and return the execution time
    private long runPerformanceTest(PrimeNumberService strategy, int intervalEnd, int numberOfThreads) {

        int intervalStart = 1;

        // create the coordinator
         threadsCoordinator = new ThreadsCoordinator(strategy, intervalStart, intervalEnd, numberOfThreads);

        // assign the work
        threadsCoordinator.assignWork();

        // set the timer
        long startTime = System.currentTimeMillis();

        threadsCoordinator.startWork();

        primes = threadsCoordinator.getResult();

        // end the timer
        long endTime = System.currentTimeMillis();

        return endTime - startTime;

    }


    // helper function to run test for a specified strategy
    private void runTestForStrategy(PrimesFindStrategy strategy, String strategyName) {

        Map<String, Long> results = new HashMap<>();

        // Iterate over each interval
        for (int interval : intervals) {

            // Iterate over the different numbers of threads
            for (int threads : threadsNumber) {
                // Measure execution time
                long timeTaken = runPerformanceTest(new PrimeNumberService(strategy), interval, threads);

                // Store result in the map
                String resultKey = strategyName + ", Interval: " + interval + "\t, Threads: " + threads;
                results.put(resultKey, timeTaken);

                // Print the result for each test case
                System.out.println(resultKey + "\t Time: " + timeTaken + "\tms");
            }
            System.out.println("\n++++++++++++++++++++++++++++++\n");
        }

    }

    // helper function to print the results of the best threads number for the strategy
    private void printResults(Map<String, Long> results) {
        System.out.println("\n==== Performance Test Results ====\n");
        for (Map.Entry<String, Long> entry : results.entrySet()) {
            System.out.println(entry.getKey() + " -> Time: " + entry.getValue() + " ms");
        }
    }
}
