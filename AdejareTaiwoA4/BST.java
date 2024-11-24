import java.util.ArrayList;

public class BST implements GameTree {
    private Node root;

    // Node class for BST;
    private class Node {
        public String word;
        public int frequency;
        public Node left;
        public Node right;

        public Node(String word, int frequency, Node left, Node right) {
            this.word = word;
            this.frequency = frequency;
            this.left = left;
            this.right = right;
        }
    }

    //  Contructor
    public BST() {
        root = null;
    }

    @Override
    public void addWord(String word) {
        Node curr = root;
        Node prev = null;
        Node exist = null;

        while (curr != null && exist == null) {
            prev = curr;
            if (curr.word.equals(word)) {
                exist = curr;
                // Branch left if less and right if greater.
            } else if (curr.word.compareTo(word) > 0) {
                curr = curr.left;
            } else {
                curr = curr.right;
            }
        }

        // if word doesnt exist in tree
        if (exist == null) {
            Node newWord = new Node(word, 1, null, null);

            // if tree is empty
            if (prev == null) {
                root = newWord;
            } else {
                // Insert into tree : left if smaller and right if bigger
                if (prev.word.compareTo(word) < 0) {
                    prev.right = newWord;
                } else {
                    prev.left = newWord;
                }
            }
        } else {
            // if its exist increase freq
            exist.frequency += 1;
        }
    }

    // Accept multiple words to insert.
    public void addManyWord(String[] words) {
        for (String word : words) {
            this.addWord(word);
        }
    }

    // Find node with the word in the tree.
    private Node find(String word) {
        Node found = null;
        Node curr = root;
        while (curr != null && found == null) {
            if (curr.word.equals(word)) {
                found = curr;
            } else if (curr.word.compareTo(word) > 0) {
                curr = curr.left;
            } else {
                curr = curr.right;
            }
        }
        return found;
    }

    @Override
    public boolean containsWord(String word) {
        return find(word) != null;
    }

    @Override
    public int getFrequency(String word) {
        int freq = 0;
        Node wordNode = find(word);

        // if word node is found
        if (wordNode != null) {
            freq = wordNode.frequency;
        }

        return freq;
    }

    @Override
    public boolean doubleFreqency(String word) {
        boolean isDoubled = false;
        Node wordNode = this.find(word);

        // if word node is found
        if (wordNode != null) {
            wordNode.frequency *= 2;
            isDoubled = true;
        }

        return isDoubled;
    }

    @Override
    public boolean swapFrequency(String word1, String word2) {
        boolean swapped = false;

        // find node for both words
        Node wordNode1 = find(word1);
        Node wordNode2 = find(word2);

        if (wordNode1 != null & wordNode2 != null) {
            int tempFreq = wordNode1.frequency;

            // Swap Frequency
            wordNode1.frequency = wordNode2.frequency;
            wordNode2.frequency = tempFreq;
            swapped = true;
        }
        return swapped;
    }

    @Override
    public void print() {
        inOrderPrint(root);
    }

    // Print word in tree in order.
    private void inOrderPrint(Node curr) {
        if (curr == null) {
            return;
        } else {
            inOrderPrint(curr.left);
            System.out.printf("%s(%d) ", curr.word, curr.frequency);
            inOrderPrint(curr.right);
        }
    }

    @Override
    public int height() {
        return treeSize(root);
    }

    // Calculate tree size
    private int treeSize(Node subTree) {
        if (subTree == null) {
            return -1;
        } else {
            int leftSide = treeSize(subTree.left);
            int rightSide = treeSize(subTree.right);

            if (leftSide > rightSide) {
                return leftSide + 1;
            } else {
                return rightSide + 1;
            }
        }
    }

    @Override
    public void compare(GameTree otherTree) {
        if (!(otherTree instanceof BST)) {
            System.out.println("Other tree dont have the same instance");
            return;
        }

        // Cast otherTree to BST
        BST otherBST = (BST) otherTree;

        // Initalize list
        ArrayList<String> commonList = new ArrayList<>();
        ArrayList<String> uniqueList = new ArrayList<>();

        compareRecursive(this.root, otherBST.root, commonList, uniqueList);

        System.out.println("Same words: " + commonList.toString());
        System.out.println("Unique words: " + uniqueList.toString());
    }

    private void compareRecursive(Node currNode, Node otherNode, ArrayList<String> commonList,
            ArrayList<String> uniqueList) {
        if (currNode == null && otherNode == null) {
            return; // Both trees are exhausted
        }

        // if not is not equal to other nodes words.
        if (currNode.word.compareTo(otherNode.word) != 0) {
            // currNode has a smaller word
            compareRecursive(currNode.left, otherNode, commonList, uniqueList);
            uniqueList.add(currNode.word);
            compareRecursive(currNode.right, otherNode, commonList, uniqueList);
        } else {
            // Both words are equal
            compareRecursive(currNode.left, otherNode.left, commonList, uniqueList);
            commonList.add(currNode.word);
            compareRecursive(currNode.right, otherNode.right, commonList, uniqueList);
        }
    }

    @Override
    public void printTree() {
        printTreeRec(root, 0);
    }

    // Recursive function to traverse over each word node in the tree.
    private void printTreeRec(Node curr, int level) {
        if (curr == null) {
            return;
        }
        String tabs = "\t".repeat(level);
        System.out.print("\nLevel " + level + ": ");
        System.out.printf("%s %s(%d) ", tabs, curr.word, curr.frequency);

        printTreeRec(curr.left, level + 1);
        printTreeRec(curr.right, level + 1);

    }
}
