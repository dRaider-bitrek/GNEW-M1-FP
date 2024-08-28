package ua.com.javarush.gnew.language;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LanguageFactory {

    public static Alphabet alphabet(String text) {
        Pattern pattern =  Pattern.compile("[а-яА-ЯґҐєЄіІїЇ]+");
        Matcher matcher = pattern.matcher(text);
        if (matcher.find()) { // Український текст
        return new UkrainianAlphabet();
        } else { // Англійський текст
            return new EnglishAlphabet();
        }
    }

}
