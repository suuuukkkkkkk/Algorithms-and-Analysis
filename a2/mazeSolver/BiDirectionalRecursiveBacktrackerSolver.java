package mazeSolver;

import java.util.Random;
import java.util.ArrayList;
import java.util.Stack;
import maze.Cell;
import maze.Maze;
import static maze.Maze.HEX;
import static maze.Maze.NUM_DIR;

/**
 * Implements the BiDirectional recursive backtracking maze solving algorithm.
 */
public class BiDirectionalRecursiveBacktrackerSolver implements MazeSolver {

    //cells explored count
    int explored = 0;
    //the front stack and the back stack
    Stack<Cell> front = new Stack<>();
    Stack<Cell> back = new Stack<>();
    //visted or unvisted
    boolean visit[][];

    //class member maze
    Maze mmaze;
    boolean solved = false;

    /**
     * Steps: 
     *      1. Push entrance and exit into stack 
     *      2. If tunnels, move 
     *      3. Move loop: 
     *          3.1 Both way do recursive functions 
     *              3.1.1 (Loop) no way then recursive back
     *              3.1.2 if avaliable way, randomly pick and return the valuse 
     *          3.2 Check values and move or complete 3.3 If tunnels, move
     */
    @Override
    public void solveMaze(Maze maze) {

        //pass value
        this.mmaze = maze;

        //init visit size. (hex has different coordinate)
        if (mmaze.type == HEX) {
            this.visit = new boolean[this.mmaze.sizeR][(this.mmaze.sizeC + 1) / 2 + this.mmaze.sizeC];
        } else {
            this.visit = new boolean[this.mmaze.sizeR][this.mmaze.sizeC];
        }

        //set all cell as unvisited
        for (int i = 0; i < mmaze.sizeR; i++) {
            for (int j = 0; j < mmaze.sizeC; j++) {
                visit[i][j] = false;
            }

        }

        //push entrance and exit to stack
        front.push(mmaze.entrance);
        back.push(mmaze.exit);

        //set entrance and exit as unvisited
        visit[front.peek().r][front.peek().c] = true;
        visit[back.peek().r][back.peek().c] = true;

        mmaze.drawFtPrt(front.peek());
        this.explored++;
        mmaze.drawFtPrt(back.peek());
        this.explored++;
        //tunnel move (if entrance is tunnel)
        if (front.peek().tunnelTo != null) {
            front.push(front.peek().tunnelTo);
            visit[front.peek().r][front.peek().c] = true;
            mmaze.drawFtPrt(front.peek());
            this.explored++;

        }

        if (back.peek().tunnelTo != null) {
            back.push(back.peek().tunnelTo);
            visit[back.peek().r][back.peek().c] = true;
            mmaze.drawFtPrt(back.peek());
            this.explored++;

        }

        //run from both entrance and exit
        OUTER:
        while (true) {

            //call the recursive function
            //values -2 solved, valuse -1 unsolved, value other is the direction that going to move
            int tempFront = frontBack();

            //check values
            switch (tempFront) {
                case -2:
                    solved = true;
                    break OUTER;
                case -1:
                    solved = false;
                    break OUTER;
                default:
                    //move
                    front.push(front.peek().neigh[tempFront]);
                    visit[front.peek().r][front.peek().c] = true;
                    mmaze.drawFtPrt(front.peek());
                    this.explored++;
                    break;
            }

            int tempBack = backBack();
            switch (tempBack) {
                case -2:
                    solved = true;
                    break OUTER;
                case -1:
                    solved = false;
                    break OUTER;
                default:
                    //move
                    back.push(back.peek().neigh[tempBack]);
                    visit[back.peek().r][back.peek().c] = true;
                    mmaze.drawFtPrt(back.peek());
                    this.explored++;
                    break;
            }

            if (front.peek().tunnelTo != null) {
                front.push(front.peek().tunnelTo);
                visit[front.peek().r][front.peek().c] = true;
                mmaze.drawFtPrt(front.peek());
                this.explored++;

            }

            if (back.peek().tunnelTo != null) {
                back.push(back.peek().tunnelTo);
                visit[back.peek().r][back.peek().c] = true;
                mmaze.drawFtPrt(back.peek());
                this.explored++;

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

    public int frontBack() {

        //check if no solution
        if (front.empty()) {
            return -1;
        }

        //use for count the avaliable ways (unvisited)
        ArrayList<Integer> temp;
        temp = new ArrayList<>();

        for (int i = 0; i < NUM_DIR; i++) {

            //check if is a way
            if (front.peek().neigh[i] != null && front.peek().wall[i] != null
                    && front.peek().wall[i].present == false) {

                //check if found solution
                if (back.search(front.peek().neigh[i]) != -1) {

                    return -2;

                }

                //check if is unvisted
                if (visit[front.peek().neigh[i].r][front.peek().neigh[i].c] == false) {
                    //add to array
                    temp.add(i);
                }

            }

        }

        //if array 0 , pop from stack and ,return this function
        if (temp.isEmpty()) {
            front.pop();
            return frontBack();
        } // if array not 0, random pick direction, return this dir
        else {
            Random random = new Random();
            int dir = (int) temp.get(random.nextInt(temp.size()));

            return dir;
        }

    }

    public int backBack() {

        //check if no solution
        if (back.empty()) {
            return -1;
        }

        //use for count the avaliable ways (unvisited)
        ArrayList<Integer> temp;
        temp = new ArrayList<>();

        for (int i = 0; i < NUM_DIR; i++) {

            //check if is a way
            if (back.peek().neigh[i] != null && back.peek().wall[i] != null
                    && back.peek().wall[i].present == false) {

                //check if found solution
                if (front.search(back.peek().neigh[i]) != -1) {

                    return -2;

                }

                //check if is unvisted
                if (visit[back.peek().neigh[i].r][back.peek().neigh[i].c] == false) {
                    //add to array
                    temp.add(i);
                }

            }

        }

        //if array 0 , pop from stack and ,return this function
        if (temp.isEmpty()) {
            back.pop();
            return backBack();
        } // if array not 0, random pick direction, return this dir
        else {
            Random random = new Random();
            int dir = (int) temp.get(random.nextInt(temp.size()));

            return dir;
        }

    }

} // end of class BiDirectionalRecursiveBackTrackerSolver
