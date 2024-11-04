package imagem;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Cliente {

	
	public static void main(String[] args) {
	
		try {
			
			byte[] imageData = Files.readAllBytes(Paths.get("image.jpeg"));
			
			Socket socket = new Socket("localhost", 602);
			
			OutputStream outputstream = socket.getOutputStream();
			
			outputstream.write(imageData);
			
			outputstream.flush();
			
			outputstream.close();
			socket.close();
			
			
		}catch(IOException e) {
			e.printStackTrace();
		}
		
	}
	
}
