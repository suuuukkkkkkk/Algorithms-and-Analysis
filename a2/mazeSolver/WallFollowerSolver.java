package mazeSolver;

import maze.Cell;
import maze.Maze;
import static maze.Maze.HEX;
import static maze.Maze.NUM_DIR;
import static maze.Maze.deltaC;
import static maze.Maze.deltaR;
import static maze.Maze.oppoDir;

/**
 * Always follow the left most direction.
 * Steps:
 *      1. Draw entrance.
 *      2. Get enter direction and transfer to going to direction.
 *      3. Move Loop
 *          3.1 Tunnels move (Same tunnel can only be used after has no other way to go)
 *          3.2 Transfer straight going to direction to left most direction
 *          3.3 Check avaliable way.(loop)
 *              3.3.1 If is not the avaliable way, change to a right direction
 *              3.3.2 If it is, then move
 *          3.4 Check solved.
 *          
 */
public class WallFollowerSolver implements MazeSolver {
    
    public boolean solved = false;
    public int explored = 0;
    boolean visit[][];

    @Override
    public void solveMaze(Maze maze) {

        if (maze.type == HEX) {
            this.visit = new boolean[maze.sizeR][(maze.sizeC + 1) / 2 + maze.sizeC];
        } else {
            this.visit = new boolean[maze.sizeR][maze.sizeC];
        }
        
        for (int i = 0; i < maze.sizeR; i++) {
            for (int j = 0; j < maze.sizeC; j++) {
                visit[i][j] = false;
            }

        }
        //the direction the cell is moving to
        int goingTo = 0;
        Cell currCell = maze.entrance;
        maze.drawFtPrt(currCell);
        explored++;
        visit[currCell.r][currCell.c] = true;

        /*tunnels list. After the tunnel is used, this tunnel will only be 
        reactive until it has no other possible ways. The tunnel count is the 
        count of remaining possible ways.*/
        int tunnels[][] = new int[maze.sizeR][maze.sizeC];

        //init the list.
        for (int i = 0; i < maze.sizeR; i++) {
            for (int j = 0; j < maze.sizeC; j++) {

                tunnels[i][j] = - 1;

            }

        }

        //if i direction has no cell, but have wall and the wall is not presented.
        //that direction is the enter direction, and the opposite is the straight direction.
        for (int i = 0; i < NUM_DIR; i++) {

            if (currCell.neigh[i] == null && currCell.wall[i] != null
                    && currCell.wall[i].present == false) {
                goingTo = oppoDir[i];
                break;

            }

        }

        //everytime the cell move, then enter next step
        while (true) {

            //if reach the tunnel
            if (currCell.tunnelTo != null) {
                // if in the list (-1 means not in the list)
                if (tunnels[currCell.r][currCell.c] != - 1) {  //count - 1

                    //because reached, the count -1, when the count is 0, then can go through the tunnel
                    tunnels[currCell.r][currCell.c] = tunnels[currCell.r][currCell.c] - 1;
                    //if count == 0
                    if (tunnels[currCell.r][currCell.c] <= 0) {  //remove this tunnel from the list (set -1)
                        tunnels[currCell.r][currCell.c] = - 1;

                    }
                }

                //if not in the list
                if (tunnels[currCell.r][currCell.c] == -1) {
                    //go to the tunnel, add to the list
                    currCell = currCell.tunnelTo;
                    maze.drawFtPrt(currCell);
                    
                    if(visit[currCell.r][currCell.c] == false){
                    explored ++;
                    visit[currCell.r][currCell.c] = true;
                    }
                    
                    tunnels[currCell.r][currCell.c] = 0;
                    //check possible ways, add the count to the list
                    //the count in the list means possible ways
                    for (int i = 0; i < NUM_DIR; i++) {

                        if (currCell.neigh[i] != null && currCell.wall[i] != null
                                && currCell.wall[i].present == false) {

                            tunnels[currCell.r][currCell.c]++;

                        }

                    }
                    //list 0 means need to go through. 
                    if (tunnels[currCell.r][currCell.c] == 0) {

                        continue;

                    }

                }

            }

            //covert straight to the left most direction
            goingTo = goingTo + 2;

            if (goingTo > 5) {

                goingTo = goingTo - 5 - 1;

            }
            
            //do the loop everytime when direction change until have avaliable way
            while (true) {
                //see if have avaliable way
                if (currCell.neigh[goingTo] != null && currCell.wall[goingTo].present == false) {
                    //have avaliable way
                    break;

                }

                //not have a way, turn right one direction
                goingTo = goingTo - 1;

                if (goingTo < 0) {

                    goingTo = 5;

                }

            }

            //move cell
            currCell = maze.map[currCell.r + deltaR[goingTo]][currCell.c + deltaC[goingTo]];
            maze.drawFtPrt(currCell);
            
            if (visit[currCell.r][currCell.c] == false) {
                explored++;
                visit[currCell.r][currCell.c] = true;
            }
            
            //maze solved
            if (maze.exit == currCell) {
                
                this.solved = true;
                break;

            }

        }

    } // end of solveMaze()

    @Override
    public boolean isSolved() {
        // TODO Auto-generated method stub
        return this.solved;
    } // end if isSolved()

    @Override
    public int cellsExplored() {
        // TODO Auto-generated method stub
        return this.explored;
    } // end of cellsExplored()

} // end of class WallFollowerSolver
