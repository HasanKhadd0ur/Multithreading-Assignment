package multithreading.primesFinder.services;

import multithreading.primesFinder.abstraction.PrimesFindStrategy;

import java.util.List;

public class PrimeNumberService {

    private PrimesFindStrategy primesFindStrategy ;
    public PrimeNumberService(PrimesFindStrategy primesFindStrategy ){

        this.primesFindStrategy = primesFindStrategy;
    }

    public List<Integer> findPrimes(int intervalStart, int intervalEnd){

        return  primesFindStrategy.findPrimes(intervalStart,intervalEnd);
    }
}
