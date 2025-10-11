import java.util.*;
/**
 * Esta es la clase de SilkRoad para el juego The Silk Road ... with Robots!
 * 
 * @author Dana Valeria Leal y Jose Luis Lancheros 
 * @version 11
 */
// hola gente esto es una prueba

/**
 * Representa una carretera lineal de longitud fija (0 hasta length-1) en la que se pueden
 * ubicar tiendas (stores) y robots. Cada tienda tiene un "tenge" inicial (stock). 
 * Los robots pueden moverse a lo largo de la carretera y tienen el profit de cada uno.
 * 
 * Reglas principales:
 * No se permite ubicar tienda y robot en la misma posición.
 * Al mover un robot, si termina exactamente sobre una tienda con stock lo recoge,
 * el profit se calcula la ganancia como (stockActual - distanciaRecorrida)
 * 
 * El campo ok indica si la última operación ejecutada fue exitosa.
 *
 */
public class SilkRoad {
    private Integer profit;
    private Integer length;
    private List<Robot> robots; // origin, actualLocation, profit
    private List<Store> stores; // location, originalTenge, actualTenge
    private boolean ok;
    private boolean visible;
    
    /** Crea una nueva instancia de la clase SilkRoad (constructor)
    *   @param length longitud de la carretera (length > 0)
    */
    public SilkRoad(Integer length) {
        if (length <= 0) {
            throw new IllegalArgumentException("El largo debe ser mayor que 0");
        }
        this.profit = 0;
        this.length = length;
        this.robots = new ArrayList<>();
        this.stores = new ArrayList<>();
        this.ok = true;
        this.visible = false;
    }

    public void makeVisible() {
        this.visible = true;
        draw();
    }

    public void makeInvisible() {
        this.visible = false;
    }

    public void draw() {
        for (Integer i = 0; i < length; i++) {
            System.out.print("[" + i + "] ");
            for (Robot robot : robots) {
                if (robot.getPosition() == i) {
                    System.out.print("ROBOT ");
                }
            }
            for (Store store : stores) {
                if (store.getLocation() == i) {
                    System.out.print("STORE");
                }
            }
            System.out.println();
        }
    }
    
    // ========================= PRIMER CICLO =========================
    
    /**
     * Intenta ubicar una tienda en una ubicacion dada y cantidad de tenge(recurso)
     *
     * @param location posición donde ubicar la tienda (0 =< location) & (location < length)
     * @param tenge capacidad/stock inicial de la tienda (tenge >= 0)
     * @return true si se ubicó, de lo contrario false
     *         ej. fuera de rango de la calle, ya hay tienda o robot en esa posición, o tenge < 0
     *         También actualiza el campo ok con el mismo valor retornado
     */
    public void placeStore(Integer location, Integer tenge) {

        boolean canPlace =  (location != null && tenge != null) && 
                            (!hasStoreInLocation(location)) &&
                            (!hasRobotInLocation(location)) &&
                            (0 <= location) && (location < length) &&
                            (tenge >= 0);

        if (canPlace) {
            stores.add(new Store(location, tenge));
            ok = true;
        } else {
            ok = false;
        }
    }
    
    /**
     * Indica si existe una tienda exactamente en location.
     * @param location posición a consultar
     * @return true si hay tienda en esa posición o false en caso contrario
     */
    public boolean hasStoreInLocation(Integer location) {
        for (Store store : stores) {
            if (store.getLocation() == location) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * Intenta ubicar un robot en location
     * @param location posición del robot (0 =< location y location < length)
     */
    public void placeRobot(Integer location) {

        boolean canPlace =  (location != null) &&
                            (!hasStoreInLocation(location)) &&
                            (!hasRobotInLocation(location)) &&
                            (0 <= location) && (location < length);

        if (canPlace) {
            robots.add(new Robot(location));
            ok = true;
        } else {
            ok = false;
        }
    }
    
    /**
     * Indica si existe un robot exactamente en location
     * @param location posición a consultar
     */
    public boolean hasRobotInLocation(Integer location) {
       for (Robot robot : robots) {
            if (robot.getPosition() == location) {
                return true;
            }
        }
        return false;
    }
    
    // ========================= SEGUNDO CICLO =========================
    /**
     * Elimina la tienda ubicada exactamente en location, si existe.
     * @param location posición de la tienda a eliminar
     */
    public void removeStore(Integer location) {
        boolean removed = false;
        for (Store store : stores) {
           if(store.getLocation() == location) {
                stores.remove(store);
                removed = true;
                break;
           }
        }
        ok = removed;
    }
        
     /**
     * Elimina el robot ubicado exactamente en location
     * @param location posición del robot a eliminar
     */
    public void removeRobot(Integer location) {
        boolean removed = false;
        for (Robot robot : robots) {
            if (robot.getPosition() == location ) {
                robots.remove(robot);
                removed = true;
                break;
            }
        }
        ok = removed;
    }
    
     /**
     * Mueve el robot desde su orogen location a una cantidad de metros dados
     * Si al finalizar el movimiento el robot cae exactamente en una tienda con stock entonces recoge todo dejando tenge en 0,
     * el profit se calcula como la ganancia (stockActual - distanciaRecorrida)
     *
     * @param location origen del robot a mover (identifica al robot)
     * @param meters desplazamiento (puede ser negativo)
     */
    public void moveRobot (Integer location, Integer meters) {
        boolean moved = false;
        for (Robot robot : robots) {
            if(robot.getPosition() == location) {
                Integer newPos = robot.getPosition() + meters;
                if (newPos < 0 || newPos >= length) {
                    throw new IllegalArgumentException("El robot no se puede salir del camino, ingrese un movimiento valido");
                }
                robot.moveBy(meters);
                moved = true;
                Integer distance = Math.abs(meters);
                for (Store store: stores) {
                    if (store.getLocation() == newPos && store.getCurrentTenge() > 0) {
                        Integer revenue = store.getCurrentTenge() - distance;
                        robot.setProfit(revenue);
                        profit += revenue;
                        store.setCurrentTenge(0);
                        break;
                    }
                }
                break;
            }
        }
        ok = moved;
    }
    
    // ========================= TERCER CICLO =========================
    /**
     * Restablece el stock actual de todas las tiendas a su tenge inicial
     */
    public void resupplyStores() {
        for (Store store : stores) {
            store.reset();
        }
        ok = true;
    }
    
    /**
     * Retorna cada robot a su posición de origen (posicionActual = origen)
     */
    public void returnRobots() {
        for(Robot robot : robots) {
            robot.reset();
        }
        ok = true;
    }
    
     /**
     * Restablece el sistema: reabastece tiendas, retorna robots y pone la ganancia en 0
     */
    public void reboot() {
        resupplyStores();
        returnRobots();
        profit = 0;
        ok = true;
    }
    
    /**
     * Finaliza la simulación: elimina todas las tiendas y robots, y pone la ganancia en 0
     */
    public void finish() {
        stores.clear();
        robots.clear();
        profit = 0;
        ok = true;
    }
    
    // ========================= CUARTO CICLO =========================
    /**
     * Devuelve la ganancia acumulada.
     */
    public Integer profit() {
        return profit;
    }
    
    /**
     * Devuelve las tiendas como matriz ordenada por ubicación
     * Cada fila es [ubicacion, tengeOriginal, tengeActual]
     * retorna una matriz de tamaño N x 3 o int[0][3] si no hay tiendas
     */
    public Integer[][] stores() {
        if(stores == null || stores.isEmpty()) {
            return new Integer[0][3];
        }
        List<Store> copy = new ArrayList<>(stores);
        copy.sort((a,b) -> Integer.compare(a.getLocation(), b.getLocation()));
        Integer[][] result = new Integer[copy.size()][3];
        for (int i = 0; i < copy.size(); i++) {
            Store s = copy.get(i);
            result[i][0] = s.getLocation();
            result[i][1] = s.getInitialTenge();
            result[i][2] = s.getCurrentTenge();
        }
        return result;
    }

    /**
     * Devuelve los robots como matriz ordenada por origen
     * Cada fila es [origen, posicionActual, profitTotal]
     * Retorna una matriz de tamaño N x 3 o int[0][3] si no hay robots
     */
    public Integer[][] robots() {
        if (robots == null || robots.isEmpty()) {
            return new Integer[0][3];
        }
        List<Robot> copy = new ArrayList<>(robots);
        copy.sort((a, b) -> Integer.compare(a.getOrigin(), b.getOrigin()));
        Integer[][] result = new Integer[copy.size()][3];
        for (int i = 0; i < copy.size(); i++) {
            Robot r = copy.get(i);
            result[i][0] = r.getOrigin();
            result[i][1] = r.getPosition();
            result[i][2] = r.getProfit();
        }
        return result;
    }

    /** Indica si la última operación ejecutada fue exitosa */
    public boolean ok() {
        return ok;
    }

    // ENTREGA 2
    //================================== CICLO 2 ======================================
    //CONSTRUCCION DE emptiedStores

    /**
     * Devuelve las tiendas ordenadas por localización con el número de veces que han sido desocupadas
     * Cada fila es [ubicacion, vecesDesocupada]
     * retorna una matriz de tamaño N x 2 o int[0][2] si no hay tiendas
     */
    public Integer[][] emptiedStores() {
        if(stores == null || stores.isEmpty()) {
            return new Integer[0][2];
        }
        List<Store> copy = new ArrayList<>(stores);
        copy.sort((a,b) -> Integer.compare(a.getLocation(), b.getLocation()));
        Integer[][] result = new Integer[copy.size()][2];
        for(Integer i = 0; i< copy.size(); i++) {
            Store s = copy.get(i);
            result[i][0] = s.getLocation();
            result[i][1] = s.getTimesEmptied();
        }
        return result;
    }

    //================================== CICLO 3 ======================================
    //CONSTRUCCION DE profitPerMove

    /**
     * Devuelve los robots ordenados por posición con sus ganancias por movimiento
     * Cada fila es [posicion, ganancia1, ganancia2, ...]
     * Retorna una matriz de tamaño N x (1 + movimientos) o int[0][1] si no hay robots
     */
    public Integer[][] profitPerMove() {
        if (robots == null || robots.isEmpty()) {
            return new Integer[0][1];
        }
        List<Robot> copy = new ArrayList<>(robots);
        copy.sort((a, b) -> Integer.compare(a.getOrigin(), b.getOrigin()));
        // Encontrar el máximo número de movimientos
        int maxMovements = 0;
        for (Robot r : copy) {
            maxMovements = Math.max(maxMovements, r.getProfitPastMovements().size());
        }
        Integer[][] result = new Integer[copy.size()][1 + maxMovements];
        for (int i = 0; i < copy.size(); i++) {
            Robot r = copy.get(i);
            result[i][0] = r.getOrigin();
            // Agregar las ganancias de cada movimiento
            List<Integer> movements = r.getProfitPastMovements();
            for (int j = 0; j < movements.size(); j++) {
                result[i][1 + j] = movements.get(j);
            }
            // Rellenar con null si tiene menos movimientos
            for (int j = movements.size(); j < maxMovements; j++) {
                result[i][1 + j] = null;
            }
        }
        return result;
    }


    //================================== CICLO 4 ======================================
    //CONSTRUCCION DE moveRobots

    public void moveRobots() {
        for (Robot robot : robots) {
            Integer startPosition = robot.getPosition();
            List<Store> availableStores = new ArrayList<>();

            // Filtrar tiendas con tenge > 0
            for (Store store : stores) {
                if (store.getCurrentTenge() > 0) {
                    availableStores.add(store);
                }
            }

            // Encontrar la mejor secuencia de movimientos
            PathResult bestPath = findBestPath(startPosition, availableStores, new HashSet<>(), 0, 0);

            // Ejecutar la mejor secuencia si existe
            if (bestPath != null && bestPath.moves.size() > 0) {
                Integer currentPosition = startPosition;
                for (Store store : bestPath.moves) {
                    Integer meters = store.getLocation() - currentPosition;
                    moveRobot(currentPosition, meters);
                    currentPosition = store.getLocation();
                }
            }
        }
    }

    // Clase auxiliar para almacenar resultados
    private static class PathResult {
        List<Store> moves;
        int totalProfit;

        PathResult(List<Store> moves, int totalProfit) {
            this.moves = moves;
            this.totalProfit = totalProfit;
        }
    }

    // Algoritmo recursivo para encontrar la mejor secuencia de tiendas
    private PathResult findBestPath(Integer currentPos, List<Store> remaining, Set<Store> visited, int accumulatedProfit, int depth) {
        PathResult best = new PathResult(new ArrayList<>(), accumulatedProfit);

        for (Store store : remaining) {
            if (!visited.contains(store)) {
                int distance = Math.abs(store.getLocation() - currentPos);
                int profit = store.getCurrentTenge() - distance;


                if (profit <= 0) continue; // ignorar si no es ganancia

                visited.add(store);
                PathResult path = findBestPath(store.getLocation(), remaining, visited, accumulatedProfit + profit, depth + 1);
                visited.remove(store);

                if (path.totalProfit > best.totalProfit) {
                    List<Store> newMoves = new ArrayList<>();
                    newMoves.add(store);
                    newMoves.addAll(path.moves);
                    best = new PathResult(newMoves, path.totalProfit);
                }
            }
        }

        return best;
    }
}