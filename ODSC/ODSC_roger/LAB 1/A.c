#include <stdio.h>
int main() {
    int n, a, b, i = 0;
    scanf("%d", &n); 
    while (i < n) {
        scanf("%d", &a);
        scanf("%d", &b);
        int suma = a + b;
        printf("%d\n", suma);
        i += 1;
    }
}