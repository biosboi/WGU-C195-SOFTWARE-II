package main;

public class Logger {
    private static final String FILENAME = "Login Attempt Logger.txt";

    public Logger() {}

    //Must change all this

   /* public static void log (String username, boolean success, String message) {
        try (FileWriter fw = new FileWriter(FILENAME, true);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter pw = new PrintWriter(bw)) {
            pw.println(username + (success ? " Successful" : " Failed") + " " + message + " " + Instant.now().toString());
        } catch (IOException e) {
            System.out.println("Logger Error: " + e.getMessage());
        }
    }*/
}
