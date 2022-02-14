package treemap;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * 添加删除查询 时间均为 O(log N) 的 数组实现
 * Size Balanced Tree -> 依照自然时序排序
 */
public class AddRemoveGetIndexGreat {


    // 因为 比较的是使用 自然时序，所以 存入的值 只是value，所以不需要 Comparable
    public static class SBTNode<V> {
        V value;
        SBTNode<V> l;
        SBTNode<V> r;
        int size;

        public SBTNode(V v) {
            value = v;
            size = 1;
        }
    }

    public static class SBTList<V> {
        SBTNode<V> root;

        public SBTList() {
            root = null;
        }

        public SBTNode<V> rightRotate(SBTNode<V> cur) {
            SBTNode<V> left = cur.l;
            cur.l = left.r;
            left.r = cur;
            left.size = cur.size;
            ;
            cur.size = (cur.l != null ? cur.l.size : 0) + (cur.r != null ? cur.r.size : 0) + 1;
            cur = left;
            return cur;
        }

        public SBTNode<V> leftRotate(SBTNode<V> cur) {
            SBTNode<V> right = cur.r;
            cur.r = right.l;
            right.l = cur;
            right.size = cur.size;
            cur.size = (cur.l != null ? cur.l.size : 0) + (cur.r != null ? cur.r.size : 0) + 1;
            cur = right;
            return cur;
        }

        public SBTNode<V> maintain(SBTNode<V> cur) {
            if (cur == null) {
                return null;
            }
            int leftSize = cur.l != null ? cur.l.size : 0;
            int LLSize = cur.l != null && cur.l.l != null ? cur.l.l.size : 0;
            int LRSize = cur.l != null && cur.l.r != null ? cur.l.r.size : 0;
            int rightSize = cur.r != null ? cur.r.size : 0;
            int RRSize = cur.r != null && cur.r.r != null ? cur.r.r.size : 0;
            int RLSize = cur.r != null && cur.r.l != null ? cur.r.l.size : 0;
            if (LLSize > rightSize) {
                cur = rightRotate(cur);
                cur.r = maintain(cur.r);
                cur = maintain(cur);
            } else if (LRSize > rightSize) {
                cur.l = leftRotate(cur.l);
                cur = rightRotate(cur);
                cur.l = maintain(cur.l);
                cur.r = maintain(cur.r);
                cur = maintain(cur);
            } else if (RRSize > leftSize) {
                cur = leftRotate(cur);
                cur.l = maintain(cur.l);
                cur = maintain(cur);
            } else if (RLSize > leftSize) {
                cur.r = rightRotate(cur.r);
                cur = leftRotate(cur);
                cur.l = maintain(cur.l);
                cur.r = maintain(cur.r);
                cur = maintain(cur);
            }
            return cur;
        }


        private SBTNode<V> add(SBTNode<V> cur, int index, int value) {
            if (cur == null) {
                return new SBTNode(value);
            }
            // 记得 size++
            cur.size++;
            int leftAndHeadSize = (cur.l != null ? cur.l.size : 0) + 1;
            if (index <= leftAndHeadSize) {
                cur.l = add(cur.l, index, value);
            } else {
                cur.r = add(cur.r, index - leftAndHeadSize, value);
            }
            cur = maintain(cur);
            return cur;
        }

        public void add(int index, int value) {
            if (index < 0 || index > size()) {
                throw new RuntimeException("Invalid Index");
            }
            index = index + 1; // 【必要的映射转换】 将数组以0下标开始的排序方式 映射到 SBT 的映射方式（以1下标为开始）
            root = add(root, index, value);
        }

        public int size() {
            //【错误点】 常识，一定要防止出现空指针异常  ==》 原本错误的样子： return root.size；
            return root == null ? 0 : root.size;
        }

        private SBTNode<V> remove(SBTNode<V> cur, int index) {
            cur.size--;
            int leftAndHeadSize = (cur.l != null ? cur.l.size : 0) + 1;
            if (index < leftAndHeadSize) {
                cur.l = remove(cur.l, index);
            } else if (index > leftAndHeadSize) {
                cur.r = remove(cur.r, index - leftAndHeadSize);
            } else {               // 此时 index == leftAndHeadSize
                if (cur.l == null && cur.r == null) {
                    cur = null;
                } else if (cur.l != null && cur.r == null) {
                    cur = cur.l;
                } else if (cur.r != null && cur.l == null) {
                    cur = cur.r;
                } else {
                    SBTNode<V> des = cur.r;
                    while (des.l != null) {
                        des = des.l;
                    }
                    cur.r = remove(cur.r, 1);
                    des.l = cur.l;
                    des.r = cur.r;
                    des.size = cur.size;
                    cur = des;
                }
            }
            return cur;
        }

        public void remove(int index) {
            if (index < 0 || index >= size()) {
                throw new RuntimeException("Invalid Index");
            }
            // 必要的 映射转换
            index = index + 1;
            root = remove(root, index);
        }

        public V get(int index) {
            if (index < 0 || index >= size()) {
                throw new RuntimeException("Invalid Index");
            }
            index = index + 1;  // 必要的转换映射
            SBTNode<V> cur = root;
            while (cur != null) {
                int leftAndHeadSize = (cur.l != null ? cur.l.size : 0) + 1;
                if (index < leftAndHeadSize) {
                    cur = cur.l;
                } else if (index > leftAndHeadSize) {
                    index = index - leftAndHeadSize;
                    cur = cur.r;
                } else {
                    return cur.value;
                }
            }
            return null;
        }

    }


    // 通过以下这个测试，
    // 可以很明显的看到LinkedList的插入、删除、get效率不如SbtList
    // LinkedList需要找到index所在的位置之后才能插入或者读取，时间复杂度O(N)
    // SbtList是平衡搜索二叉树，所以插入或者读取时间复杂度都是O(logN)
    public static void main(String[] args) {
        // 功能测试
        int test = 50000;
        int max = 1000000;
        boolean pass = true;
//        ArrayList<Integer> list = new ArrayList<>();
        LinkedList<Integer> list = new LinkedList<>();
        SBTList<Integer> sbtList = new SBTList<>();
        for (int i = 0; i < test; i++) {
            if (list.size() != sbtList.size()) {
                pass = false;
                break;
            }
            if (list.size() > 1 && Math.random() < 0.5) {
                int removeIndex = (int) (Math.random() * list.size());
                list.remove(removeIndex);
                sbtList.remove(removeIndex);
            } else {
                int randomIndex = (int) (Math.random() * (list.size() + 1));
                int randomValue = (int) (Math.random() * (max + 1));
                list.add(randomIndex, randomValue);
                sbtList.add(randomIndex, randomValue);
            }
        }
        for (int i = 0; i < list.size(); i++) {
            if (!list.get(i).equals(sbtList.get(i))) {
                pass = false;
                break;
            }
        }
        System.out.println("功能测试是否通过 : " + pass);

        // 性能测试
        test = 500000;
//      list = new ArrayList<>();
        list = new LinkedList<>();
        sbtList = new SBTList<>();
        long start = 0;
        long end = 0;

        start = System.currentTimeMillis();
        for (int i = 0; i < test; i++) {
            int randomIndex = (int) (Math.random() * (list.size() + 1));
            int randomValue = (int) (Math.random() * (max + 1));
            list.add(randomIndex, randomValue);
        }
        end = System.currentTimeMillis();
        System.out.println("ArrayList插入总时长(毫秒) ： " + (end - start));

        start = System.currentTimeMillis();
        for (int i = 0; i < test; i++) {
            int randomIndex = (int) (Math.random() * (i + 1));
            list.get(randomIndex);
        }
        end = System.currentTimeMillis();
        System.out.println("ArrayList读取总时长(毫秒) : " + (end - start));

        start = System.currentTimeMillis();
        for (int i = 0; i < test; i++) {
            int randomIndex = (int) (Math.random() * list.size());
            list.remove(randomIndex);
        }
        end = System.currentTimeMillis();
        System.out.println("ArrayList删除总时长(毫秒) : " + (end - start));

        start = System.currentTimeMillis();
        for (int i = 0; i < test; i++) {
            int randomIndex = (int) (Math.random() * (sbtList.size() + 1));
            int randomValue = (int) (Math.random() * (max + 1));
            sbtList.add(randomIndex, randomValue);
        }
        end = System.currentTimeMillis();
        System.out.println("SbtList插入总时长(毫秒) : " + (end - start));

        start = System.currentTimeMillis();
        for (int i = 0; i < test; i++) {
            int randomIndex = (int) (Math.random() * (i + 1));
            sbtList.get(randomIndex);
        }
        end = System.currentTimeMillis();
        System.out.println("SbtList读取总时长(毫秒) :  " + (end - start));

        start = System.currentTimeMillis();
        for (int i = 0; i < test; i++) {
            int randomIndex = (int) (Math.random() * sbtList.size());
            sbtList.remove(randomIndex);
        }
        end = System.currentTimeMillis();
        System.out.println("SbtList删除总时长(毫秒) :  " + (end - start));

    }



}
