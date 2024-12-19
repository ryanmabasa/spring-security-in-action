package com.example.ch14;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

@ExtendWith(SpringExtension.class)
class CodeVerifierAndChallengeTest {

	@Test
	void testCodeChallenge() throws NoSuchAlgorithmException {
		SecureRandom secureRandom = new SecureRandom();
		byte [] code = new byte[32];
		secureRandom.nextBytes(code);
		String codeVerifier = Base64.getUrlEncoder()
				.withoutPadding()
				.encodeToString(code);

		System.out.println(codeVerifier);

		MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");

		byte [] digested = messageDigest.digest(codeVerifier.getBytes());
		String codeChallenge = Base64.getUrlEncoder()
				.withoutPadding()
				.encodeToString(digested);

		System.out.println(codeChallenge);

	}

}
