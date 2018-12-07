import java.util.LinkedList;
import java.util.Random;

public class Game {

    public enum Direction{UP,DOWN,RIGHT,LEFT}

    private int height;
    private int width;
    private int[][] grid;

    private boolean gameOver = false;

    private Direction dir = Direction.UP;
    private Direction nextDir = dir;

    Position start;

    private Random rng = new Random();

    private LinkedList<Position> snake = new LinkedList<>();

    /**
     * @param width width of the board
     * @param height height of the board
     * @param start the start position of snake
     */
    public Game(int width, int height, Position start) {
        this.width = width;
        this.height = height;
        this.start = start;
        grid = new int[width][height];

        for(int i = 0; i<width; i++){
            for(int j = 0; j<height; j++){
                grid[i][j] = 0;
            }
        }

        grid[start.getX()][start.getY()] = 1;
        snake.add(start);
        placeFood();
    }

    public int[][] getGrid(){
        return grid;
    }

    public void changeDirection(Direction dir){
        nextDir = dir;
    }

    public void step(){
        Position nextPos;
        Position head = snake.getFirst();

        if((dir == Direction.UP) && (nextDir != Direction.DOWN)){
            dir = nextDir;
        }else if((dir == Direction.DOWN) && (nextDir != Direction.UP)){
            dir = nextDir;
        }else if((dir == Direction.RIGHT) && (nextDir != Direction.LEFT)){
            dir = nextDir;
        }else if((dir == Direction.LEFT) && (nextDir != Direction.RIGHT)){
            dir = nextDir;
        }

        switch(dir){
            case UP: nextPos = new Position(head.getX()-1, head.getY()); break;
            case DOWN: nextPos = new Position(head.getX()+1, head.getY()); break;
            case RIGHT: nextPos = new Position(head.getX(), head.getY()+1); break;
            default: nextPos = new Position(head.getX(), head.getY()-1); break; //if it's not up, down or right, it will go left
        }

        if(outOfBounds(nextPos)){
            gameOver = true;
        } else if(grid[nextPos.getX()][nextPos.getY()] == 1){
            gameOver = true;
        } else if(grid[nextPos.getX()][nextPos.getY()] == 2){
            eat(nextPos);
            placeFood();
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
        grid[nextPos.getX()][nextPos.getY()] = 1;
    }

    private void move(Position nextPos){
        snake.addFirst(nextPos);
        grid[nextPos.getX()][nextPos.getY()] = 1;
        grid[snake.getLast().getX()][snake.getLast().getY()] = 0;
        snake.removeLast();
    }

    public void moveHead(Position pos){
        snake.addFirst(pos);
        grid[pos.getX()][pos.getY()] = 1;
        grid[snake.getLast().getX()][snake.getLast().getY()] = 0;
        snake.removeLast();

        /*
        grid[pos.getX()][pos.getY()] = 0;
        snake.set(0, pos);
        */
    }

    public boolean gameOver(){
        return gameOver;
    }

    public void placeFood(){
        int rHeight = rng.nextInt(height-1);
        int rWidth = rng.nextInt(width-1);
        if(grid[rHeight][rWidth] == 0){
            grid[rHeight][rWidth] = 2;
        } else {
            placeFood();
        }
    }

    public void reset(){
        snake.clear();

        dir = Direction.UP;
        nextDir = dir;

        for(int i = 0; i<width; i++){
            for(int j = 0; j<height; j++){
                grid[i][j] = 0;
            }
        }

        grid[start.getX()][start.getY()] = 1;
        snake.add(start);

        placeFood();

        gameOver = false;
    }


}
