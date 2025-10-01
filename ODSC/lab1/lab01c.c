#include <stdio.h>
int main(void) {
	int T, i;		//T = cuantos vectores va haber, i = iterador para el bucle for
	scanf("%d", &T);	//Pido el numero de vectores
	for (i = 0; T > i; i++) {	//i itera hasta que iguale el tamano de T
		int size; 		//size = el tamano de los vetcores
		scanf("%d", &size);	//pido el tamano de cada vector
		int j;		// j = iterador para el bucle for
		float num, min;	// min = el minimo de cada vector, num = los numeros que entraran
		scanf("%f", &min);
		for (j = 1; size > j; j++) {	//j itera hasta que iguale a el tamano de size
			scanf("%f", &num);	// pido num
			if  (num < min) {	//comparo si min es menor que num, si si min sigue siendo min
				min = num;
			}
		}
		printf("%f\n", min);		//imprimo min
	}
	return 0;
}
