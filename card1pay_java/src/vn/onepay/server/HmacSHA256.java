package vn.onepay.server;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class HmacSHA256 {
	private Mac mac = null;

	public static HmacSHA256 getInstance(String secret) {
		return new HmacSHA256(secret);
	}

	public HmacSHA256(String secret) {
		SecretKeySpec signingKey = new SecretKeySpec(secret.getBytes(),
				"HmacSHA256");
		try {
			mac = Mac.getInstance("HmacSHA256");
			mac.init(signingKey);
		} catch (InvalidKeyException e) {
			throw new RuntimeException(
					"Invalid key exception while converting to HMac SHA256");
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private String byteArrayToString(byte[] data) {
		BigInteger bigInteger = new BigInteger(1, data);
		String hash = bigInteger.toString(16);
		// Zero pad it
		while (hash.length() < 64) {
			hash = "0" + hash;
		}
		return hash;
	}

	public String sign(String data) {
		try {
			byte[] digest = mac.doFinal(data.getBytes("UTF-8"));
			return byteArrayToString(digest);
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
