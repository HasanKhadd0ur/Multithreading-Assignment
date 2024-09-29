package multithreading.imageProcessing.helpers;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


public class ImageLoadingHelper {

    public static BufferedImage LoadImage(String imageUrL) throws IOException {

        //System.out.println(imageUrL);

        BufferedImage image = ImageIO.read(new File(imageUrL));

        return  image ;
    }
}
