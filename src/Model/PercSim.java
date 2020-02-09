package Model;

import Model.Simulation;

import java.util.HashMap;

public class PercSim extends Simulation {

    private double percentEmpty;
    private double percentBlocked;

    public PercSim(int width, int height, HashMap<String,Double> params)
    {
        super((int)(params.get("grid_height")*10)/10,(int)(params.get("grid_width")*10/10), width,height, params);
        initParams();
        setUpHashMap();
    }

    public PercSim(int width, int height, HashMap<String,Double> params, Simulation sim)
    {
        super((int)(params.get("grid_height")*10)/10,(int)(params.get("grid_width")*10/10), width,height, params);
        initParams();
        setUpHashMap();
    }

    @Override
    public void initParams() {
        percentEmpty =getParams().get("percentEmpty");
        percentBlocked = getParams().get("percentBlocked");
        initAddToAgentNumberMap("empty");
        initAddToAgentNumberMap("blocked");
        initAddToAgentNumberMap("full");
    }

    public void createGrid(int numRows, int numCols) {
        createGrid(new String[numRows][numCols]);
        for(int i = 0; i<getRows();i++)
        {
            for(int j = 0; j<getCols();j++)
            {
                double choice = Math.random();
                if (choice<=percentEmpty) {
                    setCell(i,j,"empty");
                }
                else{
                    setCell(i,j,"blocked");
                }
            }
        }
        setCell(25,25,"full");
    }


    public void updateGrid() {
        resetAgentNumbers();
        String[][] gridCopy = new String[getRows()][getCols()];
        for(int i = 0; i<getRows();i++)
        {
            for(int j = 0; j<getCols();j++)
            {
                gridCopy[i][j] = getCell(i,j);
            }
        }
        for(int i = 0; i<getRows();i++)
        {
            for(int j = 0; j<getCols();j++)
            {
                updateCell(i,j,gridCopy);
            }
        }
        countAgentNumbers();
    }

    public void updateCell(int x, int y, String[][]gridCopy) {
        String[] neighbors = get8Neighbors(x,y, gridCopy);
        int sum = 0;
        for(int i = 0; i<neighbors.length;i++)
        {
            if(neighbors[i].equals("full"))
            {
                sum++;
            }
        }

        if(gridCopy[x][y].equals("empty") && sum > 0)
        {
            setCell(x,y,"full");
        }
    }
    public void setUpHashMap()
    {
        createColorMap(new HashMap<>());
        addToColorMap("full", "deepskyblue");
        addToColorMap("empty", "white");
        addToColorMap("blocked", "black");

    }
}
