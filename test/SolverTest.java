import com.gs.collections.impl.test.Verify;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class SolverTest
{

    @Test
    public void testSolve() throws Exception
    {
        Board board = Board.createFromString("lblrb" + "tltrl" + "trrbt" + "bbtrb" + "lrrrl");
        List<Integer> sequence = new Solver().solve(board);
        Assert.assertNotNull(sequence);
        Verify.assertTrue(board.tapInSequence(sequence).isSolved());
    }
}
