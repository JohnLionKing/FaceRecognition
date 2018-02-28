import static com.googlecode.javacv.cpp.opencv_core.IPL_DEPTH_8U;
import static com.googlecode.javacv.cpp.opencv_highgui.cvLoadImage;
import static com.googlecode.javacv.cpp.opencv_imgproc.CV_BGR2GRAY;
import static com.googlecode.javacv.cpp.opencv_imgproc.cvCvtColor;

import com.googlecode.javacv.cpp.opencv_contrib.FaceRecognizer;
import static com.googlecode.javacv.cpp.opencv_contrib.createFisherFaceRecognizer;
import static com.googlecode.javacv.cpp.opencv_contrib.createLBPHFaceRecognizer;
import com.googlecode.javacv.cpp.opencv_core;
import static com.googlecode.javacv.cpp.opencv_core.IPL_DEPTH_8U;
import com.googlecode.javacv.cpp.opencv_core.IplImage;
import com.googlecode.javacv.cpp.opencv_core.MatVector;
import static com.googlecode.javacv.cpp.opencv_core.cvCreateImage;
import static com.googlecode.javacv.cpp.opencv_core.cvGetSize;
import static com.googlecode.javacv.cpp.opencv_core.cvSetImageROI;
import static com.googlecode.javacv.cpp.opencv_imgproc.CV_BGR2GRAY;
import static com.googlecode.javacv.cpp.opencv_imgproc.CV_INTER_LINEAR;
import static com.googlecode.javacv.cpp.opencv_imgproc.cvCvtColor;
import static com.googlecode.javacv.cpp.opencv_imgproc.cvEqualizeHist;
import static com.googlecode.javacv.cpp.opencv_imgproc.cvResize;
import java.io.FilenameFilter;

import java.io.File;

public class FaceRecognition {

    public static FaceRecognizer fr_binary = null;
    public static String faceDataFolder = "C:\\opencv\\";
    public static String imageDataFolder = faceDataFolder + "faces\\";
    public static String tempImageFolder = faceDataFolder + "temp\\";

    public static int identify(int index) {
        IplImage tempImage = cvLoadImage(tempImageFolder + "temp_" + index + ".png");
        //IplImage tempImage = cvLoadImage("E:\\tmp.png");

        File root = new File(imageDataFolder);
        //File root = new File("E:\\a\\");

        FilenameFilter pngFilter = new FilenameFilter() {
            public boolean accept(File dir, String name) {
                return name.toLowerCase().endsWith(".png");
            }
        };

        File[] imageFiles = root.listFiles(pngFilter);
        
        if(imageFiles.length == 0)
            return -1;

        MatVector images = new MatVector(imageFiles.length);

        int[] labels = new int[imageFiles.length];

        int counter = 0;
        int label;

        IplImage img;
        IplImage grayImg;

        for (File image : imageFiles) {
            img = cvLoadImage(image.getAbsolutePath());
            label = Integer.parseInt(image.getName().split("_")[0]);
            grayImg = IplImage.create(img.width(), img.height(), IPL_DEPTH_8U, 1);
            cvCvtColor(img, grayImg, CV_BGR2GRAY);
            cvEqualizeHist(grayImg, grayImg);
            
            images.put(counter, grayImg);
            labels[counter] = label;
            counter++;
        }

        IplImage greyTestImage = IplImage.create(tempImage.width(), tempImage.height(), IPL_DEPTH_8U, 1);

        FaceRecognizer faceRecognizer = createFisherFaceRecognizer();
        // FaceRecognizer faceRecognizer = createEigenFaceRecognizer();
        //FaceRecognizer faceRecognizer = createLBPHFaceRecognizer(1, 8, 8, 8, 100);

        faceRecognizer.train(images, labels);

        cvCvtColor(tempImage, greyTestImage, CV_BGR2GRAY);
        cvEqualizeHist(greyTestImage, greyTestImage);
        
        int[] predictedLabel = new int[1];
        double[] distance = new double[1];
        faceRecognizer.predict(greyTestImage, predictedLabel, distance);

        System.out.println(predictedLabel[0] + "      " + distance[0]);
        return predictedLabel[0];
    }
}