package core;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Vector;

public class Connection implements Runnable {
	
	private static Vector<Thread> threads_;
	private static Router router_;
	private static boolean shutdownFlag_;
	
	private Socket socket_;
	private BufferedReader socketReader_;
	private BufferedWriter socketWriter_;
	
	private Connection(Socket socket) {
		this.socket_ = socket;
		
		try {
			this.socketReader_ = new BufferedReader(new InputStreamReader(this.socket_.getInputStream()));
			this.socketWriter_ = new BufferedWriter(new OutputStreamWriter(this.socket_.getOutputStream()));
		} catch (IOException e) {
			System.out.println("IO Exception setting up readers and writers in Connection class.");
			e.printStackTrace();
		}
	}
	
	public void run() {
		// TODO write this run method
		
		// TODO figure out who's connecting. Store username. 
		
		// TODO start regular loop 
		
		// TODO perhaps we can have these connections specialize? Probably not right now. 
		
		while (!Connection.shutdownFlag_) {
			
			// TODO there will be a shutdown command that the client can send. 
		}
	}
	
	public static void init() {
		Connection.threads_ = new Vector<Thread>();
		Connection.router_ = new Router();
	}
	
	public static void joinAll() {
		Connection.shutdownFlag_ = true;
		
		for (Thread thread : Connection.threads_) {
			try {
				thread.join();
			} catch (InterruptedException e) {
				// Keep trying; eventually they'll finish or the user will use CTRL-C. 
			}
		}
	}
	
	public static void launchConnectionThread(Socket socket) {
		Thread thread = new Thread(new Connection(socket));
		thread.start();
		Connection.threads_.add(thread);
	}

}
