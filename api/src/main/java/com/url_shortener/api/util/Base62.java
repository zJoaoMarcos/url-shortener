package com.url_shortener.api.util;

import java.util.Random;

public record Base62(String alphabet) {
    private static final int BASE = 62;

    public Base62(String alphabet) {
        this.alphabet = generateAlphabet(alphabet);
    }

    private String generateAlphabet(String secretKey) {
        String defaultChars = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

        Random random = new Random(secretKey.hashCode());
        char[] chars = defaultChars.toCharArray();

        for (int i = chars.length - 1; i > 0; i--) {
            int j = random.nextInt(i + 1);
            char temp = chars[i];
            chars[i] = chars[j];
            chars[j] = temp;
        }

        return new String(chars);
    }

    public String encode(long num) {
        if (num == 0) return String.valueOf(alphabet.charAt(0));

        StringBuilder sb = new StringBuilder();
        while (num > 0) {
            sb.append(alphabet.charAt((int) (num % BASE)));
            num /= BASE;
        }
        return sb.reverse().toString();
    }

    public long decode(String str) {
        long result = 0;
        for (int i = 0; i < str.length(); i++) {
            result = result * BASE + alphabet.indexOf(str.charAt(i));
        }
        return result;
    }
}