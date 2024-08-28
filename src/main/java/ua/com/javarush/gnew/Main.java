package ua.com.javarush.gnew;

import picocli.CommandLine;

public class Main {
    public static void main(String[] args) {
        int exitCode = new CommandLine(new SimpleCLI()).execute(args);
//        System.exit(exitCode);
        }
}
