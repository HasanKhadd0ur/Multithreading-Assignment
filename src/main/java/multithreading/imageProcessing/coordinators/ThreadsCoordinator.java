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
    private  BufferedImage resultImage;
    private List<Thread> threads;
    private int numberOfThreads;


    public ThreadsCoordinator(ImageRecolorService imageRecolorService, BufferedImage resultImage ,int numberOfThreads) {
        this.imageRecolorService = imageRecolorService;
        this.resultImage = resultImage;
        this.threads = new ArrayList<>();
        this.numberOfThreads=numberOfThreads;
    }

    // This Function for divid the image into horizontal slices and assign each slice to a thread
    public  void assignHorizontalSliceWork(){

        // Divide Image to horizontal sclices
        int width = imageRecolorService.getOriginalImage().getWidth();
        int height = imageRecolorService.getOriginalImage().getHeight() / numberOfThreads;

        // Assign each horizontal slice to a thread
        for(int i = 0; i < numberOfThreads ; i++) {

            final int threadMultiplier = i;

            // the thread take a worker that is responsible for recoloring a block
            Thread thread = new Thread( new ImageRecoloringWorker(
                    imageRecolorService,
                    resultImage,
                    0,
                    height*threadMultiplier,
                    width,
                    height
            ));

            threads.add(thread);
        }
    }

    // This Function responsible for divide the image into blocks and assign each group of blocks to a thread
    public  void assignBlockWork(int blockWidth , int blockHeight){

        int width = imageRecolorService.getOriginalImage().getWidth();
        int height = imageRecolorService.getOriginalImage().getHeight();

        // Iterate Over the image and divided it into blocks
        for (int x = 0; x < width; x += blockWidth) {
            for (int y = 0; y < height; y += blockHeight) {

                // Calculate the width and height for the current block (to avoid non divisibility issues)
                int currentBlockWidth = Math.min(blockWidth, width - x);
                int currentBlockHeight = Math.min(blockHeight, height - y);

                // the thread take a worker that is responsible for recoloring a block
                Thread thread = new Thread(new ImageRecoloringWorker(
                        imageRecolorService,
                        resultImage,
                        x,
                        y,
                        currentBlockWidth,
                        currentBlockHeight
                ));

                // Add the thread to the list of threads
                threads.add(thread);
            }
        }
    }

    // This Function responsible for start all threads
    public void startWork(){

        for (Thread thread : threads){
            thread.start();
        }
    }


    // this Function responsible for join all threads and return the result
    public BufferedImage getResult(){

        for (Thread thread : threads){
            try {
                thread.join();
            } catch (InterruptedException e) {

            }

        }

        return  resultImage;

    }
}
