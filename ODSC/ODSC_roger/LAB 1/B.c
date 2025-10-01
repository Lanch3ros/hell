#include <stdio.h>
int main() {
    int n, i = 0;
    float a, b;
    scanf("%d", &n); 
    while (i < n) {
        scanf("%f", &a);
        scanf("%f", &b);
        if(a > b){
            printf("%f\n", a);
        } else{
            printf("%f\n", b);
        }
        i += 1;
    }
}