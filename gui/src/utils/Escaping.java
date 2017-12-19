package utils;

public class Escaping {
	
	public static String escape(String string) {
		return string.replace("\\", "\\\\").replace("\n", "\\n").replace("\r", "\\r").replace(" ", "\\s").replace(":", "\\c");
	}
	
	public static String unescape(String string) {
		return string.replace("\\c", ":").replace("\\s", " ").replace("\\r", "\r").replace("\\n", "\n").replace("\\\\", "\\");
	}

}
