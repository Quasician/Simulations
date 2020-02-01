package cellsociety;

import java.util.ArrayList;

public class FireSim extends Simulation{

    public FireSim(int row, int col, int width, int height)
    {
        super(row, col, width,height);
    }

    public void createGrid(int numRows, int numCols) {
        grid = new ArrayList<>();
        for(int i = 0; i < numRows; i++) {
            for(int j = 0; j < numCols; j++) {
                grid.get(i).add(new FireCell(i, j, numRows, numCols,State.Empty));
            }
        }

        //updating fireCell neighbors
        for(int i = 0; i < numRows; i++) {
            for(int j = 0; j < numCols; j++) {
                grid.get(i).get(j).get4Neighbors(grid);
            }
        }
    }

}
