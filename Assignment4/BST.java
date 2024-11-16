public class BST implements GameTree {
    private Node root;

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

    public BST() {
        root = null;
    }

    @Override
    public void addWord(String word) {
        Node curr = root;
        Node prev = null;
        Node exist = null;

        String wordToInsert = word.toLowerCase();

        while (curr != null && exist == null) {
            prev = curr;
            if (curr.word.equals(wordToInsert)) {
                exist = curr;
                // Branch left if less and right if greater.
            } else if (curr.word.compareTo(wordToInsert) > 0) {
                curr = curr.left;
            } else {
                curr = curr.right;
            }
        }

        // if word doesnt exist in tree
        if (exist == null) {
            Node newWord = new Node(wordToInsert, 1, null, null);

            // if tree is empty
            if (prev == null) {
                root = newWord;
            } else {
                // Insert into tree : left if smaller and right if bigger
                if (prev.word.compareTo(wordToInsert) < 0) {
                    prev.right = newWord;
                } else {
                    prev.left = newWord;
                }
            }
        } else {
            exist.frequency += 1;
        }
    }

    public void addManyWord(String[] words) {
        for(String word : words) {
            this.addWord(word);
        }
    }

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
        if (wordNode != null) {
            freq = wordNode.frequency;
        }

        return freq;
    }

    @Override
    public void print() {
        inOrder(root);
    }

    private void inOrder(Node curr) {
        if (curr == null) {
            return;
        } else {
            inOrder(curr.left);
            System.out.printf("%s(%d) ", curr.word, curr.frequency);
            inOrder(curr.right);
        }
    }

    private void preOrder(Node curr, String format) {
        if (curr == null) {
            return;
        } else {
            System.out.printf(format, curr.word, curr.frequency);
            preOrder(curr.left, "--Left %s(%d)");
            preOrder(curr.right, "\t--Right %s(%d)");
            if (curr.left != null && curr.right != null) {
                System.out.println();
            }
        }
    }

    @Override
    public int height() {
        return treeSize(root);
    }

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
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'compare'");
    }

    @Override
    public void printTree() {
        preOrder(root, "Root -- %s(%d)\n");
    }

}
