package imageProcessingTests;

import junit.framework.TestCase;
import multithreading.imageProcessing.coordinators.ThreadsCoordinator;
import multithreading.imageProcessing.helpers.ColorHelper;
import multithreading.imageProcessing.helpers.ImageLoadingHelper;
import multithreading.imageProcessing.services.ImageRecolorService;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

import java.awt.image.BufferedImage;
import java.io.IOException;

import static javax.management.Query.times;
import static org.junit.Assert.assertNotEquals;

public class ImageServiceTest extends TestCase {


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
    public void testRecolorImage_EmptySlice() {

        resultImage =imageRecolorSVC.recolorImage(resultImage, 0, 0, 0, 0);

        assertNotEquals( "The empty slice should have return the same image ",originalImage, resultImage);

    }


    @Test
    public void testGetOriginalImage() {

        // Verify the original image getter work
        assertEquals(originalImage, imageRecolorSVC.getOriginalImage());
    }
}
