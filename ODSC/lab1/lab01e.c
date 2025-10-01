#include <stdio.h>

float sum(float a, float b) {
	float operation_result = a + b;
        return operation_result;
}

float subtraction(float a, float b) {
	float operation_result = a - b;
	return operation_result;
}

float multiplication(float a, float b) {
	float operation_result = a * b;
        return operation_result;
}

float division(float a, float b) {
	float operation_result = a / b;
        return operation_result;
}

int main (void) {
	int T, i;
	scanf("%d", &T);
	for (i = 0; i < T; i++) {
		char operacion;
		float num_a, num_b, result;
		scanf(" %c", &operacion);
		scanf("%f", &num_a);
		scanf("%f", &num_b);
		if ('+' == operacion) {
			result = sum(num_a, num_b);
		} else if ('-' == operacion) {
                        result = subtraction(num_a, num_b);
                } else if ('/' == operacion) {
			if (num_b == 0.0f) {
				return 2;
			}
                        result = division(num_a, num_b);
                } else if ('*' == operacion) {
                        result = multiplication(num_a, num_b);
                } else {
			return  1;
		}
		printf("%.6f\n", result);
	}
	return 0;
}
