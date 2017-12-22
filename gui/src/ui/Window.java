package ui;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;

import backend.SessionManager;
import resources.Strings;

public class Window extends JFrame implements WindowListener {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -808477151995604656L;
	
	//private ConversationsPanel conversations_ = null;

	/*
	 * Initialize the window. 
	 */
	private void init(String username) {
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.addWindowListener(this);
		
		UiConfigData.init();
		
		// TODO load up existing data, if it's there
		// Put this loc call into UI config data class
		Strings.setLocalizationLanguage("epo");
		
		ChatPanel chatPanel = new ChatPanel();
		
		SessionManager.init(chatPanel, username);
		
		this.setContentPane(chatPanel);
		
		this.pack();
		this.setVisible(true);
	}
	

	public static void main(String[] args) {
		Window window = new Window();
		window.init(args[0]);
	}
	
	
	/*
	 * Window Listener methods
	 */

	@Override
	public void windowClosing(WindowEvent arg0) {
		SessionManager.shutdown();
	}

	@Override public void windowActivated(WindowEvent arg0) { /* Do nothing */ }
	@Override public void windowClosed(WindowEvent arg0) { /* Do nothing */ }
	@Override public void windowDeactivated(WindowEvent arg0) { /* Do nothing */ }
	@Override public void windowDeiconified(WindowEvent arg0) { /* Do nothing */ }
	@Override public void windowIconified(WindowEvent arg0) { /* Do nothing */ }
	@Override public void windowOpened(WindowEvent arg0) { /* Do nothing */ }

}
