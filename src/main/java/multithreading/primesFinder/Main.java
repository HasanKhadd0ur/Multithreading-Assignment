package multithreading.primesFinder;

import multithreading.primesFinder.abstraction.PrimesFindStrategy;
import multithreading.primesFinder.coordinators.ThreadsCoordinator;
import multithreading.primesFinder.services.PrimeNumberService;
import multithreading.primesFinder.strategies.SieveOfAtkinStrategy;
import multithreading.primesFinder.strategies.SieveOfEratosthenesStrategy;
import multithreading.primesFinder.strategies.TrivialPrimesFindStrategy;

import java.util.ArrayList;
import java.util.List;

public class Main {

    private static List<Integer> primesNumbers = new ArrayList<>();

    public static void main(String[] args) {
        // *Note: this is a simple test for the servvice to check if its work before merge it to the testing branch
        //

        // define threads coordinator params
        int intervalStart = 2;
        int intervalEnd = 1000000;
        int numThreads = 10;

        // define the concrete strategy
        PrimesFindStrategy trivialStrategy = new TrivialPrimesFindStrategy();

        // define the primes number service
        PrimeNumberService primeNumberSVC = new PrimeNumberService(trivialStrategy);

        // create the coordinator
        ThreadsCoordinator coordinator = new ThreadsCoordinator(
                primeNumberSVC,
                intervalStart,
                intervalEnd,
                numThreads
        );

        // start the timer
        long startTime = System.currentTimeMillis();

        // assign the work (divide the intervals to sub intervals)
        coordinator.assignWork();

        // start the work (running the threads)
        coordinator.startWork();

        //get the results (join the threads)
        primesNumbers=coordinator.getResult();

        // stop the timer
        long endTime = System.currentTimeMillis();

        // showing the result
        System.out.println("trivial primes found: " + primesNumbers.size());
        System.out.println("Execution Time : " + (endTime - startTime) + " ms");


    }
}
