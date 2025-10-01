#include <stdio.h>
int main() {
    int n, v, i = 0;
    scanf("%d", &n);
    while (i < n) {
        scanf("%d", &v);
        float vector[2000];
        for(int j = 0; j < v; j++){
            scanf("%f", &vector[j]);
        }
        float menor = vector[0];
        for(int j = 1; j < v; j++){
            if(vector[j] < menor){
                menor = vector[j];
            }
        }
        printf("%f\n", menor);
        i += 1;
    }
}