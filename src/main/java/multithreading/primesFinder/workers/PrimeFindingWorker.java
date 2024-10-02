package multithreading.primesFinder.workers;

import multithreading.primesFinder.abstraction.PrimesFindStrategy;
import multithreading.primesFinder.services.PrimeNumberService;

import java.util.List;

public class PrimeFindingWorker implements  Runnable{

    private PrimeNumberService primeNumberService ;
    private List<Integer> primeNumbers ;
    private  int intervalStart, intervalEnd ;

    public PrimeFindingWorker(PrimeNumberService primeNumberService, int intervalStart, int intervalEnd) {
        this.primeNumberService = primeNumberService;
        this.intervalStart = intervalStart;
        this.intervalEnd = intervalEnd;
    }

    @Override
    public void run() {

        primeNumbers=primeNumberService.findPrimes(intervalStart,intervalEnd);

    }

    public List<Integer> getPrimeNumbers() {
        return primeNumbers;
    }

}
