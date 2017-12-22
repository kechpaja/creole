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
	
	
	public static String getAddUserLabel() {
		return Strings.strings_.getLocalizedAddUserLabel();
	}
	
	public static String getForkButtonText() {
		return Strings.strings_.getLocalizedForkButtonText();
	}
	
	public static String getNewChatButtonText() {
		return Strings.strings_.getLocalizedNewChatButtonText();
	}
	
	
	protected abstract String getLocalizedAddUserLabel();
	protected abstract String getLocalizedForkButtonText();
	protected abstract String getLocalizedNewChatButtonText();

}
