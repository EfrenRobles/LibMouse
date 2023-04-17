package com.chucuaz.lib.mouse.engine;

//import android.util.Base64;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import com.chucuaz.lib.mouse.utils.EngineDebug;
import com.chucuaz.lib.mouse.utils.Settings;

/**
 * Created by efren.robles on 10/13/2015.
 */
public class EngineAES extends EngineDebug {
	

	private static final String TAG = "AES";

	private static Cipher aesCipher;
	private static SecretKey secretKey;
	private static IvParameterSpec ivParameterSpec;


	public EngineAES(String passphrase) {
		byte[] passwordKey = encodeDigest(passphrase);

		try {
			aesCipher = Cipher.getInstance(Settings.CIPHER_TRANSFORMATION);
		} catch (NoSuchAlgorithmException e) {
			ERR(TAG, "No such algorithm " + Settings.CIPHER_ALGORITHM + " " + e.getMessage());
		} catch (NoSuchPaddingException e) {
			ERR(TAG, "No such padding PKCS5" + " " + e.getMessage());
		}

		secretKey = new SecretKeySpec(passwordKey, Settings.CIPHER_ALGORITHM);
		ivParameterSpec = new IvParameterSpec(Settings.RAW_SECRET_KEY);
	}

	public String encryptAsBase64(String clearData) {
		byte[] encryptedData = { 0 };
		try {
			encryptedData = clearData.getBytes("UTF-8");
		} catch (Exception e) {
			ERR(TAG, "--- There is an error with encryptAsBAse64 --- ");
		}
		
		encryptedData = encrypt(encryptedData);
		return Base64.getEncoder().encodeToString(encryptedData);
	}

	private byte[] encrypt(byte[] clearData) {
		try {
			aesCipher.init(Cipher.ENCRYPT_MODE, secretKey, ivParameterSpec);
		} catch (InvalidKeyException e) {
			ERR(TAG, "Invalid key" + " " + e.getMessage());
			return null;
		} catch (InvalidAlgorithmParameterException e) {
			ERR(TAG, "Invalid algorithm " + Settings.CIPHER_ALGORITHM + " " + e.getMessage());
			return null;
		}

		byte[] encryptedData;
		try {
			encryptedData = aesCipher.doFinal(clearData);
		} catch (IllegalBlockSizeException e) {
			ERR(TAG, "Illegal block size" + " " + e.getMessage());
			return null;
		} catch (BadPaddingException e) {
			ERR(TAG, "Bad padding" + " " + e.getMessage());
			return null;
		}
		return encryptedData;
	}

	private byte[] encodeDigest(String text) {
		MessageDigest digest;
		try {
			digest = MessageDigest.getInstance(Settings.MESSAGEDIGEST_ALGORITHM);
			return digest.digest(text.getBytes());
		} catch (NoSuchAlgorithmException e) {
			ERR(TAG, "No such algorithm " + Settings.MESSAGEDIGEST_ALGORITHM + " " + e.getMessage());
		}
		return null;
	}

}
