package service;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class TheShop {

    public static boolean checkItem(String plant) {
        String file = "../plantsExternalRes";
        try (BufferedReader br = Files.newBufferedReader(Paths.get(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.equals(plant)) {
                    return true;
                }
            }
        } catch (IOException e) {
            System.err.format(e.toString());
        }
        return false;
    }
}
