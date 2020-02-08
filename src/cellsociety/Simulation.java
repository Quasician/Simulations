package cellsociety;


import javafx.scene.paint.Color;

import java.util.HashMap;

public abstract class Simulation {

    private int simRows, simCols;
    private int simWidth, simHeight;
    private HashMap<String, String> colorMap;
    private HashMap<String, Double> params;


    private String[][] grid;

    public Simulation(int rows, int cols, int width, int height, HashMap<String, Double> params){
        this.simRows = rows;
        this.simCols = cols;
        this.simWidth = width;
        this.simHeight = height;
        this.params = params;
    }

    public abstract void initParams();
    public int getRows(){return simRows;}
    public int getCols(){return simCols;}
    public int getSimWidth(){return simWidth;}
    public int getSimHeight(){return simHeight;}
    public HashMap<String, Double> getParams(){return params;}
    public void createColorMap(HashMap<String, String> colorMap){
        this.colorMap = colorMap;
    }

    public void addToColorMap(String type, String color)
    {
        colorMap.putIfAbsent(type, color);
    }
    public void createGrid(String[][] grid)
    {
        this.grid = grid;
    }

    public String getCell(int x, int y)
    {
        return grid[x][y];
    }

    public void setCell(int x, int y, String value)
    {
        grid[x][y] = value;
    }


    public abstract void createGrid(int numRows, int numCols);

    public abstract void updateGrid();

    public abstract void setUpHashMap();


    public HashMap<String, String> getColorMap() {return colorMap;}


    public String[][] getGrid()
    {
        return grid;
    }

    public boolean inGrid(int rows, int cols)
    {
        if(rows>=0 && rows <simRows && cols>=0 && cols<simCols)
        {
            return true;
        }
        return false;
    }

    public String[] get4Neighbors(int x, int y, String[][] gridCopy)
    {
        String[] neighbors = new String[4];
        int count = 0;
        for(int i = x-1; i<=x+1;i++)
        {
            for(int j = y-1; j<=y+1;j++)
            {
                int temp1 = i - x + 1;
                int temp2 = j - y + 1;
                if((i - x + 1 + j - y + 1) % 2 == 0)
                {
                    continue;
                }
                else {
                    if(inGrid(i,j))
                    {
                        neighbors[count] = gridCopy[i][j];

                    } else {
                        neighbors[count] = "outOfBounds";
                    }
                    count++;
                }
            }
        }
        return neighbors;
    }

    public String[] get8Neighbors(int x, int y, String[][] gridCopy)
    {
        String[] neighbors = new String[8];
        int count = 0;
        for(int i = x-1; i<=x+1;i++)
        {
            for(int j = y-1; j<=y+1;j++)
            {
                if(i == x && j == y)
                {
                    continue;
                }
                else {
                    if(inGrid(i,j))
                    {
                        neighbors[count] = gridCopy[i][j];

                    } else {
                        neighbors[count] = "outOfBounds";
                    }
                    count++;
                }
            }
        }
        return neighbors;
    }


}
