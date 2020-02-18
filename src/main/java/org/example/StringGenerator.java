package org.example;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

public class StringGenerator {

    private static final int BATCH = 1024;
    private static final long MAX = BATCH * BATCH * BATCH - 1;
    private static final String TEXT = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890!@#$%^&*()";
    private Set<String> rndText = new HashSet<>();
    private Random rnd = new Random();

    public StringGenerator() {
        while (rndText.size() < BATCH) {
            rndText.add(getRandomString());
        }
    }

    private String getRandomString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < 33; i++) {
            stringBuilder.append(TEXT.charAt(rnd.nextInt(TEXT.length())));
        }
        return  stringBuilder.toString();
    }

    public void writeString(int count) {
        List<String> strings = new ArrayList<>(rndText);
        try (PrintWriter writer = new PrintWriter(new FileWriter("rndtext.txt"))) {
            int c = 0;
            for (int i = 0; i < BATCH; i++) {
                for (int j = 0; j < BATCH; j++) {
                    for (int k = 0; k < BATCH; k++) {
                        String str = strings.get(i)
                                + strings.get(j)
                                + strings.get(k)
                                + TEXT.charAt(rnd.nextInt(TEXT.length()));
                        c++;
                        writer.println(str);
                        if (c >= count) {
                            return;
                        }
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error writing file...");
        }
    }

    public static void main(String [] args) {
        System.out.println(String.format("Please enter a number between 1 - %s : ", MAX));
        Scanner scanner = new Scanner(System.in);
        int num = scanner.nextInt();
        if (num < 1 || num > MAX) {
            System.out.println("Invalid Number...");
            return;
        }
        new StringGenerator().writeString(num);
    }
}
