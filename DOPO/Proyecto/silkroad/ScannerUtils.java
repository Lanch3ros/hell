import java.util.Scanner;

//=============================== Ciclo 1 ===============================
// Construccion de la clase scannerUtils para poder leer la entrada del problema

public class ScannerUtils {
    public static final Scanner SCANNER = new Scanner(System.in);

    public static String capturarTexto () {
        return SCANNER.nextLine();
    }

    public static int capturarEntero () {
        return SCANNER.nextInt();
    }

    public static double capturarDecimal () {
        return SCANNER.nextDouble();
    }
}
