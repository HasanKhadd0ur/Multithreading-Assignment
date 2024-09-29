package multithreading.imageProcessing.workers;

import multithreading.imageProcessing.services.ImageRecolorService;

import java.awt.image.BufferedImage;

public class ImageRecoloringWorker implements  Runnable{

    private ImageRecolorService imageRecolorService ;
    private BufferedImage resultImage ;
    private int leftCorener ;
    private int rightCorner;
    private int height ;
    private  int width;

    public ImageRecoloringWorker(
            ImageRecolorService imageRecolorService,
            BufferedImage resultImage,
            int leftCorener,
            int rightCorner,
            int height,
            int width) {

        this.imageRecolorService = imageRecolorService;
        this.resultImage = resultImage;
        this.leftCorener = leftCorener;
        this.rightCorner = rightCorner;
        this.height = height;
        this.width = width;
    }

    @Override
    public void run() {

        imageRecolorService.recolorImage(resultImage,leftCorener,rightCorner,width,height);
    }
}
