public class TwoThreeTree implements GameTree {
    private Node root;

    private class TreeValue {
        public String word; 
        public int frequency;

        public TreeValue(String word, int frequency) {
            this.word = word;
            this.frequency = frequency;
        }
    }

    private class Node {
        public TreeValue[] data;
        public Node[] children;

        public Node(TreeValue[] data, Node[] children) {
            this.data = data;
            this.children = children;
        }
    }

    public TwoThreeTree () {
        root = null;
    }

    @Override
    public void addWord(String word) {
        Node curr = root;

        
    }

    @Override
    public boolean containsWord(String word) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'containsWord'");
    }

    @Override
    public int getFrequency(String word) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getFrequency'");
    }

    @Override
    public void print() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'print'");
    }

    @Override
    public int height() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'height'");
    }

    @Override
    public void compare(GameTree otherTree) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'compare'");
    }

    @Override
    public void printTree() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'printTree'");
    }

    @Override
    public void addManyWord(String[] word) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'addManyWord'");
    }
    
}
