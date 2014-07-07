import com.gs.collections.impl.list.mutable.FastList;

import java.util.List;

/**
 * Solve Arrooow program
 *
 * It receives a problem as an argument of the main function and print an answer of it (the sequence to tap to solve
 * the problem).
 * An input of the program is a 25-character-length string. The 1st-5th characters represent the first row,
 * the 6th - 10th do the second row, and so on, and each arrow is written as follows.
 *   Right arrow: r (right)
 *   Left arrow:  l (left)
 *   Up arrow:    t (top)
 *   Down arrow:  b (bottom)
 *
 * In the example below, the top row of the problem is composed by "-> -> v -> v", where -> is a right arrow and
 * v is a down arrow, likewise the second row is "-> -> -> v v".
 *
 * If you tap the arrows at the position in the output below in the same order, every arrow disappears (solved).
 *
 * == Example usage ==
 * java Solver rrbrbrrrbbtrrrbttllbtlllb
 *
 * == Example output ==
 * [ 1] row: 1, column 1
 * [ 2] row: 2, column 1
 * [ 3] row: 3, column 2
 * [ 4] row: 4, column 1
 * [ 5] row: 4, column 4
 * [ 6] row: 4, column 5
 * [ 7] row: 5, column 2
 * [ 8] row: 5, column 2
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
