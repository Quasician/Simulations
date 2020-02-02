package cellsociety;

import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.HashMap;

public class PredPraySim extends Simulation {

    private double probSatisfy = .75;
    private ArrayList<Integer> x_empty_cells;
    private ArrayList<Integer> y_empty_cells;


    public PredPraySim(int rows, int cols, int width, int height)
    {
        super(rows, cols, width,height);
        createGrid(rows,cols);
        setUpHashMap();
    }

    public void createGrid(int numRows, int numCols) {
        grid = new String[numRows][numCols];
        for(int i = 0; i<simRows;i++)
        {
            for(int j = 0; j<simCols;j++)
            {
                ArrayList<String> list = new ArrayList<>();
                list.add("x");
                list.add("empty");
                list.add("o");
                String choice = list.get((int)Math.round(2 * Math.random()));
                grid[i][j] = choice;
            }
        }
        printCount();
    }
    public void updateGrid() {
        String[][] gridCopy = new String[simRows][simCols];
        for(int i = 0; i<simRows;i++)
        {
            for(int j = 0; j<simCols;j++)
            {
                gridCopy[i][j] = grid[i][j];
            }
        }
        x_empty_cells = new ArrayList<>();
        y_empty_cells = new ArrayList<>();
        generateEmptyCells(gridCopy);
        for(int i = 0; i<simRows;i++)
        {
            for(int j = 0; j<simCols;j++)
            {
                updateCell(i,j,gridCopy);
            }
        }
        printCount();
    }

    public void printCount() {
        int countRed = 0;
        int countBlue = 0;
        int countWhite = 0;
        for(int i = 0; i<simRows;i++)
        {
            for(int j = 0; j<simCols;j++)
            {
                if(grid[i][j] == "x") {
                    countRed++;
                }
                if(grid[i][j] == "o") {
                    countBlue++;
                }
                if(grid[i][j] == "empty") {
                    countWhite++;
                }
            }
        }
    }

    public void updateCell(int x, int y, String[][]gridCopy) {
        String[] neighbors = get8Neighbors(x,y, gridCopy);
        double satisfaction;
        int sameCount = 0;
        int total = 0;
        if (gridCopy[x][y] == "empty") {
            return;
        }
        for(int i = 0; i<neighbors.length;i++)
        {
            if(neighbors[i].equals(gridCopy[x][y]))
            {
                sameCount++;
            }
            if(!neighbors[i].equals("empty")) {
                total++;
            }
        }
        if (total == 0) {
            return;
        }
        satisfaction = ((double) sameCount) / total;
        if (satisfaction < probSatisfy && x_empty_cells.size() > 0) {
            ArrayList<Integer> temp = chooseAnEmptyCell(gridCopy);
            grid[temp.get(0)][temp.get(1)] = gridCopy[x][y];
            grid[x][y] = "empty";
        }

    }
    public void generateEmptyCells(String[][] gridCopy) {
        for (int i = 0; i < simRows; i++) {
            for (int j = 0; j < simCols; j++) {
                if (gridCopy[i][j].equals("empty")) {
                    x_empty_cells.add(i);
                    y_empty_cells.add(j);
                }
            }
        }
    }

    public ArrayList<Integer> chooseAnEmptyCell(String[][] gridCopy) {
        ArrayList<Integer> result = new ArrayList<>();
        int index = (int) (x_empty_cells.size() * Math.random());
        result.add(x_empty_cells.get(index));
        result.add(y_empty_cells.get(index));
        x_empty_cells.remove(index);
        y_empty_cells.remove(index);
        return result;
    }

    public void setUpHashMap()
    {
        colorMap = new HashMap<>();
        colorMap.putIfAbsent("empty", Color.WHITE);
        colorMap.putIfAbsent("x", Color.RED);
        colorMap.putIfAbsent("o", Color.BLUE);
    }
}