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
	private Chat thread_; 
	
	protected ChatListEntry(Chat thread) {
		this.thread_ = thread;
		
		this.setText(this.thread_.getTitle());
		this.setEditable(false);
		this.addMouseListener(this);
	}

	
	/*
	 * Mouse Listener methods
	 */
	
	@Override
	public void mouseClicked(MouseEvent arg0) { /* Do nothing */ }

	@Override
	public void mouseEntered(MouseEvent arg0) { /* Do nothing */ }

	@Override
	public void mouseExited(MouseEvent arg0) { /* Do nothing */ }

	@Override
	public void mousePressed(MouseEvent arg0) {
		this.thread_.requestFocusInWindow();
	}

	@Override
	public void mouseReleased(MouseEvent arg0) { /* Do nothing */ }
	
	
	protected void setHighlighted(boolean highlighted) {
		if (highlighted) {
			this.setBackground(Color.CYAN);
		} else {
			this.setBackground(Color.WHITE);
		}
		
		this.validate();
	}

}
