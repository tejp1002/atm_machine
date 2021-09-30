package reader;

import java.util.Scanner;

/**
 * Scanner to read input from terminal/screen.
 */
public class ScannerReader {
    Scanner scanner;
    public ScannerReader() {
        scanner = new Scanner(System.in);
    }

    public int getNextInt() {
        return scanner.nextInt();
    }
}
