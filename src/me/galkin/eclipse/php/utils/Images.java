package me.galkin.eclipse.php.utils;

public class Images {

	public static final String IMAGE_PASS = "pass";
	public static final String IMAGE_FAIL = "fail";
	
	public static String getImagePath(String name) {
		return "icons/" + name + ".gif";
	}
}