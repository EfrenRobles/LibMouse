
package com.chucuaz.lib.mouse.utils;

public class Settings {
	
	public static final int SERVER_PORT = 1800;
	public static final int SERVER_TIME_OUT = 50;
	
	public static final String ENCRYPTION_KEY = "MZygpewJsCpRrfOr";
	public static final String CIPHER_TRANSFORMATION = "AES/CBC/PKCS5Padding";
	public static final String CIPHER_ALGORITHM = "AES";
	public static final String MESSAGEDIGEST_ALGORITHM = "MD5";
	
	// Replace me with a 16-byte key, share between Java and C#
	public static final byte[] RAW_SECRET_KEY = {
		0x00,
		0x00,
		0x00,
		0x00,
		0x00,
		0x00,
		0x00,
		0x00,
		0x00,
		0x00,
		0x00,
		0x00,
		0x00,
		0x00,
		0x00,
		0x00
	};

}
