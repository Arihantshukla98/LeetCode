class Solution {
    private static final long MOD = 1_000_000_007L;

    public int zigZagArrays(int n, int l, int r) {
        int range = r - l + 1;

        if (n == 1) {
            return range;
        }

        if (n == 2) {
            return (int) ((1L * range * (range - 1)) % MOD);
        }

        int size = 2 * range;

        long[][] mat = new long[size][size];

        for (int v = 0; v < range; v++) {
            // Previous move was UP, next value must be smaller
            for (int p = 0; p < v; p++) {
                mat[v][range + p] = 1;
            }

            // Previous move was DOWN, next value must be larger
            for (int p = v + 1; p < range; p++) {
                mat[range + v][p] = 1;
            }
        }

        long[][] power = matrixPower(mat, n - 2, size);

        long[] initial = new long[size];

        for (int prev = 0; prev < range; prev++) {
            for (int cur = 0; cur < range; cur++) {
                if (prev < cur) {
                    initial[cur]++;
                } else if (cur < prev) {
                    initial[cur + range]++;
                }
            }
        }

        long total = 0;

        for (int i = 0; i < size; i++) {
            long ways = 0;

            for (int j = 0; j < size; j++) {
                ways = (ways + power[i][j] * initial[j]) % MOD;
            }

            total = (total + ways) % MOD;
        }

        return (int) total;
    }

    private long[][] multiply(long[][] a, long[][] b, int size) {
        long[][] c = new long[size][size];

        for (int i = 0; i < size; i++) {
            for (int k = 0; k < size; k++) {
                if (a[i][k] == 0) continue;

                long aik = a[i][k];

                for (int j = 0; j < size; j++) {
                    if (b[k][j] == 0) continue;

                    c[i][j] = (c[i][j] + aik * b[k][j]) % MOD;
                }
            }
        }

        return c;
    }

    private long[][] matrixPower(long[][] base, int exp, int size) {
        long[][] result = new long[size][size];

        for (int i = 0; i < size; i++) {
            result[i][i] = 1;
        }

        while (exp > 0) {
            if ((exp & 1) == 1) {
                result = multiply(result, base, size);
            }

            base = multiply(base, base, size);
            exp >>= 1;
        }

        return result;
    }
}