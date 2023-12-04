package com.example.trilateracion.service;

import java.util.ArrayList;
import java.util.List;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class MessageReconstructor {

    public static List<String> reconstructMessage(List<List<String>> interferedMessages) {
        List<String> reconstructedMessage = new ArrayList<>();

        int maxLength = interferedMessages.stream().mapToInt(List::size).max().orElse(0);

        for (int i = 0; i < maxLength; i++) {
            StringBuilder wordBuilder = new StringBuilder();

            for (List<String> message : interferedMessages) {
                if (i < message.size() && !message.get(i).isEmpty()) {
                    wordBuilder.append(message.get(i)).append(" ");
                }
            }

            if (wordBuilder.length() > 0) {
                reconstructedMessage.add(decryptMessage(wordBuilder.toString().trim()));
            }
        }

        return reconstructedMessage;
    }

    private static String decryptMessage(String encryptedMessage) {
        try {
            String key = "clave_secreta"; // Clave secreta para desencriptar
            String initVector = "vector_inicial"; // Vector de inicialización

            IvParameterSpec iv = new IvParameterSpec(initVector.getBytes(StandardCharsets.UTF_8));
            SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);

            byte[] original = cipher.doFinal(Base64.getDecoder().decode(encryptedMessage));
            return new String(original, StandardCharsets.UTF_8);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}