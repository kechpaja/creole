package ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Window extends JFrame implements ActionListener {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -808477151995604656L;
	
	private ConversationsPanel conversations_ = null;

	/*
	 * Initialize the window. 
	 */
	private void init() {
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// Handle setting up the "New Conversation" button TODO localize
		JButton newConversationButton = new JButton("New Conversation");
		newConversationButton.addActionListener(this);
		newConversationButton.setActionCommand("new conversation");
		
		// try with a JPanel
		JPanel panel = new JPanel();
		panel.add(newConversationButton);
		
		
		// TODO load up existing data, if it's there
		conversations_ = new ConversationsPanel();
		
		panel.add(conversations_);
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
		
		Window window = new Window();
		window.init();

	}

}
