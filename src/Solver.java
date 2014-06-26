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
}
