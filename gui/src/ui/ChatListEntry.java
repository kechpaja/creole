package ui;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JTextField;

public class ChatListEntry extends JTextField implements MouseListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1255988995929741748L;
	private Chat chat_; 
	
	protected ChatListEntry(Chat chat) {
		this.chat_ = chat;
		
		this.setEditable(false);
		this.addMouseListener(this);
		
		this.redisplay();
	}
	
	public void redisplay() {
		this.setText(String.join(", ", this.chat_.getUsersInChatSorted()));
	}

	
	/*
	 * Mouse Listener methods
	 */
	
	@Override
	public void mouseClicked(MouseEvent arg0) {
		this.chat_.mouseClicked(arg0);
	}

	@Override public void mouseEntered(MouseEvent arg0) { /* Do nothing */ }
	@Override public void mouseExited(MouseEvent arg0) { /* Do nothing */ }
	@Override public void mousePressed(MouseEvent arg0) { /*  Do nothing */ }
	@Override public void mouseReleased(MouseEvent arg0) { /* Do nothing */ }
	
	
	protected void setHighlighted(boolean highlighted) {
		if (highlighted) {
			this.setBackground(Color.GREEN);
		} else {
			this.setBackground(Color.WHITE);
		}
		
		this.validate();
	}
	
	protected void setNewMessageColor() {
		this.setBackground(Color.CYAN);
	}

}
