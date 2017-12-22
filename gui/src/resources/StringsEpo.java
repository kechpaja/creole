package resources;

class StringsEpo extends Strings {

	@Override
	protected String getLocalizedAddUserLabel() {
		return "Aldonu Uzanton";
	}

	@Override
	protected String getLocalizedDefaultChatTitle(int count) {
		return "Fadeno " + count;
	}
	
	@Override
	protected String getLocalizedForkButtonText() {
		return "Forku";
	}
	
	@Override
	protected String getLocalizedForkedChatTitle(String originalChatTitle) {
		return "Nova Fadeno (forkita de: " + originalChatTitle + ")";
	}

	@Override
	protected String getLocalizedNewChatButtonText() {
		return "Novan fadenon";
	}

}
