import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Player {
    public int id;
    public GameTree tree;
    public int score;

    // Constructor 
    public Player(int id) {
        this.id = id;
        this.score = 0;
        tree = null;
    }

    // ADD SCORE FOR USER
    public void addScore(int score) {
        this.score += score;
    }

    /*
     * Loads a game by reading data from a specified file.
     * The first line determines the tree type (BST or 23Tree),
     * and subsequent lines are processed to add words to the tree.
     * If the file is not found, an error message is displayed.
     */
    public void loadGame(String fileName) {
        try {
            File file = new File(fileName);
            Scanner scnr = new Scanner(file);

            // First line
            String treeType = scnr.nextLine();
            this.tree = determineTree(treeType);

            while (scnr.hasNextLine()) {
                String[] words = scnr.nextLine().toLowerCase().split(" ");
                this.tree.addManyWord(words);
            }
            scnr.close();
        } catch (FileNotFoundException err) {
            System.out.println("Error: File not found");
        }
    }

    /*
     * Returns a GameTree based on the specified type:
     * - "BST" for a Binary Search Tree (BST)
     * - "23Tree" for a 2-3 Tree
     */
    private GameTree determineTree(String type) {
        final String BST = "BST";
        final String TTT = "23Tree";

        GameTree treeToLoad = null;
        switch (type) {
            case BST:
                treeToLoad = new BST();
                break;
            case TTT:
                treeToLoad = new TwoThreeTree();
                break;
            default:
                break;
        }
        return treeToLoad;
    }
}
