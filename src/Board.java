import java.util.List;

/**
 * ゲームボードの状態を表すオブジェクト
 */
public class Board
{
    // 矢印が{top, right, bottom, left}向きだった時にrow, columnはどのように動くべきか。
    private static final int[] dx = { 0,  1,  0, -1};
    private static final int[] dy = {-1,  0,  1,  0};

    long arrows;
    int existence;

    public static Board createFromString(String s)
    {
        if (s.length() != 25) {
            throw new RuntimeException("Board creation error: the length of string must be 25 but " + s.length() + ", s: " + s);
        }

        // existence
        int existence = 0;
        for (int i = 0; i < 25; i++) {
            if (s.charAt(i) != ' ') {
                existence += 1 << (24 - i);
            }
        }

        // arrows
        s = s.replaceAll("t", "00");
        s = s.replaceAll("r", "01");
        s = s.replaceAll("b", "10");
        s = s.replaceAll("l", "11");
        s = s.replaceAll(" ", "00");
        long arrows = Long.parseLong(s, 2);

        return new Board(arrows, existence);
    }

    private Board(long arrows, int existence)
    {
        this.arrows = arrows;
        this.existence = existence;
    }


    private Board tap(int row, int column)
    {
        return tap(row * 5 + column);
    }

    // i番目のセルをタップした時の次のボードの状態を返す。
    public Board tap(int i)
    {
        if (!tappable(i)) {
            throw new RuntimeException("Tap illegal cell (untappable).");
        }
        return null;


    }

    private static Board removeAndGoNext(int i, Board board)
    {
        int currentRow = i / 5;
        int currentColumn = i % 5;

        int mask = ~(1 << (24-i));
        int existence = board.existence & mask;

        int arrayType = board.arrayTypeAt(i);
        // int nextRow =
        return null;
    }

    public Board tapInSequence(List<Integer> sequence)
    {
        Board currentBoard = this;
        for (int i : sequence) {
            currentBoard = currentBoard.tap(i);
        }

        return currentBoard;
    }

    public boolean isSolved()
    {
        return existence == 0;
    }

    // そのセルに矢印が存在するか
    public boolean arrowExistsAt(int i)
    {
        return (this.existence & (1 << (24 - i))) != 0;
    }

    // そのセルに矢印が存在するか。
    // もし row か column がゲームボードの範囲外であった場合はFalseを返す。
    public boolean arrowExistsAt(int row, int column)
    {
        if (row < 0 || row > 4 || column < 0 || column > 4) {
            return false;
        }

        return arrowExistsAt(row * 5 + column);
    }

    /**
     * i番目のセルの矢印の向きを取得
     * @param i
     * @return 0 -> top, 1 -> right, 2 -> bottom, 3 -> left
     */
    public int arrayTypeAt(int i)
    {
        int shift = (24 - i) * 2;
        long mask = 3L << shift;
        return (int) ((this.arrows & mask) >> shift);
    }

    // そのセルをタップした時に2個以上の矢印が消えるかどうか
    // タップしたセルに矢印が存在し、かつ矢印の指す先のセルに矢印が存在するときTrue
    public boolean tappable(int row, int column)
    {
        if (!arrowExistsAt(row, column)) {
            return false;
        }

        int i = row * 5 + column;
        int arrowType = arrayTypeAt(i);

        final int nextRow = row + dx[arrowType];
        final int nextColumn = column + dy[arrowType];

        return arrowExistsAt(nextRow, nextColumn);
    }

    public boolean tappable(int i)
    {
        int row = i / 5;
        int column = i % 5;
        return tappable(row, column);
    }
}
