import java.util.ArrayList;
import java.util.List;

public class TwoThreeTree implements GameTree {
    private Node root;
    private final int MAX_CHILDREN = 3;
    private final int MAX_ITEM = 2;

    // Data in node 
    private class TreeValue {
        public String word;
        public int frequency;

        public TreeValue(String word, int frequency) {
            this.word = word;
            this.frequency = frequency;
        }
    }

    // Node class for TwoThreeTree;
    private class Node {
        public TreeValue[] data;
        public Node[] children;
        private int dataPointer;
        private Node parent;

        public Node() {
            this.data = new TreeValue[MAX_ITEM];
            this.children = new Node[MAX_CHILDREN];
            dataPointer = 0;
        }

        // Add child to children.
        public void addChild(int index, Node child) {
            children[index] = child;
            if (child != null) {
                child.parent = this;
            }
        }

        // Remove item from children.
        public Node removeChild(int index) {
            Node nodeToRemove = children[index];
            children[index] = null;

            return nodeToRemove;
        }

        // Check if boolean is a leaf node.
        public boolean isLeaf() {
            boolean aleaf = false;

            // check if child has no node.
            if (children[0] == null) {
                aleaf = true;
            }

            return aleaf;
        }

        // Check if the item tree node is full i.e have full data
        public boolean isFull() {
            boolean full = false;
            if (dataPointer == MAX_CHILDREN - 1) {
                full = true;
            }

            return full;
        }

        // find word in tree node.
        public int findWordIndex(String word) {
            int index = -1;
            for (int j = 0; j < dataPointer && index == -1; j++) {
                if (data[j].word.equals(word)) {
                    index = j;
                }
            }

            return index;
        }

        // Insert data into tree value
        public int insertTreeVal(TreeValue treeVal) {
            dataPointer++;
            String newWord = treeVal.word;

            for (int i = MAX_CHILDREN - 2; i >= 0; i--) {
                if (data[i] != null) {
                    // Place item in the righ position data array
                    if (newWord.compareTo(data[i].word) < 0) {
                        data[i + 1] = data[i];
                    } else {
                        data[i + 1] = treeVal;
                        return i + i;
                    }
                }
            }

            data[0] = treeVal;
            return 0;
        }

        // Remove tree value from Node and reduce data pointer.
        public TreeValue removeTreeVal() {
            TreeValue temp = data[dataPointer - 1];
            data[dataPointer - 1] = null;
            dataPointer--;

            return temp;
        }
    }

    // Constructor.
    public TwoThreeTree() {
        root = new Node();
    }

    @Override
    public void addWord(String word) {
        // Find word in tree and get the node
        Node exist = find(word);
        
        // if its doesnt exist then add to the tree.
        if (exist == null) {
            Node curr = root;

            boolean isLeaf = false;
            while (!isLeaf) {
                if (curr.isLeaf()) {
                    isLeaf = true;
                } else {
                    curr = getNextChild(curr, word);
                }
            }

            // Add value to tree.
            TreeValue newTreeVal = new TreeValue(word, 1);
            if (curr.isFull()) {
                splitNode(curr, newTreeVal);
            } else {
                curr.insertTreeVal(newTreeVal);
            }
        } else {
            // if item already exist in just increament.
            int wordIndex = exist.findWordIndex(word);
            exist.data[wordIndex].frequency++;
        }

    }

    // Add many words to the tree.
    public void addManyWord(String[] words) {
        for (String word : words) {
            this.addWord(word);
        }
    }

    // Get next child
    private Node getNextChild(Node child, String word) {
        Node nextChild = null;
        boolean foundNext = false;
        for (int j = 0; j < child.dataPointer && !foundNext; j++) {
            if (word.compareTo(child.data[j].word) < 0) {
                nextChild = child.children[j];
                foundNext = true;
            }
        }

        if (!foundNext) {
            nextChild = child.children[child.dataPointer];
        }

        return nextChild;
    }

    private void addItemToTreeVal(TreeValue[] treeToAdd, TreeValue[] values) {
        for (int i = 0; i < values.length; i++) {
            treeToAdd[i] = values[i];
        }
    }

    // Recusively split node.
    private Node splitNode(Node nodeToSplit, TreeValue treeVal) {
        Node parent = nodeToSplit.parent;

        TreeValue[] sortedTreeVal = new TreeValue[3];
        if (treeVal.word.compareTo(nodeToSplit.data[0].word) <= 0) {
            TreeValue[] sortVal = { treeVal, nodeToSplit.data[0], nodeToSplit.data[1] };
            addItemToTreeVal(sortedTreeVal, sortVal);
        } else if (treeVal.word.compareTo(nodeToSplit.data[1].word) <= 0) {
            TreeValue[] sortVal = { nodeToSplit.data[0], treeVal, nodeToSplit.data[1] };
            addItemToTreeVal(sortedTreeVal, sortVal);
        } else {
            TreeValue[] sortVal = { nodeToSplit.data[0], nodeToSplit.data[1], treeVal };
            addItemToTreeVal(sortedTreeVal, sortVal);
        }

        // Empty the current node and put the lowest value to the left
        nodeToSplit.removeTreeVal();
        nodeToSplit.removeTreeVal();
        nodeToSplit.insertTreeVal(sortedTreeVal[0]);

        // Add largest value to the right.
        Node newRightNode = new Node();
        newRightNode.insertTreeVal(sortedTreeVal[2]);

        // if its the root node that is splited.
        if (nodeToSplit == this.root) {

            // Create a new root
            this.root = new Node();

            // Add the middlle item to the root
            this.root.insertTreeVal(sortedTreeVal[1]);

            // Add the splited node to the left and the large node to the right.
            this.root.addChild(0, nodeToSplit);
            this.root.addChild(1, newRightNode);

        } else if (parent.isFull()) { // if parent is full also split the parent.
            Node sibling = splitNode(parent, sortedTreeVal[1]);

            if (nodeToSplit == parent.children[0]) {
                Node secondChild = parent.removeChild(1);
                Node thirdChild = parent.removeChild(2);

                parent.addChild(1, newRightNode);
                sibling.addChild(0, secondChild);
                sibling.addChild(1, thirdChild);
            } else if (nodeToSplit == parent.children[1]) {
                Node thirdChild = parent.removeChild(2);
                sibling.addChild(0, newRightNode);
                sibling.addChild(1, thirdChild);
            } else {
                parent.removeChild(2);
                sibling.addChild(0, nodeToSplit);
                sibling.addChild(1, newRightNode);
            }

        } else {
            // if parent is not full insert value to the middle and re-arrange the children.
            parent.insertTreeVal(sortedTreeVal[1]);

            if (nodeToSplit.equals(parent.children[0])) {
                parent.addChild(2, parent.removeChild(1));

                parent.addChild(1, newRightNode);
            } else {
                parent.addChild(2, newRightNode);
            }
        }

        return newRightNode;
    }

    // find node with the word.
    public Node find(String word) {
        Node found = null;
        Node curr = root;

        // While curr node is not null and if its not yet found.
        while (curr != null && found == null) {
            if (curr.findWordIndex(word) != -1) {
                found = curr;
            } else if (curr.isLeaf()) {
                curr = null;
            } else {
                curr = getNextChild(curr, word);
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

        // find the word node
        if (wordNode != null) {
            // find the word index in data list 
            int wordIndex = wordNode.findWordIndex(word);
            freq = wordNode.data[wordIndex].frequency;
        }

        return freq;
    }

    @Override
    public void print() {
        printHelper(root);
    }

    private void printHelper(Node curr) {
        if(curr == null) {
            return;
        }
        for(int i = 0; i< curr.dataPointer; i++) {
            System.out.printf("%s(%d) ", curr.data[i].word, curr.data[i].frequency);
        }
        if (!curr.isLeaf()) {
            for (int i = 0; i <= curr.dataPointer; i++) {
                printHelper(curr.children[i]);
            }
        }
    }

    private int countEdges(Node subTree) {
        if (subTree == null) {
            return -1;
        } 

        // if its reach leaf node return 0
        if(subTree.isLeaf()) {
            return 0;
        }

        // if node still has children increament count.
        return 1 + countEdges(subTree.children[0]);
    }

    @Override
    public int height() {
        return countEdges(root);
    }

    @Override
    public void compare(GameTree otherTree) {
        if (!(otherTree instanceof TwoThreeTree)) {
            System.out.println("Other tree dont have the same instance");
            return;
        }

        // Cast Other tree into TwoThreeTree.
        TwoThreeTree otherTest23 = (TwoThreeTree) otherTree;

        // Initialize list
        List<String> commonList = new ArrayList<>();
        List<String> uniqueList = new ArrayList<>();
      

        // Start the comparison using in-order traversal
        compareRecursive(this.root, otherTest23.root, commonList, uniqueList);

        // Print results
        System.out.println("Same words: " + commonList);
        System.out.println("Unique words: " + uniqueList);

    }

    private void compareRecursive(Node currNode, Node otherNode, List<String> commonList, List<String> uniqList) {
        if (currNode == null && otherNode == null) {
            return;
        }

        // Traverse through every data in the current node.
        for (int i = 0; i < currNode.dataPointer; i++) {
            // Compare the current item of this tree with the other tree's current items
            if (i < otherNode.dataPointer) {
                String wordThis = currNode.data[i].word;
                String wordOther = otherNode.data[i].word;

                if (wordThis.equals(wordOther)) {
                    commonList.add(wordThis);
                } else {
                    uniqList.add(wordThis);
                }
            }
        }

        // Compare the children of currNode and otherNode
        if (currNode.children != null && currNode.children.length > 0) {
            for (int i = 0; i < currNode.dataPointer; i++) {
                compareRecursive(currNode.children[i], otherNode.children[i], commonList, uniqList);
            }
        }
    }

    @Override
    public void printTree() {
        printTreeRec(root, 0);
    }
    
    // Recursively print the tree.
    private void printTreeRec(Node curr, int level) {
        if (curr == null) {
            return;
        }

        System.out.print("Level " + level + ": ");
        for (int i = 0; i < curr.dataPointer; i++) {
            String tabs = "\t".repeat(level);
            System.out.printf("%s %s(%d) ", tabs, curr.data[i].word, curr.data[i].frequency);
        }
        System.out.println();

        if (!curr.isLeaf()) {
            for (int i = 0; i <= curr.dataPointer; i++) {
                printTreeRec(curr.children[i], level + 1);
            }
        }
    }

    @Override
    public boolean swapFrequency(String word1, String word2) {
        boolean swapped = false;

        // find node for both words
        Node wordNode1 = find(word1);
        Node wordNode2 = find(word2);

        if (wordNode1 != null & wordNode2 != null) {
            // Get word index of items.
            int wordIndex1 = wordNode1.findWordIndex(word1);
            int wordIndex2 = wordNode2.findWordIndex(word2);

            int tempFreq = wordNode1.data[wordIndex1].frequency;

            // Swap Frequency
            wordNode1.data[wordIndex1].frequency = wordNode2.data[wordIndex2].frequency;
            wordNode2.data[wordIndex2].frequency = tempFreq;
            swapped = true;
        }
        return swapped;
    }

    @Override
    public boolean doubleFreqency(String word) {
        boolean isDoubled = false;
        // find node for word
        Node wordNode = this.find(word);

        if (wordNode != null) {
            int wordIndex = wordNode.findWordIndex(word);

            // Mulitply word frequency by 2
            wordNode.data[wordIndex].frequency *= 2;
            isDoubled = true;
        }

        return isDoubled;
    }

}
