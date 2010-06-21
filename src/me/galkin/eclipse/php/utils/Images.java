package me.galkin.eclipse.php.utils;

public class Images {

	public static final String IMAGE_PASS = "pass";
	public static final String IMAGE_FAIL = "fail";
	public static final String IMAGE_ERROR = "error";
	
	public static final String IMAGE_ERRORS = "errors_total";
	public static final String IMAGE_FAILURES = "failures_total";
	public static final String RUN = "run";
	
	public static String getImagePath(String name) {
		return "icons/" + name + ".gif";
	}
}