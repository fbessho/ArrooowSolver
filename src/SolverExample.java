import com.google.common.base.Stopwatch;
import com.gs.collections.impl.list.mutable.FastList;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Arrooowに入っている問題をすべて解き、解答例を実行時間と共に表示する。
 */
public class SolverExample
{
    public static void main(String[] args)
    {
        List<String> stageList = FastList.newListWith(
                /* stage  1 */  "rrrrrlllllrrrrrlllllrrrrr",
                /* stage  2 */  "rrrrdurrdduuudduuulduulll",
                /* stage  3 */  "rluuuruuuurruuurrruurrrru",
                /* stage  4 */  "drdrdrurudrdrdduruudullld",
                /* stage  5 */  "ddddddddddrrrrruuuuuuuuuu",
                /* stage  6 */  "rrrdldrddddudddduddddulll",
                /* stage  7 */  "drduududuuuuduuuuduururul",
                /* stage  8 */  "rrrrdrdddddllllduuurrrrrr",
                /* stage  9 */  "ldldllldduldddulldduullru",
                /* stage 10 */  "lllllrdrruudlllurrrdlldll",
                /* stage 11 */  "rrrrdulllduulldddlldlllll",
                /* stage 12 */  "urrduuulduuulluulllulrrru",
                /* stage 13 */  "lddddudlllurrrdulurdullll",
                /* stage 14 */  "udlllrrrdrurdrururrrllull",
                /* stage 15 */  "drddddllduludddulllurrddl",
                /* stage 16 */  "ldllrdlduldldrurddruurruu",
                /* stage 17 */  "udldludurdudullurrrdullll",
                /* stage 18 */  "lulllrrrrdullldullulldull",
                /* stage 19 */  "lrrlllrllllrrlllrllllrrll",
                /* stage 20 */  "ruudlullludlulurrrrrdulul",
                /* stage 21 */  "uldlllrrrdulllluududlllld",
                /* stage 22 */  "rrrrduldllldddrdldrdrdddl",
                /* stage 23 */  "dlduldudludrrrrdurrururdu",
                /* stage 24 */  "lllrurrrudlrullrurrrullll",
                /* stage 25 */  "rrdrdrrrddurrrduulldullld",
                /* stage 26 */  "uldrdurdudurdduurrrruuuld",
                /* stage 27 */  "drdrlurruururuuuruduruduu",
                /* stage 28 */  "rrddluldrdruddludlrdulull",
                /* stage 29 */  "rrrrrudllludrruududlululu",
                /* stage 30 */  "rrrrduddlluldrldllulrrrru",
                /* stage 31 */  "uluddruudrldrrdrruuduuldr",
                /* stage 32 */  "llllllrrrllrlrllrrrllllll",
                /* stage 33 */  "uruuuuddduudrduuddduuuuuu",
                /* stage 34 */  "drrlllrlrllrlrllrlrlurrrl",
                /* stage 35 */  "rrrrlrrrlrrrlrrrlrrrlrrrr",
                /* stage 36 */  "ddlrldlururuuuldlldlurudu",
                /* stage 37 */  "rurlrudllrrlulurlrduurduu",
                /* stage 38 */  "rrruddrdudrlulrdruudrludl",
                /* stage 39 */  "rrurdlldddurrddrrurdrurud",
                /* stage 40 */  "ldlrdulurlurrduddurdlrrrl"
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
