//=============================== Ciclo 1 ===============================
// Construccion de la clase scannerUtils para poder leer la entrada del problema

public class Main {

    public static final int ROBOT = 1;
    public static final int STORE = 2;

    public static void main(String[] args) {
        System.out.print("Ingrese la longitud del camino: ");
        SilkRoad road = new SilkRoad(ScannerUtils.capturarEntero());
        Integer days = ScannerUtils.capturarEntero();
        for (Integer i = 0; i < days; i++) {
            Integer objeto = ScannerUtils.capturarEntero();
            switch (objeto) {
                case ROBOT -> {
                    Integer position = ScannerUtils.capturarEntero();
                    ScannerUtils.capturarTexto();
                    road.placeRobot(position);
                }
                case STORE -> {
                    Integer position = ScannerUtils.capturarEntero();
                    Integer tenges = ScannerUtils.capturarEntero();
                    ScannerUtils.capturarTexto();
                    road.placeStore(position, tenges);
                }
            }
        }
        road.makeVisible();
    }
}
