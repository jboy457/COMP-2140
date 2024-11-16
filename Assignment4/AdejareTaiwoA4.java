import java.util.Random;
import java.util.Scanner;

public class AdejareTaiwoA4 {
    static final int MAX_PLAYERS = 2;

    // Round data
    static final int MIN_ROUND = 2;
    static final int MAX_ROUND = 5;

    // Coin Data
    static final int HEAD = 0;
    static final int TAIL = 1;

    // Actions data
    static final String ATTACK = "ATTACK";
    static final String DEFEND = "DEFEND";
    static final String SWAP = "SWAP";

    public static void main(String[] args) {
        Player[] players = new Player[MAX_PLAYERS];
        String[] argss = { "battleA.txt", "battleD.txt" };
        if (argss.length > 1) {
            for (int i = 0; i < MAX_PLAYERS; i++) {
                players[i] = new Player(i + 1);
                players[i].loadGame(argss[i]);
            }

            playGame(players);
        } else {
            System.out.println("ERROR: Invalid file input");
        }

    }

    public static void playGame(Player[] players) {
        Scanner scnr = new Scanner(System.in);
        int numOfRound = genRandom(MIN_ROUND, MAX_ROUND);
        int startIndex = genRandom(HEAD, TAIL);
        printGameState(numOfRound, players);

        int currentIndex = startIndex;
        while (numOfRound > 0) {
            // Player (id) turn to play
            System.out.printf("\nPlayer%d’s Turn\n", players[currentIndex].id);

            // Get action from player
            String action = promptAction(scnr);
            System.out.printf("You selected: %s\n", action);

            // Get word from player
            String word = promptWord(scnr, action);

            // Play action

            play(players[currentIndex], action.toUpperCase(), word);

            // Update currnt index to next player
            currentIndex = ++currentIndex % players.length;

            // update round if current index get back start index
            if (currentIndex == startIndex) {
                numOfRound--;
                printGameState(numOfRound, players);

            }
        }

        scnr.close();
    }

    public static void play(Player player, String action, String word) {
        switch (action) {
            case ATTACK:
                player.attack(word);
                break;
            case DEFEND:
                player.defend(word);
                break;
            case SWAP:
                player.swap(word);
                break;
            default:
                break;
        }
    }

    public static boolean validAction(String action) {
        boolean isValid = false;

        if (action.equals(SWAP) || action.equals(DEFEND) || action.equals(ATTACK)) {
            isValid = true;
        } else {
            System.out.println("ERROR: Invalid action, Please kindly select from above list.");
        }

        return isValid;
    }

    public static boolean validWord(String word) {
        boolean isValid = false;
        if (word.length() > 0) {
            isValid = true;
        } else {
            System.out.println("ERROR: Enter a valid word.");
        }

        return isValid;
    }

    public static String promptWord(Scanner scnr, String action) {
        System.out.printf("What word do you want to %s with? \n", action);
        String word = scnr.nextLine();

        while (!validWord(word)) {
            word = scnr.nextLine();
        }
        return word;
    }

    public static String promptAction(Scanner scnr) {
        System.out.println("\nPlease select from one of the following options:");
        System.out.printf("\t%s\n", ATTACK);
        System.out.printf("\t%s\n", DEFEND);
        System.out.printf("\t%s\n", SWAP);
        String action = scnr.nextLine();

        while (!validAction(action.toUpperCase())) {
            action = scnr.nextLine();
        }

        return action;
    }

    public static void printGameState(int numOfRound, Player[] players) {
        System.err.printf("------ ROUNDS LEFT: %d ------\n", numOfRound);
        for (Player player : players) {
            System.out.printf("\n------ Player%s Tree ------\n", player.id);
            player.tree.print();
        }
    }

    public static int genRandom(int min, int max) {
        Random rand = new Random();
        return rand.nextInt((max + 1) - min) + min;
    }

}
