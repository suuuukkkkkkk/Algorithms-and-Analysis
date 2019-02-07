package mazeGenerator;

import maze.Maze;
import maze.Cell;
import java.util.*;

public class GrowingTreeGenerator implements MazeGenerator {
    // Growing tree maze generator. As it is very general, here we implement as "usually pick the most recent cell, but occasionally pick a random cell"

    double threshold = 0.1;
    private Cell curCell;
    private Maze mMaze;
    private boolean norVisited[][];
    private HashSet<Cell> hexVisited;
    private Random rand = new Random(System.currentTimeMillis());

    /**
     * Use prim to generate a perfect maze
     *
     * Input: Maze, size, start and exit cell . Output: Perfect maze with carve
     * paths.
     *
     * 1. Pick a random starting cell and add it to set Z 
     * 2. select a cell b from Z. If cell b has unvisited neighbouring cells, randomly select a
     * neighbour, carve a path to it, and add the selected neighbour to set Z.
     * If b has no unvisited neighbours, remove it from Z
     * 3. Repeat step 2 until Z is empty.
     *
     * @param maze The reference of Maze object to generate
     */
    @Override
    public void generateMaze(Maze maze) {

        int randNeighbor;
        mMaze = maze;
        norVisited = new boolean[maze.sizeR][maze.sizeC];
        hexVisited = new HashSet<>();
        ArrayList<Cell> z = new ArrayList<>();

        int randRow = rand.nextInt(mMaze.sizeR);
        int randCol = rand.nextInt(mMaze.sizeC);
        int hexCol = rand.nextInt(mMaze.sizeC - 1) + ((randRow + 1) / 2);

        /*choose maze type, pick a random start cell, as visited*/
        if (mMaze.type == Maze.HEX) {
            curCell = mMaze.map[randRow][hexCol];
            hexVisited.add(curCell);
        } else {
            curCell = mMaze.map[randRow][randCol];
            norVisited[curCell.r][curCell.c] = true;
        }
        /* add start cell to set z*/
        z.add(curCell);

        /*until z is empty*/
        while (z.size() > 0) {

            /* pick a random cell from z*/
            curCell = z.get(rand.nextInt(z.size()));

            /*list for unVisited neighbors*/
            ArrayList<Integer> unvisitedNeighbors = new ArrayList<>();
            for (int i = 0; i < Maze.NUM_DIR; i++) {
                Cell curNeighbor = curCell.neigh[i];
                /* add unVisited neighbors to list*/
                if ((isIn(curNeighbor)) && (notVisited(curNeighbor))) {
                    unvisitedNeighbors.add(i);
                }
            }

            if (unvisitedNeighbors.size() > 0) {
                /*pick a random neighbor of current cell and carve a path */
                randNeighbor = unvisitedNeighbors.get(rand.nextInt(unvisitedNeighbors.size()));
                curCell.wall[randNeighbor].present = false;

                /*add this neighbor to z*/
                z.add(curCell.neigh[randNeighbor]);

                /* visited this neighbor*/
                if (mMaze.type == Maze.NORMAL) {
                    norVisited[curCell.neigh[randNeighbor].r][curCell.neigh[randNeighbor].c] = true;
                } else {
                    hexVisited.add(curCell.neigh[randNeighbor]);
                }

            } else {
                /*no unvisited neighbors*/
                z.remove(curCell);
            }

        }

    } // end of generateMaze()

    /**
     * Check whether cell (row, column) is in the maze.
     *
     * @param row coordinate
     * @param column coordinate
     * @return True if in the maze. Otherwise false.
     */
    private boolean isIn(int row, int column) {
        if (mMaze.type == Maze.HEX) {
            return row >= 0 && row < mMaze.sizeR && column >= (row + 1) / 2 && column < mMaze.sizeC + (row + 1) / 2;
        } else {
            return row >= 0 && row < mMaze.sizeR && column >= 0 && column < mMaze.sizeC;
        }
    }

    /**
     * Check whether the cell is in the maze.
     *
     * @param cell The cell being checked.
     * @return True if in the maze. Otherwise false.
     */
    private boolean isIn(Cell cell) {
        return cell != null && isIn(cell.r, cell.c);
    }

    /**
     * Check if a cell has not been visited
     *
     * @param cell the cell
     */
    private boolean notVisited(Cell cell) {
        if (mMaze.type == Maze.HEX) {
            return !hexVisited.contains(cell);
        } else {
            return !norVisited[cell.r][cell.c];
        }
    }
}   //end of class GrowingTreeGenerator

