import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;


public class Controller {

    private Timeline loop;
    private Game game;

    int width = 25;
    int height = 25;
    double spacing = 1;
    double rectSize = 10;

    VBox rows = new VBox();

    @FXML
    private StackPane mainContainer;

    @FXML
    private HBox header;

    @FXML
    BorderPane content;

    public void initialize(){
        game = new Game(width, height, new Position(13,13));
        drawBoard(game.getGrid());

        mainContainer.getChildren().add(rows);
        mainContainer.setAlignment(Pos.CENTER);

        loop = new Timeline(new KeyFrame(Duration.millis(100), e->{
            if(game.gameOver()){
                loop.pause();
            }
            game.step();
            drawBoard(game.getGrid());
        }));
        loop.setCycleCount(Animation.INDEFINITE);
    }

    private void drawBoard(int[][] grid){
        rows.getChildren().clear();
        rows.setSpacing(spacing);
        rows.setAlignment(Pos.TOP_CENTER);

        for(int i = 0; i < grid.length; i++){
            HBox row = new HBox();
            row.setSpacing(spacing);
            row.setAlignment(Pos.CENTER);
            rows.getChildren().add(row);


            for(int j = 0; j < grid[0].length; j++){
                Rectangle tile = new Rectangle(rectSize, rectSize);

                Color tileColor;
                int colorKey = grid[i][j];
                switch(colorKey){
                    case 1: tileColor = Color.BLACK; break;
                    case 2: tileColor = Color.RED; break;
                    default : tileColor = Color.LIGHTGRAY; break;
                }

                tile.setFill(tileColor);
                row.getChildren().add(tile);
            }
        }
    }

    public void keyListener(KeyEvent event) {
        if (event.getCode().equals(KeyCode.UP)) {
            game.changeDirection(Game.Direction.UP);
        } else if (event.getCode().equals(KeyCode.DOWN)) {
            game.changeDirection(Game.Direction.DOWN);
        } else if (event.getCode().equals(KeyCode.RIGHT)) {
            game.changeDirection(Game.Direction.RIGHT);
        } else if (event.getCode().equals(KeyCode.LEFT)) {
            game.changeDirection(Game.Direction.LEFT);
        }
    }

    public void play(){
        loop.play();
    }

    public void pause(){
        loop.pause();
    }

    public void oneStep(){
        game.step();
        drawBoard(game.getGrid());
    }

    public void moveHead(){
        game.moveHead(new Position(10,10));
        game.step();
        drawBoard(game.getGrid());
    }

    public void reset() {
        game.reset();
        drawBoard(game.getGrid());
    }
}
