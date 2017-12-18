package ui;

import java.util.List;

import javax.swing.JTextArea;

import backend.Message;

public class ChatHistoryArea extends JTextArea {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5228876157475301639L;

	public ChatHistoryArea(Chat chat) {
		super(5, 3);
		this.setEditable(false);
		this.setLineWrap(true);
		this.setWrapStyleWord(true);
		this.addMouseListener(chat);
	}
	
	public void displayMessages(List<Message> messages) {
		String chatAreaContents = "";
		
		for (Message message : messages) {
			chatAreaContents += message.getSendingUser() + ": " + message.getContent() + "\n";
		}
		
		this.setText(chatAreaContents);
	}

}
