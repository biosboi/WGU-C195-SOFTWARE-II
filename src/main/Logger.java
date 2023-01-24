package main;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.Instant;

public class Logger {
    private static final String FILENAME = "Login Attempt Logger.txt";

    public Logger() {}

    public static void log (String username, boolean validLogin) throws IOException {
        FileWriter fw = new FileWriter(FILENAME, true);
        BufferedWriter bw = new BufferedWriter(fw);
        PrintWriter pw = new PrintWriter(bw);
        pw.println(username + (validLogin ? " Successful" : " Failed") + " Login at: " + Instant.now().toString());
    }
}
