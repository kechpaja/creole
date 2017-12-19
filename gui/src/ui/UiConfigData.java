package ui;

public class UiConfigData {
	
	private static int chatAreaMaxRows_;
	private static int chatAreaMaxColumns_;
	
	public static int getChatAreaMaxRows() {
		return UiConfigData.chatAreaMaxRows_;
	}
	
	public static int getChatAreaMaxColumns() {
		return UiConfigData.chatAreaMaxColumns_;
	}
	
	
	public static void init() {
		// TODO read config file or something and set these values correctly
		UiConfigData.chatAreaMaxRows_ = 2;
		UiConfigData.chatAreaMaxColumns_ = 3;
	}

}
