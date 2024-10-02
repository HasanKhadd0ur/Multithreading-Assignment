package primeFindingTests;

import junit.framework.TestCase;
import multithreading.primesFinder.abstraction.PrimesFindStrategy;
import multithreading.primesFinder.services.PrimeNumberService;
import multithreading.primesFinder.strategies.SieveOfAtkinStrategy;
import multithreading.primesFinder.strategies.SieveOfEratosthenesStrategy;
import multithreading.primesFinder.strategies.TrivialPrimesFindStrategy;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PrimeNumberServiceTest extends TestCase {

    private PrimeNumberService primeNumberService;
    private List<PrimesFindStrategy> primesFindStrategies;

    @BeforeEach()
    public  void setUp(){

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
    public void testPrimeService_ReturnsExpectedPrimes() {

        // loop over all of our strategies
        for (PrimesFindStrategy strategy : primesFindStrategies){
            primeNumberService = new PrimeNumberService(strategy);

            // get the primes numbers in the 1 10 interval
            List<Integer> primes = primeNumberService.findPrimes(1, 10);

            // verify that the returned list matches the expected values
            assertEquals("the Strategy "+strategy.getClass().getName()+"Doesnot work",Arrays.asList(2, 3, 5, 7), primes);
        }
    }

    @Test
    public void testPrimeService_EmptyResult() {

        // loop over all of our strategies
        for (PrimesFindStrategy strategy : primesFindStrategies) {

            // create a prime numbers service
            primeNumberService = new PrimeNumberService(strategy);

            // get the primes numbers in the 20 22 interval should be empty
            List<Integer> primes = primeNumberService.findPrimes(20, 22);

            // verify that the result is an empty list
            assertEquals(0, primes.size());
        }
    }

    @Test
    public void testPrimeService_PrimesCount() {
        // loop over all of our strategies
        for (PrimesFindStrategy strategy : primesFindStrategies) {

            // create a prime numbers service
            primeNumberService = new PrimeNumberService(strategy);


            // get the primes numbers in the 20 22 interval should be empty
            List<Integer> primes = primeNumberService.findPrimes(2, 1000000);

            // verify that the result is an empty list
            assertEquals(78498, primes.size());

        }
    }


}
