package com.latiku.sky.video.util;

/* Enum class to determine the age for given parental control level string.
 * 
 * @author Kishore Ramana
 * @version 1.0
 * @since 2016-05-22
 * */
public enum AgeLevel {
	U(4), PG(8);

	private final int value;

	AgeLevel(final int newValue) {
		value = newValue;
	}

	public int getValue() {
		return value;
	}
}
