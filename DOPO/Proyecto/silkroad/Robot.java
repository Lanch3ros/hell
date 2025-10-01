import java.util.*;

/**
 * Represents a robot that moves along the road.
 * origin: initial position when created
 * position: current position
 */
public class Robot {
    private Integer origin;
    private Integer position;
    private Integer profit;
    private List<Integer> profitPastMovements;

    public Robot(Integer origin) {
        this.origin = origin;
        this.position = origin;
        this.profit = 0;
        this.profitPastMovements = new ArrayList<>();
    }

    public Integer getOrigin() { return origin; }

    public Integer getPosition() { return position; }
    
    public Integer getProfit() { return profit; }

    public List<Integer> getProfitPastMovements() { return profitPastMovements; }
    
    @Deprecated // Evita usarlo; conserva compatibilidad con código externo
    public void setPosition(int position) { this.position = position; }

    /** Move by a relative number of meters (can be negative). */
    public void moveBy(Integer meters) { this.position += meters; }
    
    public void setProfit(Integer profit) {
        this.profit += profit;
        this.profitPastMovements.add(profit);
    }

    /** Return to the origin position. */
    public void reset() { this.position = this.origin; }
}