#include <stdio.h>

typedef long long i64;

static i64 gcd(i64 a, i64 b) {
    while (b) { i64 t = a % b; a = b; b = t; }
    return a < 0 ? -a : a;
}

int main(void) {
    int T;
    if (scanf("%d", &T) != 1) return 0;
    int tc;
    for (tc = 0; tc < T; ++tc) {
        i64 a, b;
        if (scanf("%lld", &a) != 1) return 0;
        if (scanf("%lld", &b) != 1) return 0;

        /* Se asume 0 < a < b. Reducimos por si acaso. */
        i64 g = gcd(a, b);
        a /= g; b /= g;

        while (a != 0) {
            /* n = ceil(b/a) */
            i64 n = (b + a - 1) / a;
            printf("%lld\n", n);

            /* a/b <- a/b - 1/n = (a*n - b)/(b*n) */
            a = a * n - b;
            b = b * n;

            g = gcd(a, b);
            a /= g; b /= g;
        }
        puts("0");
    }
    return 0;
}

