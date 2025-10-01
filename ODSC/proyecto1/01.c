#include <stdio.h>
#include <string.h>
#include <stdlib.h>

#define LIMITE 350001
#define VERDADERO 1
#define FALSO 0

// Aplica el complemento a 9 de un número
void complemento9(char *num, size_t tam) {
    size_t k;
    for (k = 0; k < tam; k++) {
        num[k] = '9' - num[k] + '0';
    }
}

// Rellena con ceros a la izquierda
void rellenarCeros(char *cadena, size_t cantidad, size_t longitud) {
    char temp[LIMITE] = {0};
    size_t idx = 0, j;

    // ceros al inicio
    for (idx = 0; idx < cantidad; idx++) {
        temp[idx] = '0';
    }
    // copiar lo que había
    for (j = 0; j < longitud; j++) {
        temp[idx + j] = cadena[j];
    }
    memcpy(cadena, temp, longitud + cantidad);
}

// Resta usando complemento a 9
unsigned char restaComp9(char *a, size_t *tamA, char *b, size_t tamB) {
    if (*tamA < tamB) {
        rellenarCeros(a, tamB - *tamA, *tamA);
        *tamA = tamB;
    }
    if (tamB < *tamA) {
        rellenarCeros(b, *tamA - tamB, tamB);
        tamB = *tamA;
    }

    complemento9(b, tamB);

    char buff[LIMITE] = {0};
    int pos = 0;
    unsigned char acarreo = FALSO;

    int i, j;
    // suma dígito a dígito
    for (i = (int)(*tamA) - 1; i >= 0; i--) {
        int n1 = a[i] - '0';
        int n2 = b[i] - '0';
        int suma = n1 + n2 + acarreo;

        if (suma >= 10) {
            acarreo = VERDADERO;
            buff[pos++] = (suma % 10) + '0';
        } else {
            acarreo = FALSO;
            buff[pos++] = suma + '0';
        }
    }

    if (!acarreo) return VERDADERO;

    // copiar resultado parcial
    j = 0;
    for (i = pos - 1; i >= 0; i--) {
        a[j++] = buff[i];
    }
    *tamA = pos;

    // ajustar acarreo final
    for (i = pos - 1; i >= 0 && acarreo; i--) {
        if (a[i] == '9') {
            a[i] = '0';
        } else {
            a[i]++;
            acarreo = FALSO;
        }
    }

    return FALSO;
}

// División principal
void dividir(char *num1, char *num2) {
    int cociente = 0;
    size_t len1 = strlen(num1);
    size_t len2 = strlen(num2);

    char copia[LIMITE] = {0};
    memcpy(copia, num2, len2);

    while (1) {
        if (restaComp9(num1, &len1, num2, len2) == VERDADERO) {
            break;
        }
        memcpy(num2, copia, len2);
        cociente++;
    }

    printf("%d\n", cociente);

    int inicio = 0;
    while (num1[inicio] == '0') inicio++;
    if (inicio == len1) {
        printf("0\n");
    } else {
        printf("%s\n", num1 + inicio);
    }
}

int main() {
    int casos;
    scanf("%d", &casos);

    while (casos--) {
        char numA[LIMITE], numB[LIMITE];
        memset(numA, 0, sizeof(numA));
        memset(numB, 0, sizeof(numB));

        scanf("%s %s", numA, numB);
        dividir(numA, numB);
    }
    return 0;
}