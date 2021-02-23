package com.sbs.example.lolHi.util;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.URLEncoder;

public class Util {

	public static int getAsInt(Object value, int defaultValue) {
		if (value instanceof BigInteger) {
			return ((BigInteger) value).intValue();
		} else if (value instanceof Long) {
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

	public static String getAsStr(Object object, String defaultValue) {
		if(object == null) {
			return defaultValue;
		}
		
		if (object instanceof String) {
			return (String) (object);
		}

		return object.toString();
	}

	public static String getUriEncoded(String str) {
		try {
			return URLEncoder.encode(str, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			return str;
		}
	}

	public static String getNewUriRemoved(String uri, String paramName) {
		String deleteStrStarts = paramName + "=";
		int delStartPos = uri.indexOf(deleteStrStarts);

		if (delStartPos != -1) {
			int delEndPos = uri.indexOf("&", delStartPos);

			if (delEndPos != -1) {
				delEndPos++;
				uri = uri.substring(0, delStartPos) + uri.substring(delEndPos, uri.length());
			} else {
				uri = uri.substring(0, delStartPos);
			}
		}

		if (uri.charAt(uri.length() - 1) == '?') {
			uri = uri.substring(0, uri.length() - 1);
		}

		if (uri.charAt(uri.length() - 1) == '&') {
			uri = uri.substring(0, uri.length() - 1);
		}

		return uri;
	}

	public static String getNewUri(String uri, String paramName, String paramValue) {
		uri = getNewUriRemoved(uri, paramName);

		if (uri.contains("?")) {
			uri += "&" + paramName + "=" + paramValue;
		} else {
			uri += "?" + paramName + "=" + paramValue;
		}

		uri = uri.replace("?&", "?");

		return uri;
	}

	public static String getNewUriAndEncoded(String uri, String paramName, String pramValue) {
		return getUriEncoded(getNewUri(uri, paramName, pramValue));
	}
}
