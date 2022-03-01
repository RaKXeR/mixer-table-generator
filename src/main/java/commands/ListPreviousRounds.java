package commands;

import model.Round;
import model.Table;

import java.util.List;

public class ListPreviousRounds {
    public static void run(List<Round> rounds) {
        // Return if no rounds have been played
        if (rounds.isEmpty()) {
            System.out.println("No rounds have yet been played.");
            return;
        }

        // List generated rounds
        System.out.print("------------------------------------------------------------------------------------");
        int i = 0;
        for (Round round : rounds) {
            System.out.println();
            System.out.printf("Round %d [%d table(s)]:\n", ++i, round.size());
            for (Table table : round) {
                System.out.println(table);
            }
        }
    }
}
