public class MinPQWeightedEdge {
    private WeightedEdge[] heap;
    public int pointer;
    private int heapSize;

    public MinPQWeightedEdge() {
        this.heapSize = 20;
        this.heap = new WeightedEdge[this.heapSize];
        // initHeap(this.heap);
        this.pointer = 0;
    }

    public void insert(WeightedEdge weightedEdge) {
        if (pointer >= heapSize) {
            resizeHeap();
        }
        heap[pointer] = weightedEdge;
        shiftUp();
        pointer++;
    }

    public WeightedEdge retrieveMin() {
        WeightedEdge min = null;
        if(!this.isEmpty()) {
            min = heap[0];
            heap[0] = heap[pointer - 1];
            heap[pointer - 1] = new WeightedEdge(0, 0, 0);
    
            shiftDown();
    
            pointer--;
            return min;
        } 
        return min;
    }

    public boolean isEmpty() {
        return pointer < 1;
    }

    public void print() {
        if (pointer > 0) {
            String queue = "[ ";
            for (int i = 0; i < pointer; i++) {
               queue += heap[i].weight + " ";
            }
            queue += "]";
            System.out.println(queue);
        } else {
            System.out.println("The Queue is currently Empty.");
        }
    }

    private void resizeHeap() {
        heapSize *= 2;
        WeightedEdge[] newHeap = new WeightedEdge[heapSize];
        for (int i = 0; i < pointer; i++) {
            newHeap[i] = heap[i];
        }
        heap = newHeap;
    }

    public boolean isLeaf(int i) {
        return i >= pointer / 2 && i < pointer;
    }

    private void shiftDown() {
        if (pointer > 0) {
            int currIndex = 0;
            int leftChildIdx = getLChild(currIndex);
            int rightChildIdx = getRChild(currIndex);

            while (!isLeaf(currIndex) && !isLeaf(rightChildIdx) && !isLeaf(rightChildIdx)) {
                if (heap[rightChildIdx].compareTo(heap[leftChildIdx]) < 0) {
                    swap(currIndex, rightChildIdx);
                    currIndex = rightChildIdx;
                } else {
                    swap(currIndex, leftChildIdx);
                    currIndex = leftChildIdx;
                }

                leftChildIdx = getLChild(currIndex);
                rightChildIdx = getRChild(currIndex);

            }
        }
    }

    private void swap(int fromIndex, int toIndex) {
        WeightedEdge tempEdge = heap[fromIndex];
        heap[fromIndex] = heap[toIndex];
        heap[toIndex] = tempEdge;
    }

    private void shiftUp() {
        if (pointer > 0) {
            int currIndex = pointer;
            int parentIndex = getParentIndex(currIndex);
            while (heap[currIndex].compareTo(heap[parentIndex]) < 0) {
                swap(currIndex, parentIndex);
                currIndex = parentIndex;
                parentIndex = getParentIndex(currIndex);
            }
        }
    }

    private int getParentIndex(int index) {
        return (int) Math.floor((index - 1) / 2);
    }

    private int getLChild(int index) {
        return 2 * index + 1;
    }

    private int getRChild(int index) {
        return 2 * index + 2;
    }
}
