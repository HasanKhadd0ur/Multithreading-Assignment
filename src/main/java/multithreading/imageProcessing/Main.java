package multithreading.imageProcessing;

import multithreading.imageProcessing.coordinators.ThreadsCoordinator;
import multithreading.imageProcessing.helpers.ImageLoadingHelper;
import multithreading.imageProcessing.services.ImageRecolorService;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Main {

    public static final String SOURCE_FILE = "C:\\Users\\HASAN\\Desktop\\Multithreading-Assignment\\src\\main\\resources\\many-flowers.jpg";
    public static final String DESTINATION_FILE = "C:\\Users\\HASAN\\Desktop\\Multithreading-Assignment\\src\\main\\resources\\many-flowers-out.jpg";

    public static void main(String[] args) throws IOException {


        // *Note: this is a simple test for single thread check of its work
        //

        // this is for time monitoring
        long startTime, endTime;
        int numberOfThreads =10;

        // read the input image and result image
        BufferedImage originalImage = ImageLoadingHelper.LoadImage(SOURCE_FILE);
        BufferedImage resultImage = new BufferedImage(originalImage.getWidth(), originalImage.getHeight(), BufferedImage.TYPE_INT_RGB);

        // define the image service
        ImageRecolorService imageRecolorSVC = new ImageRecolorService(originalImage);

        //define the threads manager
        ThreadsCoordinator threadsCoordinator = new ThreadsCoordinator(imageRecolorSVC,resultImage,numberOfThreads);


        // assign the works to the threads
        //threadsCoordinator.assignHorizontalSliceWork();
        threadsCoordinator.assignBlockWork(100,100);

        // start the timer
        startTime = System.currentTimeMillis();

        // start the work (running the threads)
        threadsCoordinator.startWork();

        // get the result (join the threads)
        threadsCoordinator.getResult();

        // get time at the end
        endTime = System.currentTimeMillis();

        System.out.println("Horizontal-based Duration with " + numberOfThreads + " threads: " + (endTime - startTime) + "ms");

        // write the recoloring image result
        File outputFile = new File(DESTINATION_FILE);
        ImageIO.write(resultImage, "jpg", outputFile);


    }



}

