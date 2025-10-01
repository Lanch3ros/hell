#include <stdio.h>
#include <string.h>
#include <stdlib.h>

#define MAX 350005 // Tamaño máximo de los vectores utilizados para representar números grandes

// Convierte una cadena numérica en un vector de enteros (dígitos invertidos)
void str_a_vec(char *str, int *vec, int *len) {
    int i;
    *len = strlen(str); // Longitud de la cadena
    for (i = 0; i < *len; i++) {
        vec[i] = str[*len - 1 - i] - '0'; // Guarda los dígitos en orden inverso
    }
}

// Compara si el número 'a' es mayor o igual que 'b'
// Devuelve 1 si a >= b, 0 en caso contrario
int mayor_igual(int *a, int ta, int *b, int tb) {
    int i;
    if (ta != tb) return ta > tb; // Compara por longitud
    for (i = ta - 1; i >= 0; i--) {
        if (a[i] != b[i]) return a[i] > b[i]; // Compara dígito por dígito desde el más significativo
    }
    return 1; // Son iguales
}

// Realiza la resta a - b usando complemento a 9 (sin manejar préstamos directamente)
void restar_comp9(int *a, int ta, int *b, int tb, int *res, int *t_res) {
    int i, carry = 1;
    int max_len = ta > tb ? ta : tb;
    
    for (i = 0; i < max_len; i++) {
        int da = (i < ta) ? a[i] : 0; // Dígito de a
        int db = (i < tb) ? b[i] : 0; // Dígito de b
        int sum = da + (9 - db) + carry; // Suma a + (complemento a 9 de b) + carry
        res[i] = sum % 10; // Dígito resultante
        carry = sum / 10; // Nuevo carry
    }
    
    *t_res = max_len;
    // Elimina ceros a la izquierda (que están al final por estar invertido)
    while (*t_res > 1 && res[*t_res - 1] == 0) {
        (*t_res)--;
    }
}

// Desplaza un número a la izquierda (multiplicación por 10^shift)
void desplazar_izq(int *num, int t_num, int shift, int *res, int *t_res) {
    int i;
    for (i = 0; i < shift; i++) {
        res[i] = 0; // Rellena con ceros
    }
    for (i = 0; i < t_num; i++) {
        res[i + shift] = num[i]; // Copia los dígitos desplazados
    }
    *t_res = t_num + shift;
}

// Copia un vector en otro
void copiar(int *src, int *dest, int t) {
    int i;
    for (i = 0; i < t; i++) {
        dest[i] = src[i];
    }
}

// Imprime un número representado como vector (en orden correcto)
void imprimir(int *v, int t) {
    int i;
    for (i = t - 1; i >= 0; i--) {
        printf("%d", v[i]); // Imprime de atrás hacia adelante
    }
    printf("\n");
}

// Realiza la división de dos números grandes: dividendo / divisor
// Calcula también el residuo (división entera)
void dividir(int *dividendo, int t_dividendo, int *divisor, int t_divisor, 
             int *cociente, int *t_cociente, int *residuo, int *t_residuo) {
    
    int i, j;
    int temp[MAX], t_temp;
    int divisor_desp[MAX], t_divisor_desp;
    int resultado[MAX], t_resultado;
    int digito;
    
    // Inicializa el cociente con ceros
    for (i = 0; i < MAX; i++) {
        cociente[i] = 0;
    }
    *t_cociente = 1;
    
    // Copia el dividendo al residuo inicial
    copiar(dividendo, residuo, t_dividendo);
    *t_residuo = t_dividendo;
    
    // Si el dividendo < divisor, termina (cociente = 0, residuo = dividendo)
    if (!mayor_igual(dividendo, t_dividendo, divisor, t_divisor)) {
        return;
    }
    
    // Calcula cuántos dígitos se puede alinear el divisor con el dividendo
    int desplazamiento = t_dividendo - t_divisor;
    
    // Realiza la división larga desplazando el divisor
    while (desplazamiento >= 0) {
        // Desplaza el divisor para alinearlo con el residuo actual
        desplazar_izq(divisor, t_divisor, desplazamiento, divisor_desp, &t_divisor_desp);
        
        digito = 0;
        // Resta mientras el residuo sea mayor o igual al divisor desplazado
        while (mayor_igual(residuo, *t_residuo, divisor_desp, t_divisor_desp)) {
            restar_comp9(residuo, *t_residuo, divisor_desp, t_divisor_desp, temp, &t_temp);
            copiar(temp, residuo, t_temp);
            *t_residuo = t_temp;
            digito++;
            
            if (digito >= 9) break; // Evita exceso de iteraciones
        }
        
        // Asigna el dígito correspondiente al cociente
        if (digito > 0 || *t_cociente > 1) {
            cociente[desplazamiento] = digito;
            if (desplazamiento + 1 > *t_cociente) {
                *t_cociente = desplazamiento + 1;
            }
        }
        
        desplazamiento--; // Mueve hacia la derecha
    }
    
    // Asegura que el cociente tenga al menos un dígito
    if (*t_cociente == 0) {
        *t_cociente = 1;
    }
}

// Función principal
int main() {
    int n, i;
    char *txt_dividendo, *txt_divisor;
    int *dividendo, *divisor, *cociente, *residuo;
    int t_dividendo, t_divisor, t_cociente, t_residuo;
    
    // Reserva memoria dinámica para las cadenas y vectores
    txt_dividendo = (char *)malloc((MAX + 1) * sizeof(char));
    txt_divisor = (char *)malloc((MAX + 1) * sizeof(char));
    dividendo = (int *)malloc(MAX * sizeof(int));
    divisor = (int *)malloc(MAX * sizeof(int));
    cociente = (int *)malloc(MAX * sizeof(int));
    residuo = (int *)malloc(MAX * sizeof(int));
    
    // Verifica si hubo error de asignación
    if (!txt_dividendo || !txt_divisor || !dividendo || !divisor || !cociente || !residuo) {
        fprintf(stderr, "Error de asignación de memoria\n");
        return 1;
    }
    
    // Lee la cantidad de casos
    if (scanf("%d", &n) != 1) {
        free(txt_dividendo); free(txt_divisor);
        free(dividendo); free(divisor); free(cociente); free(residuo);
        return 1;
    }
    
    for (i = 0; i < n; i++) {
        // Lee dividendo y divisor como cadenas
        if (scanf("%s", txt_dividendo) != 1 || scanf("%s", txt_divisor) != 1) {
            free(txt_dividendo); free(txt_divisor);
            free(dividendo); free(divisor); free(cociente); free(residuo);
            return 1;
        }
        
        // Convierte las cadenas a vectores de enteros
        str_a_vec(txt_dividendo, dividendo, &t_dividendo);
        str_a_vec(txt_divisor, divisor, &t_divisor);
        
        // Realiza la división
        dividir(dividendo, t_dividendo, divisor, t_divisor, 
                cociente, &t_cociente, residuo, &t_residuo);
        
        // Imprime el cociente y el residuo
        imprimir(cociente, t_cociente);
        imprimir(residuo, t_residuo);
    }
    
    // Libera memoria
    free(txt_dividendo);
    free(txt_divisor);
    free(dividendo);
    free(divisor);
    free(cociente);
    free(residuo);
    
    return 0;
}
