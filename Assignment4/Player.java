import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Player {
    public int id;
    public GameTree tree;
    public int score;

    public Player(int id) {
        this.id = id;
        this.score = 0;
        tree = null;
    }

   
    public void attack(String word) {
        System.err.println("I got attacked");
    }

    public void defend(String word) {
        System.err.println("I got defined");

    }

    public void swap(String word) {
        System.err.println("I got swapped");

    }

 
    public void loadGame(String fileName) {
        try {
            File file = new File(fileName);
            Scanner scnr = new Scanner(file);

            // First line
            String treeType = scnr.nextLine();
            this.tree = determineTree(treeType);

            while (scnr.hasNextLine()) {
                String[] words = scnr.nextLine().split(" ");
                this.tree.addManyWord(words);
            }
            scnr.close();
        } catch (FileNotFoundException err) {
            System.out.println("Error: File not found");
        }
    }

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
