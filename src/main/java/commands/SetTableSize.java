package commands;

import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

public class SetTableSize {
    public static void run(Scanner s, AtomicInteger preferredTableSize) {
        System.out.println("Please set your preferred table size: ");
        if (s.hasNextInt()) {
            int size = s.nextInt();
            preferredTableSize.set(size);
            System.out.printf("Preferred table size is now %d.\n", size);
        } else {
            System.out.println("Invalid input, returning to menu.");
        }
    }
}
