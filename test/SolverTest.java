import com.google.common.base.Stopwatch;
import com.gs.collections.impl.list.mutable.FastList;
import com.gs.collections.impl.test.Verify;
import org.junit.Assert;
import org.junit.Test;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

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

    /**
     * Arroooowに入っている問題全てを解く
     */
    @Test
    public void solveAll() throws Exception
    {
        List<String> stageList = FastList.newListWith(
                /* stage  1 */  "rrrrrlllllrrrrrlllllrrrrr",
                /* stage  2 */  "rrrrbtrrbbtttbbtttlbttlll",
                /* stage  3 */  "rltttrttttrrtttrrrttrrrrt",
                /* stage  4 */  "brbrbrtrtbrbrbbtrttbtlllb",
                /* stage  5 */  "bbbbbbbbbbrrrrrtttttttttt",
                /* stage  6 */  "rrrblbrbbbbtbbbbtbbbbtlll",
                /* stage  7 */  "brbttbtbttttbttttbttrtrtl",
                /* stage  8 */  "rrrrbrbbbbbllllbtttrrrrrr",
                /* stage  9 */  "lblblllbbtlbbbtllbbttllrt",
                /* stage 10 */  "lllllrbrrttbllltrrrbllbll",
                /* stage 11 */  "rrrrbtlllbttllbbbllblllll",
                /* stage 12 */  "trrbtttlbtttllttllltlrrrt",
                /* stage 13 */  "lbbbbtbllltrrrbtltrbtllll",
                /* stage 14 */  "tblllrrrbrtrbrtrtrrrlltll",
                /* stage 15 */  "brbbbbllbtltbbbtllltrrbbl",
                /* stage 16 */  "lbllrblbtlblbrtrbbrttrrtt",
                /* stage 17 */  "tblbltbtrbtbtlltrrrbtllll",
                /* stage 18 */  "ltlllrrrrbtlllbtlltllbtll",
                /* stage 19 */  "lrrlllrllllrrlllrllllrrll",
                /* stage 20 */  "rttbltllltbltltrrrrrbtltl",
                /* stage 21 */  "tlblllrrrbtllllttbtbllllb",
                /* stage 22 */  "rrrrbtlblllbbbrblbrbrbbbl",
                /* stage 23 */  "blbtlbtbltbrrrrbtrrtrtrbt",
                /* stage 24 */  "lllrtrrrtblrtllrtrrrtllll",
                /* stage 25 */  "rrbrbrrrbbtrrrbttllbtlllb",
                /* stage 26 */  "tlbrbtrbtbtrbbttrrrrtttlb",
                /* stage 27 */  "brbrltrrttrtrtttrtbtrtbtt",
                /* stage 28 */  "rrbbltlbrbrtbbltblrbtltll",
                /* stage 29 */  "rrrrrtbllltbrrttbtbltltlt",
                /* stage 30 */  "rrrrbtbblltlbrlblltlrrrrt",
                /* stage 31 */  "tltbbrttbrlbrrbrrttbttlbr",
                /* stage 32 */  "llllllrrrllrlrllrrrllllll",
                /* stage 33 */  "trttttbbbttbrbttbbbtttttt",
                /* stage 34 */  "brrlllrlrllrlrllrlrltrrrl",
                /* stage 35 */  "rrrrlrrrlrrrlrrrlrrrlrrrr",
                /* stage 36 */  "bblrlbltrtrtttlbllbltrtbt",
                /* stage 37 */  "rtrlrtbllrrltltrlrbttrbtt",
                /* stage 38 */  "rrrtbbrbtbrltlrbrttbrltbl",
                /* stage 39 */  "rrtrbllbbbtrrbbrrtrbrtrtb",
                /* stage 40 */  "lblrbtltrltrrbtbbtrblrrrl"
        );

        Stopwatch sw = Stopwatch.createUnstarted();

        // TODO: better variable names?
        List<String> records = FastList.newList();
        List<Long> recordsInMicroSec = FastList.newList();
        long sum = 0;

        for (int i = 0; i < 40; i++) {
            sw.start();
            System.out.println(String.format("----------\nStage %d\n", i + 1));
            Solver.main(new String[]{stageList.get(i)});
            sw.stop();
            System.out.println("(" + sw + ")");

            // collect info for summary
            records.add(sw.toString());
            long elapsed = sw.elapsed(TimeUnit.MICROSECONDS);
            recordsInMicroSec.add(elapsed);
            sum += elapsed;

            sw.reset();
        }

        // -------------
        // Print summary
        System.out.println("\n----- Summary -----");

        // max, min, ave
        long max_microsec = Collections.max(recordsInMicroSec);
        long min_microsec = Collections.min(recordsInMicroSec);
        long ave_microsec = sum / 40;
        System.out.println("Max: " + max_microsec/1000 + "ms");
        System.out.println("Min: " + min_microsec + "μs");
        System.out.println("Ave: " + ave_microsec/1000 + "ms");
        System.out.println();

        // Record for each stage
        for (int i = 0; i < 40; i++) {
            System.out.println(String.format("Stage %2d: %s", i + 1, records.get(i)));
        }
    }
}
