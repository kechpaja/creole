package ui;

import java.util.HashSet;
import java.util.Set;

import javax.swing.JPanel;

public class UserList extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3661200557102046809L;
	
	private Set<String> users_;
	
	public UserList() {
		this.users_ = new HashSet<String>();
		
		this.setVisible(true);
		this.setOpaque(true);
		this.validate();
	}
	
	public Set<String> getUsers() {
		return this.users_;
	}
	
	public void addUsers(Set<String> users) {
		this.users_.addAll(users);
	}

}
