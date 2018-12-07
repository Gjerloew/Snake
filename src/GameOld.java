import java.util.LinkedList;

public class GameOld {

    LinkedList<Position> snake = new LinkedList<>();
    int[][] grid;
    public enum Direction{UP,DOWN,RIGHT,LEFT}
    Direction dir = Direction.UP;

    /**
     *
     * @param width width of the board
     * @param height height of the board
     * @param start the start position of snake
     */
    public GameOld(int width, int height, Position start) {
        grid = new int[width][height];

        for(int i = 0; i<width; i++){
            for(int j = 0; j<height; j++){
                grid[i][j] = 0;
            }
        }

        grid[start.getX()][start.getY()] = 1;
        snake.add(start);
    }

    public int[][] getGrid(){
        return grid;
    }

    public void changeDirection(Direction dir){
        this.dir = dir;
    }

    public void step(){
        Position nextPos;
        Position head = snake.getFirst();
        switch(dir){
            case UP: nextPos = new Position(head.getX()-1, head.getY()); break;
            case DOWN: nextPos = new Position(head.getX()+1, head.getY()); break;
            case RIGHT: nextPos = new Position(head.getX(), head.getY()+1); break;
            default: nextPos = new Position(head.getX(), head.getY()-1); break; //if it's not up, down or right, it will go left
        }
        if(snake.contains(nextPos) || outOfBounds(nextPos)){
            gameOver();
        } else if(grid[nextPos.getX()][nextPos.getY()] == 2){
            eat(nextPos);
        } else {
            move(nextPos);
        }
    }

    private boolean outOfBounds(Position nextPos){
        if((nextPos.getX() < 0) || (nextPos.getX() > grid.length -1) || (nextPos.getY() < 0) || (nextPos.getY() > grid[0].length -1)){
            return true;
        } else {
            return false;
        }
    }

    private void eat(Position nextPos){
        snake.addFirst(nextPos);
    }

    private void move(Position nextPos){
        snake.addFirst(nextPos);
        snake.removeLast();
    }

    public void gameOver(){
        System.out.println("Game over");
    }

}
