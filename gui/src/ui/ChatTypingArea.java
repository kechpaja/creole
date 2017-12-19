package ui;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

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
				if (!this.getText().equals("")) {
					Message message = new Message(this.getText(), SessionManager.getCurrentUser(), this.chat_.getId(), this.chat_.getConversationId());
					this.setText("");
					this.chat_.deliver(message);
					SessionManager.getSender().queueMessage(message, this.chat_.getUsersInChat());
				}
				
				e.consume();
			}
		}
	}

	@Override
	public void keyTyped(KeyEvent e) { /* Do nothing */ }

}
