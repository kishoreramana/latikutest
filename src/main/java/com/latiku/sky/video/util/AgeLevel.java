package com.latiku.sky.video.util;

/* Enum class to determine the age for given parental control level string.
 * 
 * @author Kishore Ramana
 * @version 1.0
 * @since 2015-12-10
 * */
public enum AgeLevel {
	U(0), PG(1);

	private final int value;

	AgeLevel(final int newValue) {
		value = newValue;
	}

	public int getValue() {
		return value;
	}
}
