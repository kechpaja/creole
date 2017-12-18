package resources;

class StringsEpo extends Strings {

	@Override
	protected String getLocalizedDefaultConversationTitle(int count) {
		return "Konversacio " + count;
	}

	@Override
	protected String getLocalizedDefaultChatTitle(int count) {
		return "Fadeno " + count;
	}
	
	@Override
	protected String getLocalizedForkButtonText() {
		return "Forku"; // TODO I'm really not sure
	}

	@Override
	protected String getLocalizedNewConversationButtonText() {
		return "Novan konversacion";
	}

	@Override
	protected String getLocalizedNewChatButtonText() {
		return "Novan fadenon";
	}

}
