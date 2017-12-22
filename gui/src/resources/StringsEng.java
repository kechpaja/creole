package resources;

class StringsEng extends Strings {

	@Override
	protected String getLocalizedAddUserLabel() {
		return "Add User";
	}

	@Override
	protected String getLocalizedDefaultChatTitle(int count) {
		return "Chat " + count;
	}
	
	@Override
	protected String getLocalizedForkButtonText() {
		return "Fork";
	}
	
	@Override
	protected String getLocalizedForkedChatTitle(String originalChatTitle) {
		return "New Chat (forked from: " + originalChatTitle + ")";
	}

	@Override
	protected String getLocalizedNewChatButtonText() {
		return "New Chat";
	}

}
