package cellsociety;

import java.util.ArrayList;

public abstract class Organism {

    protected String name;
    protected int lives;
    protected int breedThresh;
    protected int energy;
    protected int x;
    protected int y;
    protected Organism[] neighbors;


    protected int breedThreshFish = 3;
    protected int breedThreshShark = 1;
    protected int defaultSharkEnergy = 3;
    protected int defaultFishEnergy = 2;

    public Organism(int x, int y, String name, int lives, int breedThresh, int energy) {
        this.name = name;
        this.lives = lives;
        this.breedThresh = breedThresh;
        this.energy = energy;
        this.x = x;
        this.y = y;
    }

    public Organism(String name) {
        this.name = name;
    }

    public String getName() {return name;}

    public void increaseLives() {lives++;}

    public void decreaseEnergy() {energy--;}


    public int getEnergy() {return energy;}
    public void setEnergy(int input) {energy = input;}
    public int getBreedThresh() {return breedThresh;}

    public abstract void move(int x, int y, Organism[][] grid, Organism[][] gridCopy, ArrayList<Organism> emptyCells);

    public Organism top(Organism[][] gridCopy, int x, int y) { return gridCopy[(x+gridCopy.length)%gridCopy.length][(y+1+gridCopy[0].length)%gridCopy[0].length];}
    public Organism bottom(Organism[][] gridCopy, int x, int y) { return gridCopy[(x+gridCopy.length)%gridCopy.length][(y-1+gridCopy[0].length)%gridCopy[0].length];}
    public Organism left(Organism[][] gridCopy, int x, int y) { return gridCopy[(x-1+gridCopy.length)%gridCopy.length][(y+gridCopy[0].length)%gridCopy[0].length];}
    public Organism right(Organism[][] gridCopy, int x, int y) { return gridCopy[(x+1+gridCopy.length)%gridCopy.length][(y+gridCopy[0].length)%gridCopy[0].length];}

    public Organism[] get4Neighbors(int x, int y, Organism[][] gridCopy) {
        Organism[] neighbors = new Organism[4];
        neighbors[0] = top(gridCopy, x, y);
        neighbors[1] = bottom(gridCopy, x, y);
        neighbors[2] = left(gridCopy, x, y);
        neighbors[3] = right(gridCopy, x, y);
        return neighbors;
    }

//    public Organism[] get4Neighbors(int x, int y, Organism[][] gridCopy) {
//        Organism[] neighbors = new Organism[4];
//        int count = 0;
//        for(int i = x-1; i<=x+1;i++)
//        {
//            for(int j = y-1; j<=y+1;j++)
//            {
//                if((i - x + 1 + j - y + 1) % 2 == 0)
//                {
//                    continue;
//                }
//                else {
//                    neighbors[count] = gridCopy[(i+gridCopy.length)%gridCopy.length][(j+gridCopy[0].length)%gridCopy[0].length];
//                    count++;
//                }
//            }
//        }
//        return neighbors;
//    }

    public void setName(String input) {this.name = input;}
    public int getLives() {return lives;}
    public void setLife(int input) {this.lives = input;}

}
