package mazeGenerator;

import java.util.*;
import maze.Cell;
import maze.Maze;

public class ModifiedPrimsGenerator implements MazeGenerator{
	/*name of class*/
	
	private Maze mMaze;
	private Random rand = new Random(System.currentTimeMillis());
	
	
	/**
     * Use prim to generate a perfect maze
     *
     * Input: Maze, size, start and exit cell .
     * Output: Perfect maze with carve paths.
     *
     * 1. Pick a random start cell and add it to set Z. Put all neighbouring cells of starting cell
	 *	  into the frontier set F
	 * 2. Randomly select a cell c from the F set and remove it from F. Randomly select a cell b
	 *	  that is in Z and adjecent to the cell c. Carve a path between c and b.
	 * 3. Add cell c to the set Z. Add the neighbours of cell c to the frontier set F.
     * 4. Repeat step 2 until Z includes every cell in the maze. At the end of the process, we would have
     *    generated a perfect maze.
     *
     * @param maze The reference of Maze object to generate
     */
	   @Override
    public void generateMaze(Maze maze) {
        mMaze = maze;
        Cell fCell;
        Cell adjecentCell;
        Cell curCell;
        boolean visited;
        /* z set and f set*/
        LinkedList<Cell> z = new LinkedList<Cell>();
        LinkedList<Cell> f = new LinkedList<Cell>();

        /*pick a random start cell*/
        int randRow = rand.nextInt(mMaze.sizeR);
        int randCol = rand.nextInt(mMaze.sizeC);
        int hexCol = rand.nextInt(mMaze.sizeC - 1) + ((randRow + 1) / 2);

        /*choose maze type and put current cell to set z*/
        if (mMaze.type == Maze.HEX) {
            curCell = mMaze.map[randRow][hexCol];
        } else {
            curCell = mMaze.map[randRow][randCol];
        }

        z.add(curCell);

        /*put current cell's neighbours to f*/
        for (Cell neigh : curCell.neigh) {
            if (neigh == null) {
                continue;
            } else {
                f.add(neigh);
            }
        }

        /* until all the cells in z*/
        while (z.size() < mMaze.sizeC * mMaze.sizeR) {
            /*pick a random cell from f and remove it from f*/
            visited = false;
            int randCell = rand.nextInt(f.size());
            fCell = f.get(randCell);
            f.remove(fCell);

            while (!visited) {
                int dNeighbour = rand.nextInt(fCell.neigh.length);
                adjecentCell = fCell.neigh[dNeighbour];

                if (adjecentCell == null || !z.contains(adjecentCell)) {
                    continue;
                } else {
                    /*carve a path and add current frontier cell to z*/
                    fCell.wall[dNeighbour].present = false;
                    fCell.neigh[dNeighbour].wall[Maze.oppoDir[dNeighbour]].present = false;
                    z.add(fCell);

                    /*add current frontier cell's neighbours to f*/
                    for (Cell neigh : fCell.neigh) {
                        if (neigh != null && !z.contains(neigh) && !f.contains(neigh)) {
                            f.add(neigh);
                        }
                    }
                    visited = true;
                }
            }
        }
    } // end of generateMaze()

} // end of class ModifiedPrimsGenerator
