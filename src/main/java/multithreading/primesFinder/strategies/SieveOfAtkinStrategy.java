package multithreading.primesFinder.strategies;

import multithreading.primesFinder.abstraction.PrimesFindStrategy;

import java.util.Arrays;
import java.util.*;


public class SieveOfAtkinStrategy implements PrimesFindStrategy {

    @Override
    public boolean isPrime(int number) {
        if (number <= 1) return false;
        if (number == 2 || number == 3) return true;
        if (number % 2 == 0 || number % 3 == 0) return false;

        for (int i = 5; i * i <= number; i += 6) {
            if (number % i == 0 || number % (i + 2) == 0) return false;
        }
        return true;

    }

    @Override
    public List<Integer> findPrimes(int start, int end) {

        boolean[] isPrime = new boolean[end + 1];
        Arrays.fill(isPrime, false);

        // Sieve of Atkin Algorithm
        for (int x = 1; x * x <= end; x++) {
            for (int y = 1; y * y <= end; y++) {
                int n = (4 * x * x) + (y * y);
                if (n <= end && (n % 12 == 1 || n % 12 == 5)) {
                    isPrime[n] = !isPrime[n];
                }
                n = (3 * x * x) + (y * y);
                if (n <= end && n % 12 == 7) {
                    isPrime[n] = !isPrime[n];
                }
                n = (3 * x * x) - (y * y);
                if (x > y && n <= end && n % 12 == 11) {
                    isPrime[n] = !isPrime[n];
                }
            }
        }

        // Mark multiples of squares as non-prime
        for (int r = 5; r * r <= end; r++) {
            if (isPrime[r]) {
                for (int i = r * r; i <= end; i += r * r) {
                    isPrime[i] = false;
                }
            }
        }

        // Collect primes from the range
        List<Integer> primes = new ArrayList<>();
        if (start <= 2 && end >= 2) primes.add(2);
        if (start <= 3 && end >= 3) primes.add(3);

        for (int i = Math.max(5, start); i <= end; i++) {
            if (isPrime[i]) primes.add(i);
        }

        return primes;
    }
}
