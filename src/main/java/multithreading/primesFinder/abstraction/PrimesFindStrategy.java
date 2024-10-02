package multithreading.primesFinder.abstraction;

import java.util.List;

public interface PrimesFindStrategy {

    boolean isPrime(int number);
    List<Integer> findPrimes(int start, int end);
}
