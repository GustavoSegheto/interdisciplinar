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
            Rect[] storedFaceDetections = new Rect[0]; // Array para armazenar as detecções anteriores

            while (true) {
                // Recebendo os bytes da imagem
                byte[] bytes = (byte[]) inputStream.readObject();

                // Decodificando os bytes para um Mat
                Mat image = Imgcodecs.imdecode(new MatOfByte(bytes), Imgcodecs.IMREAD_UNCHANGED);

                // Atualiza as detecções de rosto apenas a cada 6 frames
                if (frameCounter == 0) {
                    MatOfRect faceDetections = FaceDetect.reconhecimento(image); // Chama o método de reconhecimento
                    storedFaceDetections = faceDetections.toArray(); // Armazena as novas detecções
                }

                // Desenha os retângulos usando as detecções armazenadas
                for (Rect rect : storedFaceDetections) {
                    Imgproc.rectangle(
                            image, new Point(rect.x, rect.y),
                            new Point(rect.x + rect.width, rect.y + rect.height),
                            new Scalar(0, 255, 0),
                            2
                    );
                }

                // Converte o Mat (com retângulos) para BufferedImage e exibe
                BufferedImage buff = FaceDetect.MatToBufferedImage(image);
                if (buff != null) {
                    label.setIcon(new ImageIcon(buff));
                    frame.repaint();
                }

                // Incrementa o contador ou reseta após 6 frames
                frameCounter = (frameCounter + 1) % 6;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}