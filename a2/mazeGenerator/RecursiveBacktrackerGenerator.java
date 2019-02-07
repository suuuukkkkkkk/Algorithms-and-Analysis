package mazeGenerator;

import maze.Maze;
import maze.Cell;
import java.util.*;


public class RecursiveBacktrackerGenerator implements MazeGenerator {
	
    /*Name of class*/
    private Maze mMaze;
    private Cell curCell;
    private boolean norVisited[][];
    private HashSet<Cell> hexVisited;
    private ArrayList<Cell> toHex;
    private Random rand = new Random(System.currentTimeMillis());

    /**
     * Use recursive back tracker to generate a perfect maze
     *
     * Input: Maze, size, start and exit cells .
     * Output: Perfect maze with carve paths.
     *
     * 1. judge maze type and pick a random start cell.
	 * 2. Pick a random unvisited neighbouring cell and move to that neighbour. In the process, carve a
	 *	  path between the cells.
	 * 3. Continue this process until we reach a cell that has no unvisited neighbours. In that case,
	 *    backtrack one cell at a time, until we backtracked to a cell that has unvisited neighbours.
     *    Repeat step 2.
     * 4. When there are no more unvisited neighbours for all cells, then every cell would have been visited
     *    and we have generated a perfect maze.
     *
     * @param maze The reference of Maze object to generate
     */
    @Override
    public void generateMaze(Maze maze) {

        int numOfCellsUnvisited;
        boolean exsitUnvisitedNeighbors = true;
        int randNeighbor;
        mMaze = maze;
        norVisited = new boolean[maze.sizeR][maze.sizeC];
        hexVisited = new HashSet<>();
        Stack<Cell> preCell = new Stack<>();
        ArrayList<Cell> tunCell = new ArrayList<>();

        /*when the type of maze is normal*/
        if (maze.type == Maze.NORMAL) {

            /*calculate totoal cells of maze*/
            numOfCellsUnvisited = maze.sizeR * maze.sizeC;
            /*pick a random start cell*/
            randStartCell();

            /*visited start cell*/
            norVisited[curCell.r][curCell.c] = true;
            numOfCellsUnvisited--;

            /*make sure visited all cells*/
            while (numOfCellsUnvisited > 0) {

                /*make sure visited all neighours*/
                while (exsitUnvisitedNeighbors) {

                    /*list for unVisited neighbors*/
                    ArrayList<Integer> unVisitedNeighbors = new ArrayList<>();
                    /* add unVisited neighbors to list */
                    for (int i = 0; i < Maze.NUM_DIR; i++) {
                        Cell curNeighbor = curCell.neigh[i];
                        if ((isIn(curNeighbor)) && (notVisited(curNeighbor))) {
                            unVisitedNeighbors.add(i);
                        }
                    }

                    /*pick a random unVisited neighbors*/
                    if (unVisitedNeighbors.size() > 0) {
                        randNeighbor = unVisitedNeighbors.get(rand.nextInt(unVisitedNeighbors.size()));

                        /*carve a path and change current cell to neighbor*/
                        curCell.wall[randNeighbor].present = false;
                        preCell.add(curCell);
                        curCell = curCell.neigh[randNeighbor];

                        /*visited new current cell*/
                        norVisited[curCell.r][curCell.c] = true;
                        numOfCellsUnvisited--;
                    } else {
                        exsitUnvisitedNeighbors = false;
                    }
                }

                /*back to current cell */
                if (preCell.size() > 0) {
                    curCell = preCell.pop();
                }

                exsitUnvisitedNeighbors = true;
            }
        } /*when the type of maze is hex*/ else if (maze.type == Maze.HEX) {

            /* List all hex cells in maze*/
            toHex = new ArrayList<>();
            for (int i = 0; i < maze.sizeR; i++) {
                for (int j = (i + 1) / 2; j < maze.sizeC + (i + 1) / 2; j++) {
                    if (!isIn(i, j)) {
                        continue;
                    }
                    toHex.add(mMaze.map[i][j]);
                }
            }

            /*calculate totoal cells of maze*/
            numOfCellsUnvisited = toHex.size();

            /*pick a random start cell*/
            randStartCell();

            /*visited start cell*/
            hexVisited.add(curCell);
            numOfCellsUnvisited--;

            /*make sure visited all cells*/
            while (numOfCellsUnvisited > 0) {

                /*make sure visited all neighours*/
                while (exsitUnvisitedNeighbors) {

                    /*list for unVisited neighbors*/
                    ArrayList<Integer> unVisitedNeighbors = new ArrayList<>();
                    /* add unVisited neighbors to list */
                    for (int i = 0; i < Maze.NUM_DIR; i++) {
                        Cell curNeighbor = curCell.neigh[i];
                        if ((isIn(curNeighbor)) && (notVisited(curNeighbor))) {
                            unVisitedNeighbors.add(i);
                        }
                    }

                    /*pick a random unVisited neighbors*/
                    if (unVisitedNeighbors.size() > 0) {
                        randNeighbor = unVisitedNeighbors.get(rand.nextInt(unVisitedNeighbors.size()));

                        /*carve a path and change current cell to neighbor*/
                        curCell.wall[randNeighbor].present = false;
                        preCell.add(curCell);
                        curCell = curCell.neigh[randNeighbor];

                        /*visited new current cell*/
                        hexVisited.add(curCell);
                        numOfCellsUnvisited--;
                    } else {
                        exsitUnvisitedNeighbors = false;
                    }
                }

                /*back to current cell */
                if (preCell.size() > 0) {
                    curCell = preCell.pop();
                }

                exsitUnvisitedNeighbors = true;
            }
        } /*when the type of maze is tunnel*/ else if (maze.type == Maze.TUNNEL) {

            /*calculate totoal cells of maze*/
            numOfCellsUnvisited = maze.sizeR * maze.sizeC;

            /*pick a random start cell*/
            randStartCell();

            /*visited start cell*/
            norVisited[curCell.r][curCell.c] = true;
            numOfCellsUnvisited--;

            /*make sure visited all cells*/
            while (numOfCellsUnvisited > 0) {

                /*make sure visited all neighours*/
                while (exsitUnvisitedNeighbors) {

                    /*list for unVisited neighbors*/
                    ArrayList<Integer> unVisitedNeighbors = new ArrayList<>();
                    /* add unVisited neighbors to list */
                    for (int i = 0; i < Maze.NUM_DIR; i++) {
                        Cell curNeighbor = curCell.neigh[i];
                        if ((isIn(curNeighbor)) && (notVisited(curNeighbor)) && (!tunCell.contains(curNeighbor))) {
                            unVisitedNeighbors.add(i);
                        }
                    }

                    /*tunnel cell*/
                    if ((curCell.tunnelTo != null) && (!norVisited[curCell.tunnelTo.r][curCell.tunnelTo.c])) {

                        /*add another 6 neighbors*/
                        unVisitedNeighbors.add(6);
                    }

                    /*pick a random unvisited neighbor*/
                    if (unVisitedNeighbors.size() > 0) {
                        int randNeighborIndex = rand.nextInt(unVisitedNeighbors.size());
                        randNeighbor = unVisitedNeighbors.get(randNeighborIndex);

                        /*carve a path and change current cell to neighbor*/
                        if (randNeighbor != 6) {

                            /*tunnel cell*/
                            if (curCell.tunnelTo != null) {
                                tunCell.add(curCell.tunnelTo);
                            }
                            /*carve a path*/
                            curCell.wall[randNeighbor].present = false;
                            preCell.add(curCell);
                            curCell = curCell.neigh[randNeighbor];
                        } else {

                            /*through tunnel and dont need to carve a path*/
                            preCell.add(curCell);
                            curCell = curCell.tunnelTo;
                        }

                        norVisited[curCell.r][curCell.c] = true;
                        numOfCellsUnvisited--;
                    } else {
                        exsitUnvisitedNeighbors = false;
                    }
                }

                /*back to current cell*/
                if (preCell.size() > 0) {
                    curCell = preCell.pop();
                }

                exsitUnvisitedNeighbors = true;
            }
        }
    } // end of generateMaze()

    /* Random select a start cell*/
    private void randStartCell() {
        if (mMaze.type == Maze.HEX) {
            curCell = toHex.get(rand.nextInt(toHex.size()));
        } else {
            int randRow = rand.nextInt(mMaze.sizeR);
            int randCol = rand.nextInt(mMaze.sizeC);
            curCell = mMaze.map[randRow][randCol];
        }
    }

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
} // end of class RecursiveBacktrackerGenerator
