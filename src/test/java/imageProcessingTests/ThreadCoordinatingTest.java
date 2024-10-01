package imageProcessingTests;

import junit.framework.TestCase;
import multithreading.imageProcessing.coordinators.ThreadsCoordinator;
import multithreading.imageProcessing.helpers.ImageLoadingHelper;
import multithreading.imageProcessing.services.ImageRecolorService;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

import java.awt.image.BufferedImage;
import java.io.IOException;

public class ThreadCoordinatingTest extends TestCase {

    public static final String SOURCE_FILE = "C:\\Users\\HASAN\\Desktop\\Multithreading-Assignment\\src\\main\\resources\\many-flowers.jpg";
    public static final String DESTINATION_FILE = "C:\\Users\\HASAN\\Desktop\\Multithreading-Assignment\\src\\main\\resources\\many-flowers-out.jpg";

    private ImageRecolorService imageRecolorSVC;
    private BufferedImage originalImage ;
    private BufferedImage resultImage ;
    private long startTime, endTime;
    private int numberOfThreads =100;
    private ThreadsCoordinator threadsCoordinator;

    @BeforeEach
    public void setUp() throws IOException {
        originalImage = ImageLoadingHelper.LoadImage(SOURCE_FILE);
        resultImage = new BufferedImage(originalImage.getWidth(), originalImage.getHeight(), BufferedImage.TYPE_INT_RGB);
        imageRecolorSVC =new ImageRecolorService(originalImage);
        threadsCoordinator = new ThreadsCoordinator(imageRecolorSVC,resultImage,numberOfThreads);

    }


    @Test
    public void testAssignHorizontalSliceWork() {

        // Assign the works to the threads
        threadsCoordinator.assignHorizontalSliceWork();

        // Start the timer
        startTime = System.currentTimeMillis();

        // Start the work (running the threads)
        threadsCoordinator.startWork();

        // Get the result (join the threads)
        threadsCoordinator.getResult();

        // Get time at the end
        endTime = System.currentTimeMillis();

        System.out.println("Horizontal-based Slicing Duration with " + numberOfThreads + " threads: " + (endTime - startTime) + "ms");

    }

    @Test
    public void testAssignBlockSliceWork() {

        // assign the works to the threads
        threadsCoordinator.assignBlockWork(500,500);

        // start the timer
        startTime = System.currentTimeMillis();

        // start the work (running the threads)
        threadsCoordinator.startWork();

        // get the result (join the threads)
        threadsCoordinator.getResult();

        // get time at the end
        endTime = System.currentTimeMillis();

        System.out.println("Horizontal-based Slicing Duration with " + numberOfThreads + " threads: " + (endTime - startTime) + "ms");

    }

    @Test
    public  void testHorizontalSlicingPerformance(){
        // threads number for Block Slicing
        int[] threadCounts = {1, 2, 4, 16, 64,100};

        for (int numberOfThreads : threadCounts) {

            // Create the ThreadsCoordinator
            ThreadsCoordinator threadsCoordinator = new ThreadsCoordinator(imageRecolorSVC, resultImage, numberOfThreads);

            // Assign the work to the threads
            threadsCoordinator.assignHorizontalSliceWork();

            // Start Thre Timer
            long startTime = System.currentTimeMillis();

            // Start the work
            threadsCoordinator.startWork();

            // Get  that the result
            BufferedImage finalResult = threadsCoordinator.getResult();

            long endTime = System.currentTimeMillis();

            long duration = endTime - startTime;

            // Print the performance results
            System.out.println("Time taken with " + numberOfThreads + " threads: " + duration + " ms");

            assertNotNull("The result image should not be null", finalResult);
        }
    }

    @Test
    public  void testBlockSlicingPerformance(){

        // threads number for Block Slicing
        int[] threadCounts = {1, 2, 4, 16, 64,100};

        for (int numberOfThreads : threadCounts) {

            // Create the ThreadsCoordinator
            ThreadsCoordinator threadsCoordinator = new ThreadsCoordinator(imageRecolorSVC, resultImage, numberOfThreads);

            // Assign the work to the threads
            threadsCoordinator.assignBlockWork(400,400);

            // Start Thre Timer
            long startTime = System.currentTimeMillis();

            // Start the work
            threadsCoordinator.startWork();

            // Get  that the result
            BufferedImage finalResult = threadsCoordinator.getResult();

            long endTime = System.currentTimeMillis();

            long duration = endTime - startTime;

            // Print the performance results
            System.out.println("Time taken with " + numberOfThreads + " threads: " + duration + " ms");

            assertNotNull("The result image should not be null", finalResult);
        }
    }

}
