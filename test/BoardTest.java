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

    @Test
    public void testRemoveUntilTermination() throws Exception
    {
        Board board = Board.createFromString("tttttrrrrrlllllbbbbbttttt");
        Board newBoard = Board.removeUntilTermination(6, board);
        Assert.assertEquals(Board.createFromString("ttttt" + "r    " + "lllll" + "bbbbb" + "ttttt"), newBoard);
    }

    @Test
    public void testRotateCW() throws Exception
    {
        Board board = Board.createFromString("tttttrrrrrlllllbbbbbttttt");
        Board expectedBoard = Board.createFromString(
                /**/    "tblrt" +
                        "tblrt" +
                        "tblrt" +
                        "tblrt" +
                        "tblrt"
        );

        Assert.assertEquals(expectedBoard, board.rotateCW());
    }

    @Test
    public void testRotateCCW() throws Exception
    {
        Board board = Board.createFromString("tttttrrrrrlllllbbbbbttttt");
        Board expectedBoard = Board.createFromString(
                /**/    "trlbt" +
                        "trlbt" +
                        "trlbt" +
                        "trlbt" +
                        "trlbt"
        );

        Assert.assertEquals(expectedBoard, board.rotateCCW());
    }


    @Test
    public void testDropDown() throws Exception
    {
        Board board = Board.createFromString(
                /*    */  "tt t "
                        + "r rr "
                        + "ll  l"
                        + "b   b"
                        + "ttt t"
        );

        Board expectedBoard = Board.createFromString(
                /*    */  "t    "
                        + "r    "
                        + "lt  l"
                        + "blrtb"
                        + "tttrt"
        );

        Assert.assertEquals(expectedBoard, board.dropDown());
    }

    @Test
    public void testDropLeft() throws Exception
    {
        Board board = Board.createFromString(
                /*    */  "tt t "
                        + "r rr "
                        + "ll  l"
                        + "b   b"
                        + "ttt t"
        );

        Board expectedBoard = Board.createFromString(
                /*    */  "ttt  "
                        + "rrr  "
                        + "lll  "
                        + "bb   "
                        + "tttt "
        );

        Assert.assertEquals(expectedBoard, board.dropLeft());
    }

    @Test
    public void testToString() throws Exception
    {
        String string = "ttttt rrrrlllllbbbbbttttt";
        Assert.assertEquals(string, Board.createFromString(string).toString());
    }
}
