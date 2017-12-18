package resources;

class StringsEng extends Strings {

	@Override
	protected String getLocalizedDefaultConversationTitle(int count) {
		return "Conversation " + count;
	}

	@Override
	protected String getLocalizedDefaultChatTitle(int count) {
		return "Thread " + count;
	}
	
	@Override
	protected String getLocalizedForkButtonText() {
		return "Fork";
	}

	@Override
	protected String getLocalizedNewConversationButtonText() {
		return "New Conversation";
	}

	@Override
	protected String getLocalizedNewChatButtonText() {
		return "New Thread";
	}

}
