package problems;

/**
 * @author dungptm2
 */
public class RearrangeBinaryString {

    /**
     *  "0110101"
     *  "1011010"
     *  "1101100"
     *  "1110100"
     *  "1111000"
     */
    public int secondsToRemoveOccurrences(String s) {
        int count0 = 0;
        int waitingTime = 0;
        int last1Occur = s.lastIndexOf('1');
        for (int i = 0; i < last1Occur; i++) {
            if (s.charAt(i) == '0') {
                count0++;
            }
            if (i > 0 && s.charAt(i) == '1' && s.charAt(i + 1) == '1' && count0 > 0) {
                waitingTime++;
            }
            if (i > 0 && s.charAt(i) == '0' && s.charAt(i + 1) == '0' && waitingTime > 0) {
                waitingTime--;
            }
        }
        return count0 + waitingTime;
    }

    public static void main(String[] args) {
        System.out.println(new RearrangeBinaryString().secondsToRemoveOccurrences("001011"));
        System.exit(0);
    }
}
