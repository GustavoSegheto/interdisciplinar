package socketImage;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Servidor {

	
	public static void main(String[] args) {
	
		try {
			
			while(true) {
			
			ServerSocket serverSocket = new ServerSocket(5555);

			byte[] imageData = Files.readAllBytes(Paths.get("cruzeiro.jpg"));
			
			Socket socket = serverSocket.accept();
			
			OutputStream outputStream = socket.getOutputStream();
			
			outputStream.write(imageData);
			
			outputStream.flush();
			outputStream.close();
			socket.close();
			
			}
			
		}catch(IOException e) {
			e.printStackTrace();
		}
		
	}
	
}
