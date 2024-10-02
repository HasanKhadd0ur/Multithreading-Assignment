package multithreading.primesFinder.coordinators;

import multithreading.primesFinder.services.PrimeNumberService;
import multithreading.primesFinder.workers.PrimeFindingWorker;

import java.util.ArrayList;
import java.util.List;

public class ThreadsCoordinator {

    // business related properties
    private PrimeNumberService primeNumberService;
    private List<Integer> primesNumbers ;
    private int intervalStart, intervalEnd;

    // threads related properties
    private int rangePerThread;
    private List<Thread> threads;
    private List<PrimeFindingWorker> workers;
    private int numberOfThreads;

    public ThreadsCoordinator(PrimeNumberService primeNumberService, int intervalStart, int intervalEnd, int numberOfThreads) {

        this.primeNumberService = primeNumberService;

        this.intervalStart = intervalStart;
        this.intervalEnd = intervalEnd;

        this.rangePerThread = (intervalEnd - intervalStart + 1) / numberOfThreads;
        this.numberOfThreads = numberOfThreads;


        this.primesNumbers = new ArrayList<>();
        this.threads = new ArrayList<>();
        this.workers = new ArrayList<>();
    }


    // This Function for dividing the interval to sub intervals
    // and assign each sub interval to a worker (prime numbers finder)
    public  void assignWork(){

        for (int i = 0; i < numberOfThreads; i++) {
            // divid the interval for the i worker
            int rangeStart = intervalStart + i * rangePerThread;
            int rangeEnd = (i == numberOfThreads - 1) ? intervalEnd : rangeStart + rangePerThread - 1;
            // create the new worker
            PrimeFindingWorker worker = new PrimeFindingWorker(primeNumberService,rangeStart,rangeEnd);

            // add it to the workers list
            workers.add(worker);
        }
    }


    // This Function responsible for start all threads
    public void startWork(){
        for (int i = 0; i < workers.size(); i++) {

            // create a new thread for the i worker
            Thread thread = new Thread(workers.get(i));

            // add the thread to the threads list
            threads.add(thread);

            // start the thread
            thread.start();
        }
    }


    // this Function responsible for join all threads and return the result
    public List<Integer> getResult(){

        waitForCompletion();

        for (PrimeFindingWorker worker : workers){
            primesNumbers.addAll(worker.getPrimeNumbers());
        }

        return  primesNumbers;
    }


    private void waitForCompletion() {
        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {

            }
        }
    }
}
