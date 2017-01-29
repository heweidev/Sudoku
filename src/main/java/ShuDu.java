import java.util.*;

/**
 * Created by hewei on 17-1-29.
 */
class State {
    private int[] mData = new int[81];

    public State(int[] data) {
        mData = data;
    }

    public State generate(int i, int j, int num) {
        State state = clone();
        state.mData[pos2index(i, j)] = num;
        return state;
    }

    public boolean isIndexEmpty(int i, int j) {
        return mData[pos2index(i, j)] == 0;
    }

    public List<Integer> getAvailableList(int i, int j) {
        int val;

        // 列
        Set<Integer> set = new HashSet<Integer>();
        for (int k : columnIndexes(i)) {
            val = mData[k];
            if (val != 0) {
                set.add(val);
            }
        }

        // 行
        for (int k : rowIndexes(j)) {
            val = mData[k];
            if (val != 0) {
                set.add(val);
            }
        }

        // 局部块
        for (int k : blockIndexes(i, j)) {
            val = mData[k];
            if (val != 0) {
                set.add(val);
            }
        }

        return empty(set);
    }

    public static int[] nextPos(State state, int cur) {
        for (int i = 0; i < 81; i++) {
            int index = (cur + i) % 81;
            if (state.mData[index] == 0) {
                int[] pos = new int[2];
                pos[0] = index % 9;
                pos[1] = index / 9;
                return pos;
            }
        }
        return null;
    }

    static List<Integer> columnIndexes(int i) {
        List<Integer> list = new ArrayList<Integer>();
        for (int k = 0; k < 9; k++) {
            list.add(i + k * 9);
        }
        return list;
    }

    static List<Integer> rowIndexes(int j) {
        List<Integer> list = new ArrayList<Integer>();
        for (int k = 0; k < 9; k++) {
            list.add(k + j * 9);
        }
        return list;
    }

    static List<Integer> blockIndexes(int i, int j) {
        List<Integer> list = new ArrayList<Integer>();
        int b_x = i / 3;
        int b_y = j / 3;
        int head = b_x * 3 + b_y * 27;
        for (int k = 0; k < 3; k++) {
            for (int m = 0; m < 3; m++) {
                list.add(head + k + m * 9);
            }
        }
        return list;
    }

    private List<Integer> empty(Set<Integer> set) {
        List<Integer> list = new ArrayList<Integer>();
        for (int i = 1; i <= 9; i++) {
            if (!set.contains(i)) {
                list.add(i);
            }
        }

        return list;
    }

    static int pos2index(int i, int j) {
        return i + j * 9;
    }

    @Override
    protected State clone() {
        return new State(Arrays.copyOf(mData, mData.length));
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                builder.append(mData[pos2index(j, i)]);
                builder.append(" ");
            }
            builder.append("\n");
        }
        return builder.toString();
    }
}


public class ShuDu {
    private int mIndex = 0;

    public void start(int[] data) {
        State state = new State(data);
        Stack<State> stack = new Stack<State>();
        int cur = 0;
        stack.push(state);
        int count = 0;

        while (!stack.empty() && (state = stack.pop()) != null){
            System.out.println(state);
            count++;

            int[] pos = State.nextPos(state, cur);
            if (pos == null) {
                break;
            }

            System.out.println(Arrays.toString(pos));
            List<Integer> list = state.getAvailableList(pos[0], pos[1]);
            if (list == null || list.isEmpty()) {
                System.out.println("list is empty！");
                continue;
            }

            for (int num : list) {
                System.out.println(num);
                State ns = state.generate(pos[0], pos[1], num);
                stack.push(ns);
            }
        };

        System.out.println(state);
        System.out.println(count);
    }

    public void test() {
        for (int i : State.blockIndexes(3, 4)) {
            System.out.println(i);
        }

        for (int i : State.rowIndexes(3)) {
            System.out.println(i);
        }

        for (int i : State.columnIndexes(4)) {
            System.out.println(i);
        }
    }
}
