
import com.googlecode.javacv.cpp.opencv_core;
import static com.googlecode.javacv.cpp.opencv_core.cvCreateImage;
import static com.googlecode.javacv.cpp.opencv_core.cvFlip;
import static com.googlecode.javacv.cpp.opencv_core.cvGetSize;
import static com.googlecode.javacv.cpp.opencv_highgui.cvLoadImage;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.imageio.ImageIO;
import sun.misc.BASE64Decoder;


public class Validate {
    
    public static Map<String, Integer> checkUser(String[] faces) throws IOException 
    {
        Map<Integer, Integer> map = new HashMap<>();
        
        int index = 0;
        for(String face : faces)
        {
            String base64Image = face.split(",")[1];
            saveTempImage(base64Image, index);
            index ++;
        }
        
        for(int i = 0; i < 10; i ++) {
            int predictId = FaceRecognition.identify(i);
            if(map.containsKey(predictId))
                map.put(predictId, map.get(predictId) + 1);
            else map.put(predictId, 1);
        }
            
        
        int mostPredictId = 0;
        int maxCount = 0;
        for(Map.Entry<Integer, Integer> item : map.entrySet()) {
            Integer key = item.getKey();
            Integer value = item.getValue();
            
            if(maxCount < value) {
                mostPredictId = key;
                maxCount = value;
            }
        }
        
        Map<String, Integer> resultMap = new HashMap<>();
        resultMap.put("mostPredictId", mostPredictId);
        resultMap.put("maxCount", maxCount);
        return resultMap;
    }
    
    public static boolean saveTempImage(String base64String, int index) throws IOException{
        //byte[] imageBytes = javax.xml.bind.DatatypeConverter.parseBase64Binary(base64String);
        BufferedImage image = null;
        BASE64Decoder decoder = new BASE64Decoder();
        byte[] imageByte = decoder.decodeBuffer(base64String);
        ByteArrayInputStream bis = new ByteArrayInputStream(imageByte);
        image = ImageIO.read(bis);
        bis.close();
        String tempImageFolder = FaceRecognition.tempImageFolder;
        File outputFile = new File(tempImageFolder + "temp_" + index + ".png");
        boolean result = ImageIO.write(image, "png", outputFile);
        opencv_core.IplImage img = cvLoadImage(tempImageFolder + "temp_" + index + ".png");
        
        image = null;
        decoder = null;
        imageByte = null;
        bis.close();
        outputFile = null;
        return result;
    }  
}

