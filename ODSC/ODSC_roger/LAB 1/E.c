#include <stdio.h>

int main() {
    int n, i = 0;
    float a, b, resultado;
    char o[2];
    scanf("%d", &n);
    while(i < n){
        scanf("%s", o);
        scanf("%f", &a);
        scanf("%f", &b);
        switch(o[0]){
            case '+':
                resultado = a + b;
                break;
            case '-':
                resultado = a - b;
                break;
            case '*':
                resultado = a * b;
                break;
            case '/':
                resultado = a / b;
                break;
        }
        printf("%f\n", resultado);
        i += 1;
    }
}

/*
#include <stdio.h>

int main() {
    int n, o, i = 0;
    float a, b, resultado;
    scanf("%d", &n);
    while(i < n){
        scanf("%lc", &o);
        scanf("%f", &a);
        scanf("%f", &b);
        switch(o){
            case '+':
                resultado = a + b;
                break;
            case '-':
                resultado = a - b;
                break;
            case '*':
                resultado = a * b;
                break;
            case '/':
                resultado = a / b;
                break;
        }
        printf("%f\n", resultado);
        i += 1;
    }
}
*/