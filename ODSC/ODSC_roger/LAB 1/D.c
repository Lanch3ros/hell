#include <stdio.h>
int main() {
    int n, v, i = 0;
    scanf("%d", &n);
    while (i < n) {
        scanf("%d", &v);
        int vector[2000];   
        for(int j = 0; j < v; j++){
            scanf("%d", &vector[j]);
        }
        int suma = 0;
        for(int j = 0; j < v; j++){
            suma += vector[j];
            }
        printf("%d\n", suma);
        i += 1;
    }
}