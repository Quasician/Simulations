package cellsociety;

import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.HashMap;

public class PredPraySim extends Simulation {

    private int breedThreshFish = 3;
    private int breedThreshShark = 2;
    private int defaultSharkEnergy = 1;
    private int defaultFishEnergy = 2;
    private Organism[][] organismGrid;

    public PredPraySim(int rows, int cols, int width, int height)
    {
        super(rows, cols, width,height);
        createGrid(rows,cols);
        setUpHashMap();
    }

    public void createGrid(int numRows, int numCols) {
        grid = new String[numRows][numCols];
        organismGrid = new Organism[numRows][numCols];
        for(int i = 0; i<simRows;i++) {
            for(int j = 0; j<simCols;j++) {
                ArrayList<String> list = new ArrayList<>();
                list.add("fish");
                list.add("kelp");
                list.add("shark");
                String choice = list.get((int)Math.round(2 * Math.random()));
                if (choice.equals("fish")) {
                    organismGrid[i][j] = new Fish(i,j,choice,0, breedThreshFish, defaultFishEnergy);
                    grid[i][j] = "fish";
                }
                else if (choice.equals("shark")) {
                    organismGrid[i][j] = new Shark(i,j,choice, 0, defaultSharkEnergy, breedThreshShark);
                    grid[i][j] = "shark";
                }
                else {
                    organismGrid[i][j] = new Kelp(choice, i,j);
                    grid[i][j] = "kelp";
                }
//                organismGrid[i][j] = new Kelp("kelp", i,j);
//                grid[i][j] = "kelp";
            }
        }
//        organismGrid[0][0] = new Fish(simRows/2,simCols/2,"fish",0, breedThreshFish, defaultFishEnergy);
//        grid[0][0] = "fish";
    }


    public void updateGrid() {
        String[][] gridCopy = new String[simRows][simCols];
        Organism[][] organismGridCopy = new Organism[simRows][simCols];
        for (int i = 0; i < simRows; i++) {
            for (int j = 0; j < simCols; j++) {
                organismGridCopy[i][j] = organismGrid[i][j];
                gridCopy[i][j] = grid[i][j];
            }
        }
//        x_empty_cells = new ArrayList<>();
//        y_empty_cells = new ArrayList<>();
//        generateEmptyCells(toString(organismGridCopy));
        for (int i = 0; i < simRows; i++) {
            for (int j = 0; j < simCols; j++) {
                moveOrganism(i, j, organismGridCopy);
                updateCell(i, j, grid);
            }
        }
        updateStringArray();
        System.out.println("finished round");
    }

    public void updateCell(int x, int y, String[][] grid) {
        organismGrid[x][y].increaseLives();
        if(organismGrid[x][y].getName().equals("fish"))
        {
            System.out.println("FISH at X: "+ x + "  Y: "+ y + "  Lives: "+organismGrid[x][y].getLives());
        }
        if(organismGrid[x][y].getName().equals("shark"))
        {
            System.out.println(organismGrid[x][y].getEnergy());
            organismGrid[x][y].decreaseEnergy();
            System.out.print(" DECREASED to organismGrid[x][y].getEnergy()\n");
            if(organismGrid[x][y].getEnergy()<=0)
            {
                //grid[x][y] = "kelp";
                organismGrid[x][y] = new Kelp("kelp",x,y);
            }
        }
    }

    public void moveOrganism(int x, int y, Organism[][] organismGridCopy) {
        organismGridCopy[x][y].move(x,y,organismGrid,organismGridCopy);
    }


    public void setUpHashMap() {
        colorMap = new HashMap<>();
        colorMap.putIfAbsent("fish", Color.BLUE);
        colorMap.putIfAbsent("shark", Color.GRAY);
        colorMap.putIfAbsent("kelp", Color.BLACK);
    }

    private void updateStringArray() {
        for (int i = 0; i < simRows; i++) {
            for (int j = 0; j < simCols; j++) {
                grid[i][j] = organismGrid[i][j].getName();
                if(grid[i][j].equals("fish"))
                {
                    System.out.println(organismGrid[i][j].getLives());
                }
            }
        }
    }
}
