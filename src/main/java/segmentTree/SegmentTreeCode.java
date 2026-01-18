package segmentTree;

public class SegmentTreeCode {

    // arr[] 为 原序列的信息从0开始，但在arr中从1开始
    // sum[] 模拟线段树 维护的区间和
    // lazy[] 为累加和懒惰标记
    // change[] 为更新的值
    // update[] 为更新懒惰标记，update[i] 标记当前 change[i]，是否有效
    public static class SegmentTree{
        int[] arr;
        int MAXN; // 原数组数量（0 start），多加1(从而 1 start)
        // Add
        int[] lazy;
        // Update
        int[] change;
        boolean[] update;
        // !!! 存储所有结果的 区间和
        int[] sum;

        public SegmentTree(int[] origin) {
            int n = origin.length;
            MAXN = n + 1;
            arr = new int[MAXN];
            for (int i = 1; i < MAXN; i++) {
                arr[i] = origin[i - 1];
            }
            // 线段树的分支数量 最多四倍MAXN
            lazy = new int[MAXN << 2];
            change = new int[MAXN << 2];
            update = new boolean[MAXN << 2];
            sum = new int[MAXN << 2];
        }

        // 信息合并： 子的信息 合并成 父的信息 -》 sum
        // 入参只需要 父节点 rt， 因为 父节点可以推出子节点下标。
        public void pushUp(int rt) {
            sum[rt] = sum[rt * 2] + sum[rt * 2 + 1];
        }

        // 因为代码中 坐标节点与范围 是解耦的，所以 确定一个坐标时，一定要给出可以对应的范围
        // [l, mid] 代表 左子节点范围；   [mid + 1, r] 代表右子节点范围
        // 任务下发，先看 update，再看ADD。 因为update时，会清空lazy。 所以如果update与lazy同在，说明 lazy是晚来的。
        public void pushDown(int rt, int l, int mid, int r) {
            int left = rt * 2;
            int right = rt * 2 + 1;
            if (update[rt]) {
                change[left] = change[rt];
                change[right] = change[rt];
                update[left] = true;
                update[right] = true;
                sum[left] = change[left] * (mid - l + 1);
                sum[right] = change[right] * (r - mid);
                lazy[left] = 0;
                lazy[right] = 0;
                update[rt] = false;
            }
            if (lazy[rt] != 0) {  // 是否有 懒增加
                lazy[left] += lazy[rt];
                lazy[right] += lazy[rt];
                sum[left] += lazy[rt] * (mid - l + 1);
                sum[right] += lazy[rt] * (r - mid);
                // 【错误点】]别忘了 下发完之后， 当前结点更新清0
                lazy[rt] = 0;
            }
        }


        // 先要 build 整棵树, 其实就是对应的就是 初始化 sum,数组中的值,  update,change, lazy是动态过程中使用的
        // 入参： 告诉我哪一个节点为头build整棵树，并且告诉我 rt对应的范围
        public void build(int l, int r, int rt) {
            if (l == r) {
                sum[rt] = arr[l];
                return;
            }
            // recursion
            int mid = (l + r) / 2;
            build(l, mid, rt * 2);
            build(mid + 1, r, rt * 2 + 1);
            pushUp(rt); // 左树建立完了，右树 也建立完了，最后 整合子节点数值，获得当前父节点的值，则整棵树，初始化完毕。
        }

        // 递归终止条件写好了，很重要
        public void add(int L, int R, int C, int l, int r, int rt) {
            if (l >= L && r <= R) {
                lazy[rt] += C;
                sum[rt] += (r - l + 1) * C;
                return;
            }
            // 如果当前没有 懒住，下发
            // 【错误点】 不要搞错了
//            int mid = (L + R) / 2;
            int mid = (l + r) / 2;
            pushDown(rt, l , mid, r);
            // pushDown 完成后，当前结点 更新缓存全部清空。可以对子节点进行后续操作啦
            if (L <= mid) {
                add(L, R, C, l, mid, rt * 2);
            }
            if (R > mid) {
                add(L, R, C, mid + 1, r, rt * 2 + 1);
            }
            // 不要忘记，子更新完之后，要将更新的值 更新回 当前rt节点
            // 因为 我当前无法全部更新我自己， 所以我先更新子，说不定子满足范围， 子完成更新后，回来更新 rt；
            pushUp(rt);
        }

        public void update(int L, int R, int C, int l, int r, int rt) {
            if (l >= L && r <= R) {
                change[rt] = C;
                update[rt] = true;
                lazy[rt] = 0;
                sum[rt] = C * (r - l + 1);
                return;
            }
            int mid = (l + r) / 2;
            pushDown(rt, l, mid, r);
            if (L <= mid) {
                update(L, R, C, l, mid, rt * 2);
            }
            if (R > mid) {
                update(L, R, C, mid + 1, r, rt * 2 + 1);
            }
            // 左结点实现了，右节点实现了，分解子问题完成了吗？ 并没有：
            // 本来的任务是 rt整棵树完成，但是呢，左右子树完成了，rt自己这个单独的节点还没有完成，所以要 pushUp()更新 rt 这个节点。
            pushUp(rt);
        }

        //从哪一个节点开始呀（rt）, 你这个节点具体是什么意思呀？ (管理 [l, r] 范围的 sum等值， 代表节点)
        // 啥意思呢？ 用户想要 [L, R]范围上的sum， 请求区间是l,r,rt。 函数返回 l,r上满足L,R的区间的值
        public long query(int L, int R, int l, int r, int rt) {
            if (l >= L && r <= R) {
                return sum[rt];
            }
            // 分解子问题
            // 没法直接返回，返回不了
            int mid = (l + r) / 2;
            // 我要去下面取值，但是当前结点将之前的更新挡住了，下面节点并不知道，并不是最新值。所以，将更新向下传递，使下层节点的值更新，从而就能获得正确的下层节点的的值了。
            pushDown(rt, l, mid, r);
            // 我只是取值，我并不改变结构，所以 设置一个变量 ans
            long ans = 0;
            if (L <= mid) {
                ans += query(L, R, l, mid, rt * 2);
            }
            if (R > mid) {
                ans += query(L, R, mid + 1, r, rt * 2 + 1);
            }
            return ans;
        }
    }


    public static class Right {
        public int[] arr;

        public Right(int[] origin) {
            arr = new int[origin.length + 1];
            for (int i = 0; i < origin.length; i++) {
                arr[i + 1] = origin[i];
            }
        }

        public void update(int L, int R, int C) {
            for (int i = L; i <= R; i++) {
                arr[i] = C;
            }
        }

        public void add(int L, int R, int C) {
            for (int i = L; i <= R; i++) {
                arr[i] += C;
            }
        }

        public long query(int L, int R) {
            long ans = 0;
            for (int i = L; i <= R; i++) {
                ans += arr[i];
            }
            return ans;
        }

    }

    public static int[] genarateRandomArray(int len, int max) {
        int size = (int) (Math.random() * len) + 1;
        int[] origin = new int[size];
        for (int i = 0; i < size; i++) {
            origin[i] = (int) (Math.random() * max) - (int) (Math.random() * max);
        }
        return origin;
    }

    public static boolean test() {
        int len = 100;
        int max = 1000;
        int testTimes = 5000;
        int addOrUpdateTimes = 1000;
        int queryTimes = 500;
        for (int i = 0; i < testTimes; i++) {
            int[] origin = genarateRandomArray(len, max);
            SegmentTree seg = new SegmentTree(origin);
            int S = 1;
            int N = origin.length;
            int root = 1;
            seg.build(S, N, root);
            Right rig = new Right(origin);
            for (int j = 0; j < addOrUpdateTimes; j++) {
                int num1 = (int) (Math.random() * N) + 1;
                int num2 = (int) (Math.random() * N) + 1;
                int L = Math.min(num1, num2);
                int R = Math.max(num1, num2);
                int C = (int) (Math.random() * max) - (int) (Math.random() * max);
                if (Math.random() < 0.5) {
                    seg.add(L, R, C, S, N, root);
                    rig.add(L, R, C);
                } else {
                    seg.update(L, R, C, S, N, root);
                    rig.update(L, R, C);
                }
            }
            for (int k = 0; k < queryTimes; k++) {
                int num1 = (int) (Math.random() * N) + 1;
                int num2 = (int) (Math.random() * N) + 1;
                int L = Math.min(num1, num2);
                int R = Math.max(num1, num2);
                long ans1 = seg.query(L, R, S, N, root);
                long ans2 = rig.query(L, R);
                if (ans1 != ans2) {
                    return false;
                }
            }
        }
        return true;
    }

    public static void main(String[] args) {
        int[] origin = { 2, 1, 1, 2, 3, 4, 5 };
        SegmentTree seg = new SegmentTree(origin);
        int S = 1; // 整个区间的开始位置，规定从1开始，不从0开始 -> 固定
        int N = origin.length; // 整个区间的结束位置，规定能到N，不是N-1 -> 固定
        int root = 1; // 整棵树的头节点位置，规定是1，不是0 -> 固定
        int L = 2; // 操作区间的开始位置 -> 可变
        int R = 5; // 操作区间的结束位置 -> 可变
        int C = 4; // 要加的数字或者要更新的数字 -> 可变
        // 区间生成，必须在[S,N]整个范围上build
        seg.build(S, N, root);
        // 区间修改，可以改变L、R和C的值，其他值不可改变
        seg.add(L, R, C, S, N, root);
        // 区间更新，可以改变L、R和C的值，其他值不可改变
        seg.update(L, R, C, S, N, root);
        // 区间查询，可以改变L和R的值，其他值不可改变
        long sum = seg.query(L, R, S, N, root);
        System.out.println(sum);

        System.out.println("对数器测试开始...");
        System.out.println("测试结果 : " + (test() ? "通过" : "未通过"));

    }

}
