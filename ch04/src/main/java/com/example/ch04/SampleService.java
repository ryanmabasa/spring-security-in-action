package com.example.ch04;

import org.springframework.security.crypto.encrypt.BytesEncryptor;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.crypto.keygen.BytesKeyGenerator;
import org.springframework.security.crypto.keygen.KeyGenerators;
import org.springframework.security.crypto.keygen.StringKeyGenerator;
import org.springframework.stereotype.Service;

@Service
public class SampleService {


//    The generator creates an 8-byte key,
//    and it encodes that as a hexadecimal string. The method returns the result of these operations as a string
    public void stringKeyGenerator(){
        StringKeyGenerator keyGenerator = KeyGenerators.string();
        String salt = keyGenerator.generateKey();
    }

    /*
    *
    * In the previous code snippet, the key generator generates keys of 8-byte length.
    * If you want to specify a different key length, you can do this when obtaining
    * the key generator instance by providing the desired value to the KeyGenerators.secureRandom() method
    * */

    public void bytesKeyGenerator(){
        BytesKeyGenerator keyGenerator = KeyGenerators.secureRandom(16);
        byte [] key = keyGenerator.generateKey();
//        If you want to specify a different key length,
        //        you can do this when obtaining the key generator instance by providing the desired value to the
        int keyLength = keyGenerator.getKeyLength();
    }


    /*
    * Behind the scenes, the standard byte encryptor uses 256-byte AES encryption to encrypt input.
    * To build a stronger instance of the byte encryptor, you can call the Encryptors.stronger() method
    *
    * */

    public void encryption(){
        String salt = KeyGenerators.string().generateKey();
        String password = "secret";
        String valueToEncrypt = "HELLO";

        BytesEncryptor e = Encryptors.standard(password, salt);
        byte [] encrypted = e.encrypt(valueToEncrypt.getBytes());
        byte [] decrypted = e.decrypt(encrypted);
    }
}
