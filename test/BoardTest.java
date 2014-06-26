import com.gs.collections.impl.set.mutable.UnifiedSet;
import org.junit.Assert;
import org.junit.Test;

import java.util.Set;

public class BoardTest
{
    @Test
    public void testCreateFromString()
    {
        Board board = Board.createFromString("tttttrrrrrlllllbbbbbttttt");
        Assert.assertEquals(Long.parseLong("0000000000" + "0101010101" + "1111111111" + "1010101010" + "0000000000", 2), board.arrows);
        Assert.assertEquals(Integer.parseInt("11111" + "11111" + "11111" + "11111" + "11111", 2), board.existence);
    }

    @Test
    public void testArrowExistsAt() throws Exception
    {
        Board board = Board.createFromString("ttttt" + "     " + "     " + "     " + "     ");
        for (int i = 0; i < 5; i++) {
            Assert.assertTrue(board.arrowExistsAt(i));
        }
        for (int i = 5; i < 25; i++) {
            Assert.assertFalse(board.arrowExistsAt(i));
        }
    }

    @Test
    public void testTappable() throws Exception
    {
        Board board = Board.createFromString("tttttrrrrrlllllbbbbbttttt");
        Set<Integer> untappableCells = UnifiedSet.newSetWith(0, 1, 2, 3, 4, 9, 10);
        for (int i = 0; i < 25; i++) {
            int row = i / 5;
            int column = i % 5;
            if (untappableCells.contains(i)) {
                Assert.assertFalse("Cell " + i + " should be untappable",  board.tappable(row, column));
            } else {
                Assert.assertTrue("Cell " + i + " should be tappable", board.tappable(row, column));
            }
        }

    }
}