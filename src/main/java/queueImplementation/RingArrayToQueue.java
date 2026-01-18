package queueImplementation;

public class RingArrayToQueue {

    // 用户给定limit,决定上限大小
    public static class RingArrayQueue<T> {
        private int[] arr;
        private int offeri; // 入队位置
        private int polli; // 出队位置  [polli, offeri)
        private int size;
        private final int limit;

        public RingArrayQueue(int limit) {
            arr = new int[limit];
            offeri = 0;
            polli = 0;
            size = 0;
            this.limit = limit;
        }

        // isEmpty() size() poll() offer() peek()
        public boolean isEmpty() {
            return size == 0;
        }

        public int size() {
            return size;
        }

        public void offer(int value) {
            if (size == limit) {
                throw new RuntimeException("The queue is full!");
            }
            size++;
            arr[offeri] = value;
            offeri = getNext(offeri);
        }

        public int poll() {
            if (size == 0) {
                throw new RuntimeException("The queue is Empty!");
            }
            size--;
            int ans = arr[polli];
            polli = getNext(polli);
            return ans;
        }

        public int peek() {
            if (size == 0) {
                throw new RuntimeException("The queue is Empty!");
            }
            return arr[polli];
        }

        // 因为是环形数组， 所以 pos < N - 1时，下一个位置就是pos + 1； 但是当pos = N - 1时，处于最后位置，则下一个位置为0（环形）
        // 因为不对外公开，所以可以设为 private
        private int getNext(int pos) {
            return pos < arr.length - 1 ? pos + 1 : 0;
        }
    }


}
