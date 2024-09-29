package multithreading.imageProcessing.services;

import multithreading.imageProcessing.helpers.ColorHelper;

import java.awt.image.BufferedImage;

public class ImageRecolorService {

    private  BufferedImage originalImage;

    public ImageRecolorService(BufferedImage originalImage) {
        this.originalImage = originalImage;
    }



    public  BufferedImage recolorImage( BufferedImage resultImage, int leftCorner, int topCorner , int width, int height) {


        for(int x = leftCorner ; x < leftCorner + width && x < originalImage.getWidth() ; x++) {
            for(int y = topCorner ; y < topCorner + height && y < originalImage.getHeight() ; y++) {
                ColorHelper.recolorPixel(originalImage, resultImage, x , y);
            }
        }

        return  resultImage ;
    }

    public BufferedImage getOriginalImage() {
        return originalImage;
    }
}
