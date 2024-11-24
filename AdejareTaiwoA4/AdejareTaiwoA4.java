/*
* COMP 2140 SECTION A02
* INSTRUCTOR: Dr. Lauren Himbeault
* STUDENT NUMBER: 7980132
* ASSIGNMENT: Assignment 1
* QUESTION: Question 1 & 2
*/

import java.util.ArrayList;
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

    // Used Words
    static ArrayList<String> usedWords = new ArrayList<String>();

    public static void main(String[] args) {
        Player[] players = new Player[MAX_PLAYERS];
        if (args.length > 1) {
            for (int i = 0; i < MAX_PLAYERS; i++) {
                players[i] = new Player(i + 1);
                players[i].loadGame(args[i]);
            }

            playGame(players);
        } else {
            System.out.println("ERROR: Invalid file input");
        }

    }

    /**
     * Plays a multiplayer word-based game for a random number of rounds.
     *
     * @param players Array of Player objects in the game.
     */
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

            // Update currnt index to next player
            int nextIndex = (currentIndex + 1) % players.length;

            // Get word from player
            String word = promptWord(scnr, action);

            // Play action
            play(players[currentIndex], players[nextIndex], action.toUpperCase(), word);

            currentIndex = nextIndex;

            // update round if current index get back start index
            if (currentIndex == startIndex) {
                numOfRound--;
                printScores(players);
                printGameState(numOfRound, players);
            }
        }

        printScores(players);
        scnr.close();
    }

    /**
     * Prints the scores of all players.
     *
     * @param players Array of Player objects with their scores.
     */
    public static void printScores(Player[] players) {
        System.out.println("\n ========= SCORE TABLE ============");
        for (Player player : players) {
            System.out.println("Player: Player" + player.id);
            System.out.println("Score: " + player.score);
        }
    }

    /**
     * Executes the action chosen by the current player against the opponent.
     *
     * @param currentPlayer The player performing the action.
     * @param opponent      The player being targeted by the action.
     * @param action        The action to be performed (e.g., ATTACK, DEFEND, SWAP).
     * @param word          The word associated with the action.
     */
    public static void play(Player currentPlayer, Player opponent, String action, String word) {
        switch (action) {
            case ATTACK:
                attack(currentPlayer, opponent, word);
                break;
            case DEFEND:
                defend(currentPlayer, opponent, word);
                break;
            case SWAP:
                swap(currentPlayer, opponent, word);
                break;
            default:
                break;
        }
    }

    /**
     * Swaps two words in the current player's tree if valid.
     *
     * @param currentPlayer The player performing the swap.
     * @param opponent      The opponent player (not used in this method).
     * @param word          The words to be swapped, separated by a space.
     */
    public static void swap(Player currentPlayer, Player component, String word) {
        String[] swapWords = word.split(" ");
        if (swapWords.length >= 2 && !(swapWords[0].equals(swapWords[1]))) {
            if (!usedWords.contains(word)) {
                boolean swapped = currentPlayer.tree.swapFrequency(swapWords[0], swapWords[1]);

                if (!swapped) {
                    System.out.println("Error trying to swapp invalid words!!");
                }
            } else {
                System.out.println("Opps!!.. This words has already been used in previous attack.");
            }
        } else {
            System.out.println(
                    "Either you messed up your entry or both words aren't in your tree. What a waste of a turn");
        }
    }

    /**
     * Doubles the frequency of a word in the current player's tree to defend
     * against an attack.
     *
     * @param currentPlayer The player performing the defense.
     * @param opponent      The opponent player (not used in this method).
     * @param word          The word to be defended.
     */
    public static void defend(Player currentPlayer, Player opponent, String word) {
        if (!usedWords.contains(word)) {
            boolean doubled = currentPlayer.tree.doubleFreqency(word);

            if (doubled) {
                System.out.println(word + " has been successfully defended.");
                System.out.println(word + " here frequency is doubled.");
            } else {
                System.out.println("Word is invalid! What a waste!");
                System.out.println("What a waste, that isn't even in your tree.");
            }
        } else {
            System.out.println("Opps!!.. This words has already been used in previous attack.");
        }
    }

    /**
     * Attacks the opponent with a word, comparing frequencies to earn points.
     *
     * @param currentPlayer The player performing the attack.
     * @param opponent      The opponent player being attacked.
     * @param word          The word used for the attack.
     */
    public static void attack(Player currentPlayer, Player opponent, String word) {
        if (!usedWords.contains(word)) {

            int currFreq = currentPlayer.tree.getFrequency(word);
            int oppFreq = opponent.tree.getFrequency(word);

            if (currFreq == 0) {
                System.out.println("Word is invalid! What a waste!");
                System.out.println("What a waste, that isn't even in your tree.");
            } else if (currFreq > oppFreq) {
                currentPlayer.addScore(currFreq);
                System.out.println("Score increased by " + currFreq);
                usedWords.add(word);

            } else {
                System.out.println("Frequency wasn't high enough to earn any points.");
            }

        } else {
            System.out.println("Opps!!.. This words has already been used in previous attack..");
        }

    }

    /**
     * Validates if the given action is a valid game action (SWAP, DEFEND, ATTACK).
     *
     * @param action The action to be validated.
     * @return True if the action is valid, otherwise false.
     */
    public static boolean validAction(String action) {
        boolean isValid = false;

        if (action.equals(SWAP) || action.equals(DEFEND) || action.equals(ATTACK)) {
            isValid = true;
        } else {
            System.out.println("ERROR: Invalid action, Please kindly select from above list.");
        }

        return isValid;
    }

    /**
     * Validates if the given word is non-empty.
     *
     * @param word The word to be validated.
     * @return True if the word is valid (non-empty), otherwise false.
     */
    public static boolean validWord(String word) {
        boolean isValid = false;
        if (word.length() > 0) {
            isValid = true;
        } else {
            System.out.println("ERROR: Enter a valid word.");
        }

        return isValid;
    }

    /**
     * Prompts the player to enter a valid word based on the selected action.
     *
     * @param scnr   The Scanner object used to get input from the user.
     * @param action The action to be performed (e.g., ATTACK, DEFEND, SWAP).
     * @return The valid word entered by the player.
     */
    public static String promptWord(Scanner scnr, String action) {
        System.out.printf("What word do you want to %s with? \n", action);
        String word = scnr.nextLine().trim();

        while (!validWord(word)) {
            word = scnr.nextLine().trim();
        }
        return word;
    }

    /**
     * Prompts the player to select a valid action from the available options.
     *
     * @param scnr The Scanner object used to get input from the user.
     * @return The valid action selected by the player.
     */
    public static String promptAction(Scanner scnr) {
        System.out.println("\nPlease select from one of the following options:");
        System.out.printf("\t%s\n", ATTACK);
        System.out.printf("\t%s\n", DEFEND);
        System.out.printf("\t%s\n", SWAP);
        String action = scnr.nextLine().trim();

        while (!validAction(action.toUpperCase())) {
            action = scnr.nextLine().trim();
        }

        return action;
    }

    /**
     * Prints the current game state, including the number of rounds left and each
     * player's tree.
     *
     * @param numOfRound The number of rounds remaining in the game.
     * @param players    The array of players in the game.
     */
    public static void printGameState(int numOfRound, Player[] players) {
        System.err.printf("------ ROUNDS LEFT: %d ------\n", numOfRound);
        for (Player player : players) {
            System.out.printf("\n------ Player%s Tree ------\n", player.id);
            player.tree.printTree();
        }
    }

    /**
     * Generates a random integer between the specified minimum and maximum values
     * (inclusive).
     *
     * @param min The minimum value (inclusive).
     * @param max The maximum value (inclusive).
     * @return A random integer between min and max.
     */
    public static int genRandom(int min, int max) {
        Random rand = new Random();
        return rand.nextInt((max + 1) - min) + min;
    }

}
