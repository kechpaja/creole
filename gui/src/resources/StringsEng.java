package resources;

class StringsEng extends Strings {

	@Override
	protected String getLocalizedDefaultConversationTitle(int count) {
		return "Conversation " + count;
	}

	@Override
	protected String getLocalizedDefaultThreadTitle(int count) {
		return "Thread " + count;
	}

	@Override
	protected String getLocalizedNewConversationButtonText() {
		return "New Conversation";
	}

	@Override
	protected String getLocalizedNewThreadButtonText() {
		return "New Thread";
	}

}
