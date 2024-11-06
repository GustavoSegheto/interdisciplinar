
package FaceDetectionOPENCV;

/**
 *
 * @author 0074942
 */
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.core.MatOfRect;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;

public class FaceDetect {
    
    public static BufferedImage MatToBufferedImage(Mat mat) throws IOException{
      //Encoding the image
      MatOfByte matOfByte = new MatOfByte();
      Imgcodecs.imencode(".jpg", mat, matOfByte);
      //Storing the encoded Mat in a byte array
      byte[] byteArray = matOfByte.toArray();
      //Preparing the Buffered Image
      InputStream in = new ByteArrayInputStream(byteArray);
      BufferedImage bufImage = ImageIO.read(in);
      return bufImage;
   }
    
    
    public static MatOfRect reconhecimento(Mat image) {
        // TODO code application logic here
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

        CascadeClassifier faceDetector = new CascadeClassifier();
        faceDetector.load("haarcascade_frontalface_alt.xml");
        

        MatOfRect faceDetections = new MatOfRect();
        faceDetector.detectMultiScale(image, faceDetections);

        /*for (Rect rect : faceDetections.toArray()) {
            Imgproc.rectangle(
                    image, new Point(rect.x, rect.y),
                    new Point(rect.x + rect.width, rect.y + rect.height),
                    new Scalar(0, 255, 0),
                    2
                    );
        }*/

        return faceDetections;
        
    } 
    
}