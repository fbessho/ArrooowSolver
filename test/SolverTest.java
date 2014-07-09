import com.gs.collections.impl.test.Verify;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class SolverTest
{
    @Test
    public void testSolve1() throws Exception
    {
        Board board = Board.createFromString("rrrrd" + "dllll" + "rrrrd" + "dllll" + "rrrrr");
        List<Integer> sequence = new Solver().solve(board);
        Assert.assertNotNull(sequence);
        Verify.assertTrue(board.tapInSequence(sequence).isSolved());
    }

    @Test
    public void testSolve2() throws Exception
    {
        Board board = Board.createFromString("ldlrd" + "ulurl" + "urrdu" + "ddurd" + "lrrrl");
        List<Integer> sequence = new Solver().solve(board);
        Assert.assertNotNull(sequence);
        Verify.assertTrue(board.tapInSequence(sequence).isSolved());
    }

}
