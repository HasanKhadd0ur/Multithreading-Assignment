package multithreading.primesFinder.strategies;

import  multithreading.primesFinder.abstraction.PrimesFindStrategy;

import java.util.ArrayList;
import java.util.List;

public class TrivialPrimesFindStrategy implements PrimesFindStrategy{

    @Override
    public List<Integer> findPrimes(int start, int end) {
        List<Integer> primes = new ArrayList<>();
            for (int number = start; number <= end; number++) {
            if (isPrime(number)) {
                primes.add(number);
            }
        }
        return primes;
    }

    public boolean isPrime(int number) {
        if (number <= 1) {
            return false;
        }
        for (int i = 2; i * i <= number; i++) {
            if (number % i == 0) {
                return false;
            }
        }
        return true;
    }
}
