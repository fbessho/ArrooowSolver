import com.gs.collections.impl.list.mutable.FastList;

import java.util.List;

/**
 * Solve Arrooow program
 */
public class Solver
{
    public List<Integer> solve(Board board)
    {
        if (board.isSolved())
            return FastList.newList();

        for (int i = 0; i < 25; i++) {
            if (board.tappable(i)) {
                List<Integer> tapSequence = solve(board.tap(i));
                if (tapSequence != null) {
                    FastList<Integer> ans = FastList.newListWith(i);
                    ans.addAll(tapSequence);
                    return ans;
                }
            }
        }

        // unsolved
        return null;
    }

    public static void main(String[] args)
    {
        Board board = Board.createFromString(args[0]);
        List<Integer> solvedSequence = new Solver().solve(board);

        int count = 1;
        for (int i : solvedSequence) {
            int row = i / 5;
            int column = i % 5;
            System.out.println(String.format("[%2d] row: %d, column %d", count, row + 1, column + 1));
            count++;
        }
    }
}
