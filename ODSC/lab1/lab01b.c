#include <stdio.h>
int main(void) {
	int T;
	float a, b, mayor;	// T es cuantos casos va haber, a,b = los numeros que ingresara el usuario y toca diferenciar

	scanf("%d", &T);	//leo T
	while (T > 0) {
		scanf("%f", &a);	// leo a
		scanf("%f", &b);	// leo b
		if (a > b) {		// si a es mayor que b
			mayor = a;	// mayor se vuelve a
		} else {			// de lo contrario
			mayor = b;	// mayor se vuekve b
		}
		printf("%f\n", mayor);	//imprime mayor
		T = T - 1;		//resto uno a T para que se vaya acercando a 0
	}
	return 0;
}
