package ee.ttu.algoritmid.trampoline;

import java.util.ArrayList;
import java.util.List;

public class ResultClass implements Result {

    private List<String> jumps = new ArrayList<>();
    private int totalFine = 0;

    public ResultClass() {
    }

    public ResultClass(List<String> jumps, int totalFine) {
        this.jumps = jumps;
        this.totalFine =  totalFine;
    }

    public void addAJump(String jump) {
        jumps.add(jump);
    }

    public void addFine(int x) {
        this.totalFine += x;
    }

    @Override
    public List<String> getJumps() {
        return jumps;
    }

    @Override
    public int getTotalFine() {
        return totalFine;
    }
}
