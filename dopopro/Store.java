/**
 * Represents a store on the road.
 * location: cell index on the road [0 hasta length-1]
 * initialTenge: initial stock
 * currentTenge: mutable stock
 */
public class Store {
    private Integer location;
    private Integer initialTenge;
    private Integer currentTenge;
    private Integer timesEmptied;
    
    public Store(Integer location, Integer tenge) {
        this.location = location;
        this.initialTenge = tenge;
        this.currentTenge = tenge;
        this.timesEmptied = 0;
    }

    public Integer getLocation() { return location; }

    public Integer getInitialTenge() { return initialTenge; }

    public Integer getCurrentTenge() { return currentTenge; }

    public Integer getTimesEmptied() { return timesEmptied; }

    public void setCurrentTenge(Integer value) {
        this.currentTenge = value;
        this.timesEmptied += 1;
    }

    /** Restores the current stock to the initial stock. */
    public void reset() { this.currentTenge = this.initialTenge; }
}