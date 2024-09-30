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

    @BeforeEach
    public void setUp() throws IOException {
        originalImage = ImageLoadingHelper.LoadImage(SOURCE_FILE);
        resultImage = new BufferedImage(originalImage.getWidth(), originalImage.getHeight(), BufferedImage.TYPE_INT_RGB);
        imageRecolorSVC =new ImageRecolorService(originalImage);

    }


    @Test
    public void testAssignHorizontalSliceWork() {

        ThreadsCoordinator threadsCoordinator = new ThreadsCoordinator(imageRecolorSVC,resultImage,numberOfThreads);

        // assign the works to the threads
        threadsCoordinator.assignHorizontalSliceWork();

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
    public void testAssignBlockSliceWork() {

        ThreadsCoordinator threadsCoordinator = new ThreadsCoordinator(imageRecolorSVC,resultImage,numberOfThreads);

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

}
