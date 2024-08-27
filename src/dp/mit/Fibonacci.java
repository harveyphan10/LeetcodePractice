package dp.mit;

/**
 * @author dungptm2
 */
public class Fibonacci {
    public int fib(int n) {
        int[] F = new int[n + 1];
        F[0] = 0;
        F[1] = 1;
        for (int i = 2; i <=n; i++) {
            F[i] = F[i-1] + F[i-2];
        }
        return F[n];
    }

    public static void main(String[] args) {
        System.out.println(new Fibonacci().fib(5));
        System.exit(0);
    }
}
