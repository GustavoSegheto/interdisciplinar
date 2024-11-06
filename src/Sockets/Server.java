package Sockets;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.videoio.VideoCapture;
import org.opencv.imgcodecs.Imgcodecs;

import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) {
        
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

        try (ServerSocket serverSocket = new ServerSocket(5000)) {
            System.out.println("Aguardando conexão do cliente...");
            Socket clientSocket = serverSocket.accept();
            System.out.println("Cliente conectado.");

            
            VideoCapture camera = new VideoCapture(0);
            if (!camera.isOpened()) {
                System.out.println("Erro ao abrir a câmera.");
                return;
            }

           
            ObjectOutputStream outputStream = new ObjectOutputStream(clientSocket.getOutputStream());

            Mat frame = new Mat();
            MatOfByte matOfByte = new MatOfByte();

            while (true) {
                if (camera.read(frame)) {
                   
                    Imgcodecs.imencode(".jpg", frame, matOfByte);
                    byte[] imageBytes = matOfByte.toArray();

                    
                    outputStream.writeObject(imageBytes);
                    outputStream.flush();

                    Thread.sleep(22);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
