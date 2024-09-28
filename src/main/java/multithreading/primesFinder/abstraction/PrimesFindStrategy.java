package multithreading.primesFinder.abstraction;

import java.util.List;

public interface PrimesFindStrategy {

    List<Integer> findPrimes(int start, int end);
}
