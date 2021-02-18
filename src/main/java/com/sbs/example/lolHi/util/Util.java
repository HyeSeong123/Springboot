package com.sbs.example.lolHi.util;

import java.math.BigInteger;

public class Util {

	public static int getAsInt(Object value, int defaultValue) {
		if (value instanceof Long) {
			return Long.valueOf((long) value).intValue();
		} else if (value instanceof Integer) {
			return (int) value;
		} else if (value instanceof Float) {
			return Float.valueOf((float) value).intValue();
		} else if (value instanceof Double) {
			return Double.valueOf((Double) value).intValue();
		} else if (value instanceof String) {
			try {
				return Integer.parseInt((String) value);
			} catch (NumberFormatException e) {

			}
		}
		return defaultValue;
	}

}
