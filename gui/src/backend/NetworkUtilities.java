package backend;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class NetworkUtilities {
	
	private Socket socket_; 
	private BufferedReader socketReader_;
	private BufferedWriter socketWriter_;
	
	protected void init(String serverHost, int serverPort) {
		try {
			this.socket_ = new Socket(serverHost, serverPort);
			this.socketReader_ = new BufferedReader(new InputStreamReader(this.socket_.getInputStream()));
			this.socketWriter_ = new BufferedWriter(new OutputStreamWriter(this.socket_.getOutputStream()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Could not connect to server.");
		}
	}
	
	protected String[] sendAndGetResponse(String[] request) {
		try {
			this.socketWriter_.write(String.join(" ", request));
			this.socketWriter_.newLine();
			this.socketWriter_.flush();
			
			return this.socketReader_.readLine().split(" ");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	protected void shutdown() {
		try {
			this.socketWriter_.close();
			this.socketReader_.close();
			this.socket_.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
