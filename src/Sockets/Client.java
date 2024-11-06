package Sockets;

import FaceDetectionOPENCV.FaceDetect;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;

import javax.swing.*;
import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import org.opencv.core.MatOfByte;
import org.opencv.core.MatOfRect;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

public class Client {

    public static void main(String[] args) {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

        try (Socket socket = new Socket("localhost", 5000)) {
            System.out.println("Conectado ao servidor.");

            ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());

            JFrame frame = new JFrame("Cliente de Vídeo");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            JLabel label = new JLabel();
            frame.getContentPane().add(label);
            frame.setSize(640, 480);
            frame.setVisible(true);

            int frameCounter = 0;
            Rect[] storedFaceDetections = new Rect[0]; 

            while (true) {
                
                byte[] bytes = (byte[]) inputStream.readObject();

                
                Mat image = Imgcodecs.imdecode(new MatOfByte(bytes), Imgcodecs.IMREAD_UNCHANGED);

               
                if (frameCounter == 0) {
                    MatOfRect faceDetections = FaceDetect.reconhecimento(image); 
                    storedFaceDetections = faceDetections.toArray(); 
                }

               
                for (Rect rect : storedFaceDetections) {
                    Imgproc.rectangle(
                            image, new Point(rect.x, rect.y),
                            new Point(rect.x + rect.width, rect.y + rect.height),
                            new Scalar(0, 255, 0),
                            2
                    );
                }

                
                BufferedImage buff = FaceDetect.MatToBufferedImage(image);
                if (buff != null) {
                    label.setIcon(new ImageIcon(buff));
                    frame.repaint();
                }

                
                frameCounter = (frameCounter + 1) % 6;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
