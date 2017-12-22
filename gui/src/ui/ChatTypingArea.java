package ui;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JTextArea;

import backend.Message;
import backend.SessionManager;

public class ChatTypingArea extends JTextArea implements KeyListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6995004424786514497L;
	private Chat chat_; 
	
	public ChatTypingArea(Chat chat) {
		super(5, 3);
		this.setEditable(true);
		this.setLineWrap(true);
		this.setWrapStyleWord(true);
		this.addKeyListener(this);
		this.addMouseListener(chat);
		this.chat_ = chat;
	}
	
	/*
	 * Key Listener Methods
	 */
	
	@Override
	public void keyReleased(KeyEvent e) { /* Do nothing */ }
	
	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			if (e.isShiftDown()) {
				this.append("\n");
			} else {
				Set<String> toUsers = new HashSet<String>();
				
				for (String user : this.chat_.getUsersInChat()) {
					if (!user.equals(SessionManager.getCurrentUser())) {
						toUsers.add(user); // TODO there's got to be something more elegant than this. 
					}
				}
				
				if (!this.getText().equals("")) {
					Message message = new Message(
											this.getText(), 
											SessionManager.getCurrentUser(), 
											this.chat_.getId(),
											toUsers);
					this.setText("");
					this.chat_.deliver(message);
					SessionManager.getSender().queueMessage(message);
				}
				
				e.consume();
			}
		}
	}

	@Override
	public void keyTyped(KeyEvent e) { /* Do nothing */ }

}
