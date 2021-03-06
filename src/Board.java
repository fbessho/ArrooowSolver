import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * ゲームボードの状態を表すオブジェクト
 */
public class Board
{
    // 矢印が{up, right, down, left}向きだった時にrow, columnはどのように動くべきか。
    private static final int[] dx = {0, 1, 0, -1};
    private static final int[] dy = {-1, 0, 1, 0};

    private static final char[] ARROW_TYPES = {'u', 'r', 'd', 'l'};
    private static final String BLANK = ".";
    private static final char BLANK_C = BLANK.charAt(0);

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
            if (s.charAt(i) != BLANK_C) {
                existence += 1 << (24 - i);
            }
        }

        // arrows
        s = s.replace(String.valueOf(ARROW_TYPES[0]), "00");
        s = s.replace(String.valueOf(ARROW_TYPES[1]), "01");
        s = s.replace(String.valueOf(ARROW_TYPES[2]), "10");
        s = s.replace(String.valueOf(ARROW_TYPES[3]), "11");
        s = s.replace(BLANK, "00");
        long arrows = Long.parseLong(s, 2);

        return new Board(arrows, existence);
    }

    private Board(long arrows, int existence)
    {
        this.arrows = arrows;
        this.existence = existence;
    }

    // i番目のセルをタップした時の次のボードの状態を返す。
    public Board tap(int i)
    {
        if (!tappable(i)) {
            throw new RuntimeException("Tap an illegal cell (untappable).");
        }

        // 矢印にそって矢印を消す
        Board newBoard = removeUntilTermination(i, this);

        // 矢印を下、左に落とす
        return newBoard.dropDown().removeBlankColumns();
    }

    /**
     * 矢印にそって矢印を消していく。
     */
    static Board removeUntilTermination(int i, Board board)
    {
        int currentRow = i / 5;
        int currentColumn = i % 5;

        Board newBoard = new Board(board.arrows, board.existence);

        // 矢印の指すセルを見つける
        int arrayType = board.arrowTypeAt(i);
        int nextRow = currentRow + dy[arrayType];
        int nextColumn = currentColumn + dx[arrayType];
        int nextIndex = nextRow * 5 + nextColumn;

        // 指定されたセルを消す
        int shift = 24 - i;
        int existenceMask = ~(1 << shift);
        long arrowsMask = ~(3L << (shift * 2));
        newBoard.existence = newBoard.existence & existenceMask;
        newBoard.arrows = newBoard.arrows & arrowsMask;

        // 矢印の指すセルが存在しなければ終了、そうでなければ矢印をたどることを繰り返す
        if (!newBoard.arrowExistsAt(nextRow, nextColumn)) {
            return newBoard;
        }
        else {
            return Board.removeUntilTermination(nextIndex, newBoard);
        }
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
     * @return 0 -> top, 1 -> right, 2 -> bottom, 3 -> left
     */
    public int arrowTypeAt(int i)
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
        int arrowType = arrowTypeAt(i);

        final int nextRow = row + dy[arrowType];
        final int nextColumn = column + dx[arrowType];

        return arrowExistsAt(nextRow, nextColumn);
    }

    public boolean tappable(int i)
    {
        int row = i / 5;
        int column = i % 5;
        return tappable(row, column);
    }

    /**
     * 矢印を下に落とした時のボードを返す。
     */
    public Board dropDown()
    {
        return this.rotateCW().dropLeft().rotateCCW();
    }

    /**
     * 空の行があれば、左に詰めたものを返す。
     */
    // TODO: Better naming of this method?
    public Board removeBlankColumns()
    {
        Board board = Board.createFromString(this.toString());
        for (int i = 3; i >= 0; i--) {
            boolean allBlankOnAColumn =
                   /**/     (!board.arrowExistsAt(0, i)) &&
                            (!board.arrowExistsAt(1, i)) &&
                            (!board.arrowExistsAt(2, i)) &&
                            (!board.arrowExistsAt(3, i)) &&
                            (!board.arrowExistsAt(4, i));
            if (allBlankOnAColumn) {
                board = board.removeBlankColumn(i);
            }
        }

        return board;
    }

    private Board removeBlankColumn(int column)
    {
        String s = this.toString();
        StringBuilder sb = new StringBuilder();
        for (int row = 0; row < 5; row++) {
            String sn = s.substring(row * 5, row * 5 + 5);
            sb.append(sn.substring(0, column));
            sb.append(sn.substring(column + 1));
            sb.append(BLANK);
        }

        return Board.createFromString(sb.toString());
    }

    /**
     * （矢印の向きはそのままに）位置を時計回りに90度回転したものを返す
     */
    Board rotateCW()
    {
        String s = this.toString();
        StringBuilder sb = new StringBuilder();

        for (int column = 0; column < 5; column++) {
            for (int row = 4; row >= 0; row--) {
                int i = row * 5 + column;
                sb.append(s.charAt(i));
            }
        }

        return Board.createFromString(sb.toString());
    }

    /**
     * （矢印の向きはそのままに）位置を反時計回りに90度回転したものを返す
     */
    Board rotateCCW()
    {
        String s = this.toString();
        StringBuilder sb = new StringBuilder();

        for (int column = 4; column >= 0; column--) {
            for (int row = 0; row < 5; row++) {
                int i = row * 5 + column;
                sb.append(s.charAt(i));
            }
        }

        return Board.createFromString(sb.toString());
    }

    /**
     * 矢印を左に落とした時のボードを返す。
     */
    public Board dropLeft()
    {
        String s = this.toString();
        StringBuilder sb = new StringBuilder();

        for (int row = 0; row < 5; row++) {
            String si = s.substring(row * 5, (row + 1) * 5); // String of each row
            int numOfSpace = StringUtils.countMatches(si, BLANK);

            sb.append(si.replace(BLANK, ""));
            for (int i = 0; i < numOfSpace; i++) {
                sb.append(BLANK);
            }
        }

        return Board.createFromString(sb.toString());
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Board board = (Board) o;

        if (arrows != board.arrows) return false;
        if (existence != board.existence) return false;

        return true;
    }

    @Override
    public int hashCode()
    {
        int result = (int) (arrows ^ (arrows >>> 32));
        result = 31 * result + existence;
        return result;
    }

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder(26);
        for (int i = 0; i < 25; i++) {
            if (arrowExistsAt(i)) {
                int arrowType = arrowTypeAt(i);
                sb.append(ARROW_TYPES[arrowType]);
            }
            else {
                sb.append(BLANK);
            }
        }

        return sb.toString();
    }
}
