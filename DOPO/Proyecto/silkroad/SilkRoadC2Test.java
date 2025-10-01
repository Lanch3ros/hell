

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * The test class SilkRoadTest.
 *
 * @author  (your name)
 * @version (a version number or a date)
 */
public class SilkRoadC2Test
{
    /**
     * Default constructor for test class SilkRoadTest
     */
    public SilkRoadC2Test()
    {
    }

    /**
     * Sets up the test fixture.
     *
     * Called before every test case method.
     */
    @BeforeEach
    public void setUp()
    {
    }

    @Test
    public void accordingLLShouldMoveRobotsToMaximizeProfit() {
        SilkRoad road = new SilkRoad(50);
        road.placeRobot(20);
        road.placeStore(15, 15);
        road.placeStore(40, 50);
        road.moveRobots();
        Integer[][] robots = road.robots();
        assertEquals(35, robots[0][2]);
    }

    @Test
    public void accordingLLShouldNotMoveRobotIfAllProfitsAreNegative() {
        SilkRoad road = new SilkRoad(20);
        road.placeRobot(10);
        road.placeStore(0, 5); // distancia 10, profit = -5
        road.moveRobots();
        assertEquals(10, road.robots()[0][1]);
        assertEquals(0, road.robots()[0][2]);
    }

    @Test
    public void accordingLLShouldEmptyStoresWhenVisited() {
        SilkRoad road = new SilkRoad(30);
        road.placeRobot(10);
        road.placeStore(12, 20);
        road.moveRobots();
        Integer[][] stores = road.stores();
        assertEquals(0, stores[0][2]);
    }

    @Test
    public void accordingLLShouldNotVisitSameStoreTwice() {
        SilkRoad road = new SilkRoad(40);
        road.placeRobot(20);
        road.placeStore(25, 20);
        road.moveRobots();
        road.moveRobots(); // no debe moverse de nuevo
        assertEquals(25, road.robots()[0][1]);
    }

    @Test
    public void accordingLLShouldAccumulateProfitCorrectly() {
        SilkRoad road = new SilkRoad(50);
        road.placeRobot(10);
        road.placeStore(12, 10); // profit = 8
        road.placeStore(18, 20); // profit = 10
        road.moveRobots();
        assertEquals(18, road.robots()[0][1]);
        assertEquals(22, road.robots()[0][2]);
    }

    @Test
    public void accordingLLShouldHandleMultipleRobotsIndependently() {
        SilkRoad road = new SilkRoad(50);
        road.placeRobot(5);
        road.placeRobot(40);
        road.placeStore(10, 15);
        road.placeStore(45, 20);
        road.moveRobots();
        Integer[][] robots = road.robots();
        assertEquals(10, robots[0][1]); // Robot 1
        assertEquals(45, robots[1][1]); // Robot 2
    }

    @Test
    public void accordingLLShouldRespectRebootResets() {
        SilkRoad road = new SilkRoad(30);
        road.placeRobot(10);
        road.placeStore(15, 10);
        road.moveRobots();
        road.reboot();
        assertEquals(0, road.profit());
        assertEquals(10, road.robots()[0][1]); // back to origin
        assertEquals(10, road.stores()[0][2]); // tenge restored
    }

    @Test
    public void accordingLLShouldTrackEmptiedStoreCounts() {
        SilkRoad road = new SilkRoad(20);
        road.placeRobot(10);
        road.placeStore(15, 20);
        road.moveRobots(); // 1
        road.reboot();
        road.moveRobots(); // 2
        assertEquals(2, road.emptiedStores()[0][1]);
    }

    @Test
    public void accordingLLShouldHandleNoRobotsGracefully() {
        SilkRoad road = new SilkRoad(10);
        road.placeStore(5, 10);
        road.moveRobots();
        assertEquals(10, road.stores()[0][2]); // no cambio
    }

    @Test
    public void accordingLLShouldHandleNoStoresGracefully() {
        SilkRoad road = new SilkRoad(10);
        road.placeRobot(5);
        road.moveRobots();
        assertEquals(5, road.robots()[0][1]); // sin mover
    }

    @Test
    public void accordingLLShouldFinishAndClearAllEntities() {
        SilkRoad road = new SilkRoad(10);
        road.placeRobot(3);
        road.placeStore(8, 10);
        road.finish();
        assertEquals(0, road.robots().length);
        assertEquals(0, road.stores().length);
    }

    @Test
    public void accordingLLShouldNotPlaceRobotOnStore() {
        SilkRoad road = new SilkRoad(10);
        road.placeStore(4, 5);
        road.placeRobot(4);
        assertFalse(road.ok());
    }

    @Test
    public void accordingLLShouldNotPlaceStoreOnRobot() {
        SilkRoad road = new SilkRoad(10);
        road.placeRobot(6);
        road.placeStore(6, 10);
        assertFalse(road.ok());
    }

    @Test
    public void accordingLLShouldRecordProfitPerMove() {
        SilkRoad road = new SilkRoad(20);
        road.placeRobot(5);
        road.placeStore(8, 10); // profit = 7
        road.moveRobots();
        Integer[][] profitMoves = road.profitPerMove();
        assertEquals(1, profitMoves.length);
        assertEquals(7, profitMoves[0][1]);
    }

    @Test
    public void accordingLLShouldReturnEmptyMatrixesWhenEmpty() {
        SilkRoad road = new SilkRoad(10);
        assertEquals(0, road.robots().length);
        assertEquals(0, road.stores().length);
        assertEquals(0, road.emptiedStores().length);
        assertEquals(0, road.profitPerMove().length);
    }
    
    /**
     * Tears down the test fixture.
     *
     * Called after every test case method.
     */
    @AfterEach
    public void tearDown()
    {
    }
}