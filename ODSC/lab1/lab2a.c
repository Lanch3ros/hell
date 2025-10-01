#include <stdio.h>
#include <math.h>

int find_roots(float a, float b, float c, float *r1, float *r2) {
    if (a == 0.0f) {
        return 0;
    }
    float D = b*b - 4.0f*a*c;
    if (D < 0.0f) {
        return 0;
    } else if (D == 0.0f) {
        *r1 = (-b) / (2.0f*a);
        return 1;
    } else {
        float s = sqrtf(D);
        *r1 = (-b + s) / (2.0f*a);
        *r2 = (-b - s) / (2.0f*a);
        return 2;
    }
}

int main(void) {
    int T, i;
    scanf("%d", &T);

    for (i = 0; i < T; i++) {
        float a, b, c;
        scanf("%f", &a);
        scanf("%f", &b);
        scanf("%f", &c);

        float x1 = 0.0f, x2 = 0.0f;
        int kind = find_roots(a, b, c, &x1, &x2);

        if (kind == 0) {
            printf("no roots\n");
        } else if (kind == 1) {
            printf("%.2f\n", x1);
        } else {
            printf("%.2f\n", x1);
            printf("%.2f\n", x2);
        }
    }
    return 0;
}

