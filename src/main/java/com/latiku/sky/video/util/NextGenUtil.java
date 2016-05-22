package com.latiku.sky.video.util;

/* Utility class for next generation services for sky video.
 * 
 * @author Kishore Ramana
 * @version 1.0
 * @since 2016-05-21
 * */
public class NextGenUtil {

	public static boolean isNumeric(String str) {
		boolean isNumeric = true;
		try {
			Integer.parseInt(str);
		} catch (Exception e) {
			isNumeric = false;
		}
		return isNumeric;
	}
}
