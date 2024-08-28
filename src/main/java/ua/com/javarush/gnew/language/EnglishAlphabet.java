package ua.com.javarush.gnew.language;

public class EnglishAlphabet implements Alphabet{
    private final String Language = "EN";
    private static final String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final double[] LETTER_FREQUENCY = {
            8.167, 1.492, 2.782, 4.253, 12.702, 2.228, 2.015, 6.094,
            6.966, 0.153, 0.772, 4.025, 2.406, 6.749, 7.507, 1.929,
            0.095, 5.987, 6.327, 9.056, 2.758, 0.978, 2.360, 0.150,
            1.974, 0.074
    };

    @Override
    public String getLanguage() {
        return Language;
    }

    @Override
    public String getAlphabet() {
        return ALPHABET;
    }

    @Override
    public double[] getLetterFrequency() {
        return LETTER_FREQUENCY;
    }

    @Override
    public int absKey(int key) {
        if(key<0) { key = ALPHABET.length()-Math.abs(key % ALPHABET.length());}
        return key;
    }

    @Override
    public int length() {
        return ALPHABET.length();
    }
}
