package greedyAlgorithm;

import java.util.Arrays;
import java.util.Comparator;

public class BestArrange {

    public static class Program {
        int start;
        int end;

        public Program(int s, int e) {
            start = s;
            end = e;
        }
    }


    // 暴力递归
    public static int bestArrange1(Program[] programs) {
        if (programs == null || programs.length == 0) {
            return 0;
        }
        return process(programs, 0);
    }

    // 意义：传入【Program[] programs, int timeLine】它能够返回在这个TimeLine 之后，所能 安排的 最大 会议 数量
    public static int process(Program[] programs, int timeLine) {
        if (programs.length == 0) {
            return 0;
        }
        int ans = 0;
        for (int i = 0; i < programs.length; i++) {
            if (programs[i].start >= timeLine) {
                int tmp = 1 + process(copyButExcept(programs, i), programs[i].end);
                ans = Math.max(ans, tmp);
            }
        }
        return ans;
    }


    public static Program[] copyButExcept(Program[] programs, int i) {
        Program[] ans = new Program[programs.length - 1];
        int index = 0;
        for (int k = 0; k < programs.length; k++) {
            if (k != i) {
                ans[index++] = programs[k];
            }
        }
        return ans;
    }


    // 贪心算法
    // 策略： 以end 最早进行排序，end小的排在前面
    public static class MyComparator implements Comparator<Program> {
        public int compare(Program o1, Program o2) {
            return o1.end - o2.end;
        }
    }

    public static int bestArrange2(Program[] programs) {
        Arrays.sort(programs, new MyComparator());
        int timeLine = 0;
        int ans = 0;
        for (int i = 0; i < programs.length; i++) {
            if (programs[i].start >= timeLine) {
                ans++;
                timeLine = programs[i].end;
            }
        }
        return ans;
    }


    // for test
    public static Program[] generatePrograms(int programSize, int timeMax) {
        Program[] ans = new Program[(int) (Math.random() * (programSize + 1))];
        for (int i = 0; i < ans.length; i++) {
            int r1 = (int) (Math.random() * (timeMax + 1));
            int r2 = (int) (Math.random() * (timeMax + 1));
            if (r1 == r2) {
                ans[i] = new Program(r1, r1 + 1);
            } else {
                ans[i] = new Program(Math.min(r1, r2), Math.max(r1, r2));
            }
        }
        return ans;
    }

    public static void main(String[] args) {
        int programSize = 12;
        int timeMax = 20;
        int timeTimes = 1000000;
        for (int i = 0; i < timeTimes; i++) {
            Program[] programs = generatePrograms(programSize, timeMax);
            if (bestArrange1(programs) != bestArrange2(programs)) {
                System.out.println("Oops!");
            }
        }
        System.out.println("finish!");
    }


}
