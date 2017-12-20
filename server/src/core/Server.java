package core;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;

public class Server implements Runnable {
	
	/*
	 * TODO 
	 * 
	 * For now, I'm just going to spawn a new thread for every connection. I've heard that
	 * that doesn't scale very well, but it's fine for the time being. 
	 */
	
	private ServerSocket serverSocket_;
	private boolean shutdownFlag_;
	
	public static void main(String[] args) {
		Server server = new Server();
		server.init(1234);
		
		Thread serverThread = new Thread(server);
		serverThread.start();
		
		// Control loop
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		String input = "";
		try {
			while (!(input = reader.readLine()).equals("quit")) {
				System.out.println("\"" + input + "\" is not a valid command.");
			}
			
			reader.close();
		} catch (IOException e1) {
			System.out.println("IO Exception from control loop. Might as well exit to be on the safe side.");
			e1.printStackTrace();
		}
		
		server.shutdown();
		try {
			serverThread.join();
		} catch (InterruptedException e) {
			System.out.println("Interruption during join... well, we were terminating anyway.");
			e.printStackTrace();
		}
	}

	public void run() {
		while (!this.shutdownFlag_) {
			try {
				Connection.launchConnectionThread(this.serverSocket_.accept());
			} catch (IOException e) {
				System.out.println("Failed to accept new connection.");
				e.printStackTrace();
			}
		}
		
		Connection.joinAll();
	}
	
	private void init(int port) {
		Connection.init();
		
		try {
			this.serverSocket_ = new ServerSocket(port);
		} catch (IOException e) {
			System.out.println("Could not start server.");
			e.printStackTrace();
		}
	}
	
	private void shutdown() {
		this.shutdownFlag_ = true;
	}

}
