package com.likya.bugreport.JDK_8199849;

import java.io.UnsupportedEncodingException;

public class UTF8String {
	public static String getValue(byte [] userpassbytes) {
		String password = null;
		try {
			password = new String(userpassbytes, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			password = new String(userpassbytes);
		}
		return password;
	}
}
