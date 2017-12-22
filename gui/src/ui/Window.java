package ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

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
		
		// TODO create separate class for this button, perhaps
		//JButton newConversationButton = new JButton(Strings.getNewConversationButtonText());
		//newConversationButton.addActionListener(this);
		//newConversationButton.setActionCommand("new conversation");
		
		// try with a JPanel
		// TODO create separate class for this panel
		JPanel panel = new JPanel();
	//	panel.add(newConversationButton);
		
		UiConfigData.init();
		
		// TODO load up existing data, if it's there
		//this.conversations_ = new ConversationsPanel();

		Strings.setLocalizationLanguage("epo");
		
		Conversation conversation = new Conversation();
		
		SessionManager.init(conversation, username);
		
		panel.add(conversation);
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.setOpaque(true);
		this.setContentPane(panel);
		
		this.pack();
		this.setVisible(true);
	}
	

	public static void main(String[] args) {
		Window window = new Window();
		//window.init(args[0]);
		window.init("kechpaja");
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
