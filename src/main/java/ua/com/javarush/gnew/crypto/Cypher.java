package ua.com.javarush.gnew.crypto;

import ua.com.javarush.gnew.language.LanguageFactory;


import java.util.HashMap;
import java.util.Map;


public class Cypher {


    public String decrypt(String text, int shift) {
        var alphabetLanguage = LanguageFactory.alphabet(text);
        shift = alphabetLanguage.absKey(shift);

        return encrypt(text, alphabetLanguage.length() - shift); // Для розшифрування використовується зворотний зсув
    }

    public String encrypt(String text, int shift) {
        var alphabetLanguage = LanguageFactory.alphabet(text);
        shift = alphabetLanguage.absKey(shift);
        String alphabet = alphabetLanguage.getAlphabet();

        StringBuilder encryptedText = new StringBuilder();
        int alphabetSize = alphabet.length();

        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            if (Character.isUpperCase(c)) {
                int index = alphabet.indexOf(c);
                if (index != -1) {
                    char encryptedChar = alphabet.charAt((index + shift) % alphabetSize);
                    encryptedText.append(encryptedChar);
                } else {
                    encryptedText.append(c);
                }
            } else if (Character.isLowerCase(c)) {
                int index = alphabet.toLowerCase().indexOf(c);
                if (index != -1) {
                    char encryptedChar = alphabet.toLowerCase().charAt((index + shift) % alphabetSize);
                    encryptedText.append(encryptedChar);
                } else {
                    encryptedText.append(c);
                }
            } else {
                encryptedText.append(c); // Якщо це не буква, просто додаємо символ без змін
            }
        }
        return encryptedText.toString();
    }

    public int analyzeFrequency(String text) {
        var alphabetLanguage = LanguageFactory.alphabet(text);
        String alphabet = alphabetLanguage.getAlphabet();
        var letterFrequency = alphabetLanguage.getLetterFrequency();

        Map<Character, Integer> frequencyMap = new HashMap<>();
        int alphabetSize = alphabet.length();

        for (char c : text.toUpperCase().toCharArray()) {
            if (alphabet.indexOf(c) != -1) {
                frequencyMap.put(c, frequencyMap.getOrDefault(c, 0) + 1);
            }
        }

        double minChiSquared = Double.MAX_VALUE;
        int bestShift = 0;

        for (int shift = 0; shift < alphabetSize; shift++) {
            double chiSquared = 0.0;

            for (int i = 0; i < alphabetSize; i++) {
                char decryptedChar = alphabet.charAt((i + shift) % alphabetSize);
                int observedCount = frequencyMap.getOrDefault(decryptedChar, 0);
                double expectedCount = text.length() * letterFrequency[i] / 100.0;
                chiSquared += Math.pow(observedCount - expectedCount, 2) / expectedCount;
            }

            if (chiSquared < minChiSquared) {
                minChiSquared = chiSquared;
                bestShift = shift;
            }
        }

        return bestShift;
    }

}
