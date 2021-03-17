package ee.ttu.algoritmid.trampoline;

import java.util.ArrayList;
import java.util.List;

public class HW02 implements TrampolineCenter {

    private List<Result> results = new ArrayList<>();

    @Override
    public Result play(Trampoline[][] map) {
        results = new ArrayList<>();
        if (map != null) {
            Trampoline start = map[0][0];
            Trampoline end = map[map.length - 1][map[map.length - 1].length - 1];
            return playRec(map);
        }
        return null;
    }

    private Result playRec(Trampoline[][] map) {
        moveRight(map, 0, 0, new ResultClass());
        moveDown(map, 0, 0, new ResultClass());
        Result shortestPath = null;
        for (Result x : results) {
            if (shortestPath == null || x.getJumps().size() < shortestPath.getJumps().size()) {
                shortestPath = x;
            }
        }
        System.out.println(shortestPath);
        return shortestPath;
    }

    private Result moveRight(Trampoline[][] map, int currentX, int currentY, ResultClass result) {
        Trampoline current = map[currentY][ currentX];
        if (currentX + current.getJumpForce() >= map[0].length) {
            return null;
        }
        if (current == map[map.length - 1][map[map.length - 1].length - 1]) {
            results.add(result);
            return result;
        } else {
            int newX = (currentX + current.getJumpForce()) % map[0].length;
            if (currentY == map.length - 2
                    && newX == map[0].length - 1
                    && map[currentY][newX].getJumpForce() != 1) {
                moveDown(map, currentX, currentY, result);
            } else {
                ResultClass moveRight = new ResultClass(result.getJumps(), result.getTotalFine());
                moveRight.addAJump("E" + current.getJumpForce());
                if ((currentX + current.getJumpForce()) + (map[currentY][newX].getJumpForce()) >= map[0].length) {
                    moveDown(map, newX, currentY, moveRight);
                } else {
                    moveRight(map, newX, currentY, moveRight);
                }
            }
            return null;
        }
    }

    private Result moveDown(Trampoline[][] map, int currentX, int currentY, ResultClass result) {
        Trampoline current = map[currentY][ currentX];
        if (currentY + current.getJumpForce() >= map.length) {
            return null;
        }
        if (current == map[map.length - 1][map[map.length - 1].length - 1]) {
            results.add(result);
            return result;
        } else {
            int newY = (currentY + current.getJumpForce()) % map.length;
            if (newY == map.length - 1
                    && currentX == map[0].length -2
                    && map[newY][currentX].getJumpForce() != 1) {
                moveRight(map, currentX, currentY, result);
            } else {
                ResultClass moveDown = new ResultClass(result.getJumps(), result.getTotalFine());
                moveDown.addAJump("S" + current.getJumpForce());
                if ((currentY + current.getJumpForce()) + (map[newY][currentX].getJumpForce()) >= map.length) {
                    moveRight(map, currentX, newY, moveDown);
                } else {
                    moveDown(map, currentX, newY, moveDown);
                }
            }
            return null;
        }
    }

    public static class TrampolineImpl implements Trampoline {
        int force;
        Type type;

        public TrampolineImpl(int force, Type type) {
            this.force = force;
            this.type = type;
        }

        @Override
        public int getJumpForce() {
            return force;
        }

        @Override
        public Type getType() {
            return type;
        }
    }

    public static void main(String[] args) {
        HW02 hw02 = new HW02();
        Trampoline[][] map = new Trampoline[2][2];
        map[0][0] = new TrampolineImpl(1, Trampoline.Type.NORMAL);
        map[0][1] = new TrampolineImpl(1, Trampoline.Type.NORMAL);
        map[1][0] = new TrampolineImpl(1, Trampoline.Type.NORMAL);
        map[1][1] = new TrampolineImpl(0, Trampoline.Type.NORMAL);
//      [1, 1]
//      [1, 0]
        List<String> result = hw02.play(map).getJumps();
        System.out.println("\n" + result + ", " + result.size() + " jumps, =, +- 2");
        // sõltuvalt alguse suunast: [E1, S1] või [S1, E1]

        Trampoline[][] map2 = new Trampoline[3][3];
        map2[0][0] = new TrampolineImpl(1, Trampoline.Type.NORMAL);
        map2[0][1] = new TrampolineImpl(2, Trampoline.Type.NORMAL);
        map2[0][2] = new TrampolineImpl(3, Trampoline.Type.NORMAL);
        map2[1][0] = new TrampolineImpl(2, Trampoline.Type.NORMAL);
        map2[1][1] = new TrampolineImpl(9, Trampoline.Type.NORMAL);
        map2[1][2] = new TrampolineImpl(2, Trampoline.Type.NORMAL);
        map2[2][0] = new TrampolineImpl(2, Trampoline.Type.NORMAL);
        map2[2][1] = new TrampolineImpl(1, Trampoline.Type.NORMAL);
        map2[2][2] = new TrampolineImpl(0, Trampoline.Type.NORMAL);
//      [1, 2, 3]
//      [2, 9, 2]
//      [2, 1, 0]
        List<String> result2 = hw02.play(map2).getJumps();
        System.out.println("\n" + result2 + ", " + result2.size() + " jumps, = 3, +- 2");
        // = [E1, S2, E1]
        // +- sõltuvalt alguse suunast: [E2, S2] või [S2, E2]

        Trampoline[][] map3 = new Trampoline[9][9];
        int[] array =
                        {1, 1, 20, 1, 2, 2, 1, 2, 2,
                        20, 1, 1, 10, 2, 2, 1, 2, 2,
                        1, 10, 1, 1, 20, 2, 1, 2, 2,
                        2, 1, 10, 1, 1, 20, 1, 2, 2,
                        1, 2, 2, 10, 1, 1, 10, 2, 2,
                        2, 1, 1, 1, 10, 1, 1, 20, 2,
                        1, 2, 2, 1, 2, 10, 1, 1, 20,
                        2, 1, 1, 1, 2, 2, 10, 1, 1,
                        3, 2, 1, 1, 2, 2, 1, 10, 0};
        int k = 0;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                map3[i][j] = new TrampolineImpl(array[k], Trampoline.Type.NORMAL);
                k++;
            }
        }
        hw02.play(map3);
        List<String> result3 = hw02.play(map3).getJumps();
        System.out.println("\n" + result3 + ", " + result3.size() + " jumps, = 16, +- 7");
        // = [E1, S1, E1, S1, E1, S1, E1, S1, E1, S1, E1, S1, E1, S1, E1, S1], 16 hüpet
        // +- [S2, E2, E1, E2, E3, S3, S3], 7 hüpet

        Trampoline[][] map4 = new Trampoline[9][9];
        int[] array2 =
                {1, 10, 20, 1, 2, 2, 1, 2, 2,
                        1, 10, 1, 10, 2, 2, 1, 2, 2,
                        1, 10, 1, 1, 20, 2, 1, 2, 2,
                        2, 1, 10, 1, 1, 20, 1, 2, 2,
                        1, 2, 2, 10, 1, 1, 10, 2, 2,
                        2, 1, 1, 1, 10, 1, 1, 20, 2,
                        1, 2, 2, 1, 2, 10, 1, 1, 20,
                        1, 1, 1, 1, 2, 2, 10, 1, 1,
                        1, 2, 1, 1, 2, 2, 1, 1, 0};
        k = 0;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                map4[i][j] = new TrampolineImpl(array2[k], Trampoline.Type.NORMAL);
                k++;
            }
        }
        List<String> result4 = hw02.play(map4).getJumps();
        System.out.println("\n" + result4 + ", " + result4.size() + " jumps, = 11, +- 7");
        // = [S1, S1, S1, S2, E2, S1, E2, S2, E2, E1, E1], 11 hüpet
        // +- [S2, E2, E1, E2, E3, S3, S3], 7 hüpet
        // muidugi võite iseennast, mind ja ülejäänuid üllatada lühemate tulemustega :)
    }
}