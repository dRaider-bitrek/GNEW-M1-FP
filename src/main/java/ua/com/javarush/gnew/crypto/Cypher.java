package ua.com.javarush.gnew.crypto;

import ua.com.javarush.gnew.language.Alphabet;
import ua.com.javarush.gnew.language.LanguageFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Cypher {
    private final ArrayList<Character> originalAlphabet = new ArrayList<>(Arrays.asList('A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'));
    private static final String ENGLISH_ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String UKRAINIAN_ALPHABET = "АБВГҐДЕЄЖЗИІЇЙКЛМНОПРСТУФХЦЧШЩЬЮЯ";
    private static final double[] ENGLISH_LETTER_FREQUENCY = {
            8.167, 1.492, 2.782, 4.253, 12.702, 2.228, 2.015, 6.094,
            6.966, 0.153, 0.772, 4.025, 2.406, 6.749, 7.507, 1.929,
            0.095, 5.987, 6.327, 9.056, 2.758, 0.978, 2.360, 0.150,
            1.974, 0.074
    };
    private static final double[] UKRAINIAN_LETTER_FREQUENCY = {
            7.45, 4.92, 4.33, 1.06, 0.01, 2.98, 1.83, 0.77, 4.92, 5.68, 2.28, 5.61, 2.72, 4.03, 7.59,
            9.13, 4.54, 0.81, 7.19, 5.45, 1.58, 2.06, 2.08, 0.99, 0.43, 1.47, 1.72, 0.18, 1.24, 0.32,
            2.26, 1.27, 0.21
    };

//    public String encrypt(String input, int key) {
//        key = Math.negateExact(key);
//
//        ArrayList<Character> rotatedAlphabet = new ArrayList<>(originalAlphabet);
//        Collections.rotate(rotatedAlphabet, key);
//        char[] charArray = input.toCharArray();
//
//        StringBuilder builder = new StringBuilder();
//        for (char symbol : charArray) {
//            builder.append(processSymbol(symbol, rotatedAlphabet));
//        }
//        return builder.toString();
//    }

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
//    public String encrypt(String text, int shift) {
//
//        String alphabet = getAlphabet(text);
//
//        if(shift<0) { shift = alphabet.length()-Math.abs(shift % alphabet.length());}
//        StringBuilder encryptedText = new StringBuilder();
//        int alphabetSize = alphabet.length();
//
//        for (int i = 0; i < text.length(); i++) {
//            char c = text.charAt(i);
//            if (Character.isUpperCase(c)) {
//                int index = alphabet.indexOf(c);
//                if (index != -1) {
//                    char encryptedChar = alphabet.charAt((index + shift) % alphabetSize);
//                    encryptedText.append(encryptedChar);
//                } else {
//                    encryptedText.append(c);
//                }
//            } else if (Character.isLowerCase(c)) {
//                int index = alphabet.toLowerCase().indexOf(c);
//                if (index != -1) {
//                    char encryptedChar = alphabet.toLowerCase().charAt((index + shift) % alphabetSize);
//                    encryptedText.append(encryptedChar);
//                } else {
//                    encryptedText.append(c);
//                }
//            } else {
//                encryptedText.append(c); // Якщо це не буква, просто додаємо символ без змін
//            }
//        }
//
//        return encryptedText.toString();
//    }

    public int analyzeFrequency(String text) {
        var alphabetLanguage = LanguageFactory.alphabet(text);
        String alphabet = alphabetLanguage.getAlphabet();
        var letterFrequency = alphabetLanguage.getLetterFrequency();

//        String alphabet = getAlphabet(text);
//        double[] letterFrequency= null;
//        if (alphabet == UKRAINIAN_ALPHABET)  letterFrequency =UKRAINIAN_LETTER_FREQUENCY;
//        else letterFrequency=ENGLISH_LETTER_FREQUENCY;

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


    public static String getAlphabet(String text) {
        Pattern pattern = Pattern.compile("[а-яА-ЯґҐєЄіІїЇ]+");

        Matcher matcher = pattern.matcher(text);
//        System.out.println(matcher.find());
        String alphabet = "";

        if (matcher.find()) { // Український текст
//            if (text.toString().contains("а")) { // Український текст

            alphabet = UKRAINIAN_ALPHABET;
//                letterFrequency = UKRAINIAN_LETTER_FREQUENCY;
        } else { // Англійський текст
            alphabet = ENGLISH_ALPHABET;
//                letterFrequency = ENGLISH_LETTER_FREQUENCY;
        }


        return alphabet;
    }
//
//
//    private Character processSymbol(char symbol, ArrayList<Character> rotatedAlphabet) {
//        if (!originalAlphabet.contains(symbol)) {
//            return symbol;
//        }
//        int index = originalAlphabet.indexOf(symbol);
//
//        return rotatedAlphabet.get(index);
//    }
}
