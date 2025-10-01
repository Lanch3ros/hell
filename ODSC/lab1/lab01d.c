#include <stdio.h>
#define MAXN 2000

void read_vector(int v[], int n) {	//procedimiento para leer el vector
	int j;		//variable que  sera la que itera
	for (j = 0; j < n; j++) {	//bucle for que  itera el tamano del vector
		scanf("%d", &v[j]);	//pido cada numero y lo anado al vector
	}
}

int sum_vector(int v[], int n) {	//funcion que suma los numeros del vector
	int j;		//variable que  sera la que itera
	int suma = 0;		//declaro suma que empieza en 0
	for (j = 0; j < n; j++) {	//bucle for que itera el tamano del vector
		suma = suma + v[j];	//voy sumando cada elemneto del vector, con v[j] voy recorriendo el vector con su valor en cada casilla
	}
	return suma;	//retorno suma
}

int main(void) {	//funcion main
	int T, i;	// t = el valor de cuantos vectores entraran
	scanf("%d", &T);	//pido T
	int v[MAXN];		// declaro v y digo que su tamano es de 2000
	for (i = 0; i < T; i++) {	//bucle for que itera hasta  T
		int size;	//declaro size y su tipo
		scanf("%d", &size);	//pido el tamano del vector
		if (size > MAXN) {	//condicion (si size es mas grande que MAXN entonces se acaba el programa
			return 0;
		}
		read_vector (v, size);		//utilizo el procedimiento read_vetor
		int suma = sum_vector(v, size);	//utilizo la funcion sum_vector y el valor que retorna se guarda en suma
		printf("%d\n", suma);		//imprimo la suma del vector
	}
	return 0;
}
