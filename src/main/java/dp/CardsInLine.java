package dp;

public class CardsInLine {


    public static int winner(int[] arr) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        int N = arr.length;
        int A = f(0, N - 1, arr);
        int B = g(0, N - 1, arr);
        return Math.max(A, B);
    }

    // 先手
    public static int f(int L, int R, int[] arr) {
        if (L == R) {
            return arr[L];
        }
        int p1 = arr[L] + g(L + 1, R, arr);
        int p2 = arr[R] + g(L, R - 1, arr);

        return Math.max(p1, p2);
    }


    // 后手
    public static int g(int L, int R, int[] arr) {
        if (L == R) {
            return 0;
        }
        int p1 = f(L + 1, R, arr);
        int p2 = f(L, R - 1, arr);

        return Math.min(p1, p2);
    }


    public static int winnerDp(int[] arr) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        int N = arr.length;
//        错误点
//        int[][] f = new int[N - 1][N - 1];
//        int[][] g = new int[N - 1][N - 1];
        int[][] f = new int[N][N];
        int[][] g = new int[N][N];
        for (int i = 0; i < N; i++) {
            f[i][i] = arr[i];
        }

        for (int L = N - 1; L >= 0; L--) {
            for (int R = L + 1; R < N; R++) {
                f[L][R] = Math.max(arr[L] + g[L + 1][R], arr[R] + g[L][R - 1]);
                g[L][R] = Math.min(f[L + 1][R], f[L][R - 1]);
            }
        }

        return Math.max(f[0][N - 1], g[0][N - 1]);
    }

    public static void main(String[] args) {
        int[] arr = { 5, 7, 4, 5, 8, 1, 6, 0, 3, 4, 6, 1, 7 };
        System.out.println(winner(arr));
        System.out.println(winnerDp(arr));
    }



}
