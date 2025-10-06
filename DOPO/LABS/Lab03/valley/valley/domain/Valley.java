package domain;
import java.util.*;

/*No olviden adicionar la documentacion*/
public class Valley{
    static private int SIZE=25;
    private Unit[][] places;
    
    public Valley() {
        places=new Unit[SIZE][SIZE];
        for (int r=0;r<SIZE;r++){
            for (int c=0;c<SIZE;c++){
                places[r][c]=null;
            }
        }
        someUnits();
    }

    public int  getSize(){
        return SIZE;
    }

    public Unit getUnit(int r,int c){
        return places[r][c];
    }

    public void setUnit(int r, int c, Unit e){
        places[r][c]=e;
    }

    public void someUnits(){   

    }
    
    public int neighborsEquals(int r, int c){
        int num=0;
        if (inValley(r,c) && places[r][c]!=null){
            for(int dr=-1; dr<2;dr++){
                for (int dc=-1; dc<2;dc++){
                    if ((dr!=0 || dc!=0) && inValley(r+dr,c+dc) && 
                    (places[r+dr][c+dc]!=null) &&  (places[r][c].getClass()==places[r+dr][c+dc].getClass())) num++;
                }
            }
        }
        return num;
    }
   

    public boolean isEmpty(int r, int c){
        return (inValley(r,c) && places[r][c]==null);
    }    
        
    private boolean inValley(int r, int c){
        return ((0<=r) && (r<SIZE) && (0<=c) && (c<SIZE));
    }
    
   
    public void ticTac(){

    }

}
