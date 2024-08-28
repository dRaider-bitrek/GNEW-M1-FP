package ua.com.javarush.gnew.language;

public class UkrainianAlphabet implements Alphabet{
    private final String Language = "UKR";
    private  final String ALPHABET = "АБВГҐДЕЄЖЗИІЇЙКЛМНОПРСТУФХЦЧШЩЬЮЯ";
    private  final double[] LETTER_FREQUENCY = {
            7.45, 4.92, 4.33, 1.06, 0.01, 2.98, 1.83, 0.77, 4.92, 5.68, 2.28, 5.61, 2.72, 4.03, 7.59,
            9.13, 4.54, 0.81, 7.19, 5.45, 1.58, 2.06, 2.08, 0.99, 0.43, 1.47, 1.72, 0.18, 1.24, 0.32,
            2.26, 1.27, 0.21
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
