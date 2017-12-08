package resources;

public abstract class Strings {
	
	private static Strings strings_ = new StringsEng(); // Default to English if all else fails
	
	
	/*
	 * Set the language to display in
	 */
	public static void setLocalizationLanguage(String iso639dash3code) {
		System.out.println(iso639dash3code.toLowerCase());
		
		switch (iso639dash3code.toLowerCase()) {
		case "epo": Strings.strings_ = new StringsEpo(); break;
		default: Strings.strings_ = new StringsEng();
		}
	}
	
	
	/*
	 * Arguments:
	 * int count - the index of the new conversation. 
	 */
	public static String getDefaultConversationTitle(int count) {
		return Strings.strings_.getLocalizedDefaultConversationTitle(count);
	}
	
	public static String getDefaultThreadTitle(int count) {
		return Strings.strings_.getLocalizedDefaultThreadTitle(count);
	}

	public static String getNewConversationButtonText() {
		return Strings.strings_.getLocalizedNewConversationButtonText();
	}
	
	public static String getNewThreadButtonText() {
		return Strings.strings_.getLocalizedNewThreadButtonText();
	}
	
	
	protected abstract String getLocalizedDefaultConversationTitle(int count);
	protected abstract String getLocalizedDefaultThreadTitle(int count);
	protected abstract String getLocalizedNewConversationButtonText();
	protected abstract String getLocalizedNewThreadButtonText();

}
