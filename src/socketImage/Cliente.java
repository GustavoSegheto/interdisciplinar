package socketImage;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Cliente {

	
	public static void main(String[] args) {
	
		try {
			
		
			
			Socket socket = new Socket("localhost", 5555);
			
			InputStream inputStream = socket.getInputStream();
			
			ByteArrayOutputStream buffer = new ByteArrayOutputStream();
			
			int nRead;
			byte[] data = new byte[1024];
			while((nRead = inputStream.read(data, 0, data.length)) != -1) {
				buffer.write(data, 0 , nRead);
			}
			
			buffer.flush();
			byte[] imageData = buffer.toByteArray();
			
			FileOutputStream outputStream = new FileOutputStream("recievedImage.jpeg");
			outputStream.write(imageData);
			
			outputStream.close();
			
			inputStream.close();
			socket.close();
			
			
		}catch(IOException e) {
			e.printStackTrace();
		}
		
	}
	
}
