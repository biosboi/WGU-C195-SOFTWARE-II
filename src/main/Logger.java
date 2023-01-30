package main;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.Instant;

/**
 * @author William Nathan
 * Logs login attempts in login_activity.txt file
 */
public class Logger {
    private static final String FILENAME = "login_activity.txt";

    public Logger() {}

    /**
     * Records username and timestamp of login attempts in login_activity.txt
     * @param username User attempting to log in
     * @param validLogin true if login was successful
     * @throws IOException File Opening exception handler
     */
    public static void log (String username, boolean validLogin) throws IOException {
        FileWriter fw = new FileWriter(FILENAME, true);
        BufferedWriter bw = new BufferedWriter(fw);
        PrintWriter pw = new PrintWriter(bw);
        pw.println(username + (validLogin ? " Successful" : " Failed") + " Login at: " + Instant.now().toString());
        bw.close();
        pw.close();
        fw.close();
    }
}
