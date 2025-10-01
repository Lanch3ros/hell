#include <stdio.h>

int gcd(int a, int b) {
    while (b != 0) {
        int r = a % b;
        a = b;
        b = r;
    }
    return a;
}

int main(void) {
    int T, caso;
    scanf("%d", &T);
    for (caso = 0; caso < T; caso++) {
        int be, uk, no, ie, fr;
        scanf("%d", &be);
        scanf("%d", &uk);
        scanf("%d", &no);
        scanf("%d", &ie);
        scanf("%d", &fr);

        int cap = gcd(be, gcd(uk, gcd(no, gcd(ie, fr))));

        int p_be = be / cap;
        int p_uk = uk / cap;
        int p_no = no / cap;
        int p_ie = ie / cap;
        int p_fr = fr / cap;

        int total = p_be + p_uk + p_no + p_ie + p_fr;

        printf("%d\n", cap);
        printf("%d\n", p_be);
        printf("%d\n", p_uk);
        printf("%d\n", p_no);
        printf("%d\n", p_ie);
        printf("%d\n", p_fr);
        printf("%d\n", total);
    }
    return 0;
}

