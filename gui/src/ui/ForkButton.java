package ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import resources.Strings;

public class ForkButton extends JButton implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2071983323942604170L;
	private Chat chat_;
	
	public ForkButton(Chat chat) {
		super(Strings.getForkButtonText());
		this.addActionListener(this);
		this.setActionCommand("fork chat");
		this.chat_ = chat;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("fork chat")) {
			this.chat_.fork();
		}
	}

}
