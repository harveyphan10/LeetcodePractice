package dp.mit;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @author dungptm2
 */
public class BuildingBLocks {

    static class Box {
        int w;
        int l;
        int h;

        Box(int w, int l, int h) {
            this.w = w;
            this.l = l;
            this.h = h;
        }
    }

    public int maxHeightBoxStacking(Box[] b) {
        int[] L = new int[b.length*3+1];

        Box[] rotB = new Box[b.length*3];
        for (int i = 0; i < b.length; i++) {
            List<Integer> dimentions = Arrays.asList(b[i].w, b[i].l, b[i].h);
            Collections.sort(dimentions);
            Box b1 = new Box(dimentions.get(0), dimentions.get(1), dimentions.get(2));
            Box b2 = new Box(dimentions.get(0), dimentions.get(2), dimentions.get(1));
            Box b3 = new Box(dimentions.get(1), dimentions.get(2), dimentions.get(0));
            rotB[i*3] = b1;
            rotB[i*3+1] = b2;
            rotB[i*3+2] = b3;
        }

        Arrays.sort(rotB, (o1, o2) -> {
            if (o1.w != o2.w) {
                return o1.w - o2.w;
            } else {
                return o1.l - o2.l;
            }
        });

        for (int i = rotB.length-1; i >= 0; i--) {
            L[i] = rotB[i].h;
        }

        for (int i = rotB.length-2; i >= 0; i--) {
            for (int j = i+1; j < rotB.length; j++) {
                if (rotB[i].w < rotB[j].w && rotB[i].l < rotB[j].l) {
                    L[i] = Math.max(L[i], rotB[i].h + L[j]);
                }
            }
        }

        return Arrays.stream(L).max().getAsInt();
    }

    public static void main(String[] args) {
        BuildingBLocks buildingBLocks = new BuildingBLocks();
        Box[] boxes = {new Box(1, 2, 3), new Box(4, 5, 6), new Box(1, 2, 4)};
        System.out.println(buildingBLocks.maxHeightBoxStacking(boxes));
        System.exit(0);
    }

}
