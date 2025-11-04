package com.family.server.service;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class DecipherAES {
	private static String AES_ALGO = "AES/GCM/NoPadding";
	private static int GCM_TAG_LENGTH = 128;
	private static String key = "T7o2xNIqNT8C2/RG4WG+7g==";
	
	public DecipherAES()
	{
		
	}
	
	public static String Decipher(byte[] textEnc, byte[] iv)
	{	
		try
		{
			byte[] keybyte = Base64.getDecoder().decode(key.trim());
			SecretKeySpec keySpec = new SecretKeySpec(keybyte, "AES");
			Cipher cipher = Cipher.getInstance(AES_ALGO);
			GCMParameterSpec gcm = new GCMParameterSpec(GCM_TAG_LENGTH, iv);
			cipher.init(Cipher.DECRYPT_MODE, keySpec, gcm);
			
			byte[] plainText = cipher.doFinal(textEnc);
			
			return new String(plainText, StandardCharsets.UTF_8);
		}
		catch(Exception e)
		{
			
		}
		return "";
	}
	
}
