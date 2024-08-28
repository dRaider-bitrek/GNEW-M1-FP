package ua.com.javarush.gnew.language;

public interface Alphabet {

    public String getLanguage();
    public String getAlphabet();
    public double[] getLetterFrequency();
    public int absKey(int key);
    public int length();

}
