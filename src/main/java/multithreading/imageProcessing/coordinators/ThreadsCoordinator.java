package multithreading.imageProcessing.coordinators;


import multithreading.imageProcessing.services.ImageRecolorService;
import multithreading.imageProcessing.workers.ImageRecoloringWorker;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

/**
 * The ThreadsCoordinator class is responsible for managing and coordinating multiple threads
 * for image processing tasks.
 * It can divide the image either into horizontal slices or into  blocks
 * It assign each part to a thread for recoloring.
 *
 **/
public class ThreadsCoordinator {

    private ImageRecolorService imageRecolorService ;
    private BufferedImage resultImage;
    private List<Thread> threads;
    private List<ImageRecoloringWorker> workers;
    private int numberOfThreads;


    public ThreadsCoordinator(ImageRecolorService imageRecolorService, BufferedImage resultImage ,int numberOfThreads) {
        this.imageRecolorService = imageRecolorService;
        this.resultImage = resultImage;
        this.threads = new ArrayList<>();
        this.numberOfThreads=numberOfThreads;
        this.workers=new ArrayList<>();
    }

    // This Function for divid the image into horizontal slices and assign each slice to a thread
    public  void assignHorizontalSliceWork(){

        // Divide Image to horizontal sclices
        int width = imageRecolorService.getOriginalImage().getWidth();
        int height = imageRecolorService.getOriginalImage().getHeight() / numberOfThreads;

        for (int i = 0; i < numberOfThreads; i++) {
            final int threadMultiplier = i;

            workers.add(new ImageRecoloringWorker(
                    imageRecolorService,
                    resultImage,
                    0,
                    height * threadMultiplier,
                    height,
                    width
            ));
        }


    }

    // This Function responsible for divide the image into blocks and assign each group of blocks to a thread
    public  void assignBlockWork(int blockWidth , int blockHeight){
        int width = imageRecolorService.getOriginalImage().getWidth();
        int height = imageRecolorService.getOriginalImage().getHeight();

        int blocksX = (int) Math.ceil((double) width / blockWidth);
        int blocksY = (int) Math.ceil((double) height / blockHeight);

        for (int blockX = 0; blockX < blocksX; blockX++) {
            for (int blockY = 0; blockY < blocksY; blockY++) {
                int startX = blockX * blockWidth;
                int startY = blockY * blockHeight;
                int currentBlockWidth = Math.min(blockWidth, width - startX);
                int currentBlockHeight = Math.min(blockHeight, height - startY);

                workers.add(new ImageRecoloringWorker(
                        imageRecolorService,
                        resultImage,
                        startX,
                        startY,
                        currentBlockHeight,
                        currentBlockWidth
                ));
            }
        }

    }

    // This Function responsible for start all threads
    public void startWork(){
        for (int i = 0; i < workers.size(); i++) {
            Thread thread = new Thread(workers.get(i));
            threads.add(thread);

            thread.start();
            // if all the threads are assigned
            if (i >= numberOfThreads) {
                try {
                    // Distribute the works By Round Robin Algo.
                    threads.get((i)% numberOfThreads).join();
                } catch (InterruptedException e) {

                }
            }
        }
    }


    // this Function responsible for join all threads and return the result
    public BufferedImage getResult(){

        waitForCompletion();
        return  resultImage;

    }

    private void createWorkers(int blockWidth, int blockHeight) {


        int width = imageRecolorService.getOriginalImage().getWidth();
        int height = imageRecolorService.getOriginalImage().getHeight();

        int blocksX = (int) Math.ceil((double) width / blockWidth);
        int blocksY = (int) Math.ceil((double) height / blockHeight);

        for (int blockX = 0; blockX < blocksX; blockX++) {
            for (int blockY = 0; blockY < blocksY; blockY++) {
                int startX = blockX * blockWidth;
                int startY = blockY * blockHeight;
                int currentBlockWidth = Math.min(blockWidth, width - startX);
                int currentBlockHeight = Math.min(blockHeight, height - startY);

                workers.add(new ImageRecoloringWorker(
                        imageRecolorService,
                        resultImage,
                        startX,
                        startY,
                        currentBlockHeight,
                        currentBlockWidth
                ));
            }
        }

    }

    // Assign threads based on the workers created
    private void assignThreads(List<ImageRecoloringWorker> workers) {
        for (int i = 0; i < workers.size(); i++) {
            Thread thread = new Thread(workers.get(i));
            threads.add(thread);

            thread.start();
            // if all the threads are assigned
            if (i >= numberOfThreads) {
                try {
                    // Wait for the oldest thread to finish before starting a new one
                    threads.get(i - numberOfThreads).join();
                } catch (InterruptedException e) {

                }
            }
        }
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
