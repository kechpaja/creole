package ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import backend.NetworkUtilities;
import backend.Sender;
import resources.Strings;

public class Window extends JFrame implements ActionListener {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -808477151995604656L;
	private static String currentUser_;
	
	private ConversationsPanel conversations_ = null;
	private Sender sender_ = null;

	/*
	 * Initialize the window. 
	 */
	private void init(String user) {
		// Set current user
		Window.currentUser_ = user;
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// Handle setting up the "New Conversation" button TODO localize
		JButton newConversationButton = new JButton(Strings.getNewConversationButtonText());
		newConversationButton.addActionListener(this);
		newConversationButton.setActionCommand("new conversation");
		
		// try with a JPanel
		JPanel panel = new JPanel();
		panel.add(newConversationButton);
		
		
		// TODO consider wrapping all of the backend stuff into one class?
		NetworkUtilities networkUtilities = new NetworkUtilities();

		// TODO initialize sender threads
		this.sender_ = new Sender(networkUtilities);
		
		// TODO load up existing data, if it's there
		this.conversations_ = new ConversationsPanel(this.sender_);
		
		
		panel.add(this.conversations_);
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.setOpaque(true);
		this.setContentPane(panel);
		
		this.pack();
		this.setVisible(true);
	}
	
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("new conversation")) {
			this.conversations_.createNewConversation();
		}
	}
	
	

	public static void main(String[] args) {
		Strings.setLocalizationLanguage("epo"); // TODO read this from config file somewhere
		
		Window window = new Window();
		window.init("kechpaja"); // TODO read username from config as well, probably. Or from login screen. 
	}
	
	public static String getCurrentUser() {
		return Window.currentUser_;
	}

}
