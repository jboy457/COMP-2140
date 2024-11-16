public class Tree23Test {

    public static void main(String[] args) {
        // Create instances of BST and TwoThreeTree
        GameTree tree = new TwoThreeTree();

        System.out.println("===== Testing with TwoThreeTree =====");
        runTests(tree);
    }

    public static void runTests(GameTree tree) {
        testAddWord(tree);
        // testContainsWord(tree);
        // testGetFrequency(tree);
        // testPrint(tree);
        // testHeight(tree);
        // testPrintTree(tree);
    }

    public static void testAddWord(GameTree tree) {
        System.out.println("\n--- Test: addWord() ---");
        tree.addWord("apple");
        tree.addWord("banana");
        tree.addWord("apple"); // Adding "apple" again to update frequency
        tree.addWord("cherry");

        System.out.println("Expected: [apple(2), banana(1), cherry(1)]");
        System.out.print("Actual: ");
        tree.print();
    }

    // public static void testContainsWord(GameTree tree) {
    //     System.out.println("\n--- Test: containsWord() ---");
    //     boolean result1 = tree.containsWord("apple");
    //     boolean result2 = tree.containsWord("banana");
    //     boolean result3 = tree.containsWord("durian"); // Should not exist
    //     boolean result4 = tree.containsWord("cherry");

    //     System.out.println("Contains 'apple': Expected = true, Actual = " + result1);
    //     System.out.println("Contains 'banana': Expected = true, Actual = " + result2);
    //     System.out.println("Contains 'durian': Expected = false, Actual = " + result3);
    //     System.out.println("Contains 'cherry': Expected = true, Actual = " + result4);

    // }

    // public static void testGetFrequency(GameTree tree) {
    //     System.out.println("\n--- Test: getFrequency() ---");
    //     int freqApple = tree.getFrequency("apple");
    //     int freqBanana = tree.getFrequency("banana");
    //     int freqDurian = tree.getFrequency("durian"); // Should not exist
    //     int freqCherry = tree.getFrequency("cherry"); 

    //     System.out.println("Frequency of 'apple': Expected = 2, Actual = " + freqApple);
    //     System.out.println("Frequency of 'banana': Expected = 1, Actual = " + freqBanana);
    //     System.out.println("Frequency of 'durian': Expected = 0, Actual = " + freqDurian);
    //     System.out.println("Frequency of 'cherry': Expected = 1, Actual = " + freqCherry);

    // }

    // public static void testPrint(GameTree tree) {
    //     System.out.println("\n--- Test: print() ---");
    //     System.out.println("Expected: [apple(2), banana(1), cherry(1)]");
    //     System.out.print("Actual: ");
    //     tree.print();
    // }

    // public static void testHeight(GameTree tree) {
    //     System.out.println("\n--- Test: height() ---");
    //     int height = tree.height();
    //     System.out.println("Expected height for 23Tree (counted by Edges): 1");
    //     System.out.println("Actual Height of the tree: " + height);
    // }

    // public static void testPrintTree(GameTree tree) {
    //     System.out.println("\n--- Test: printTree() ---");
    //     System.out.println("Visual representation of the tree structure:");
    //     tree.printTree();
    // }
}