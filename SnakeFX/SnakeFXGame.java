import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

public class SnakeFXGame extends Application {

    private static final int WIDTH = 800;
    private static final int HEIGHT = WIDTH;
    private static final int ROWS = 20;
    private static final int COLUMNS = ROWS;
    private static final int SQUARE_SIZE = WIDTH / ROWS;

    private static final int RIGHT = 0;
    private static final int LEFT = 1;
    private static final int UP = 2;
    private static final int DOWN = 3;

    private GraphicsContext gc;
    private List<Point> snakeBody = new ArrayList();
    private Point snakeHead;
    private boolean gameOver;
    private int currentDirection;
    private int score = 0;
    
    private ArrayList<GameElement> gameElementList = new ArrayList<GameElement>();
    
    private int voidX = -2;
    private int voidY = -2;
    private Food food = new Food(voidX, voidY);
    
    private int coinCounter = 5;
    private ArrayList<Coin> coinList = new ArrayList<Coin>();
    
    private String foodClassDescriptionString = food.getClass().getName();
    private String coinClassDescriptionString = new Coin(voidX,voidY).getClass().getName();
    private Image foodImage = new Image(food.getPathToImage());
    private Image coinImage = new Image(new Coin(voidX,voidY).getPathToImage());
        
    private Map<String, Image> imageMap = new HashMap<>();
    
    public void addSnakeBodyPart(int posX, int posY){
        snakeBody.add(new Point(posX, posY));
    }
    
    public void addSnakeBodyPart(){
        snakeBody.add(new Point(-1, -1));
    }
    
    public int getScore(){
        return score;
    }
    
    public void setScore(int newScore){
        this.score = newScore;
    }
    
    public int getCoinCounter(){
        return coinCounter;
    }
    
    public void decreaseCoinCounter(){
        coinCounter--;
    }
    
    public void setGameOver(){
        gameOver = true;
    }
    
    public int getVoidX(){
        return voidX;
    }
    
    public int getVoidY(){
        return voidY;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Snake");
        Group root = new Group();
        Canvas canvas = new Canvas(WIDTH, HEIGHT);
        root.getChildren().add(canvas);
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
        gc = canvas.getGraphicsContext2D();

        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                KeyCode code = event.getCode();
                if (code == KeyCode.RIGHT || code == KeyCode.D) {
                    if (currentDirection != LEFT) {
                        currentDirection = RIGHT;
                    }
                } else if (code == KeyCode.LEFT || code == KeyCode.A) {
                    if (currentDirection != RIGHT) {
                        currentDirection = LEFT;
                    }
                } else if (code == KeyCode.UP || code == KeyCode.W) {
                    if (currentDirection != DOWN) {
                        currentDirection = UP;
                    }
                } else if (code == KeyCode.DOWN || code == KeyCode.S) {
                    if (currentDirection != UP) {
                        currentDirection = DOWN;
                    }
                }
            }
        });
        
        gameElementList.add(food);
        imageMap.put(foodClassDescriptionString, foodImage);
        imageMap.put(coinClassDescriptionString, coinImage);

        for (int i = 0; i < 3; i++) {
            addSnakeBodyPart(5, 2);
        }
        
        snakeHead = snakeBody.get(0);
        generateFood();
        
        for (int i = 0; i < coinCounter; i++) {
            generateCoin();
        }

        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(130), e -> run(gc)));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    private void run(GraphicsContext gc) {
        if (gameOver) {
            gc.setFill(Color.RED);
            gc.setFont(new Font("Digital-7", 70));
            gc.fillText("Game Over", WIDTH / 3.5, HEIGHT / 2);
            return;
        }
        drawBackground(gc);
        drawSnake(gc);
        drawScore();
        
        drawGameElements(gc);

        for (int i = snakeBody.size() - 1; i >= 1; i--) {
            snakeBody.get(i).x = snakeBody.get(i - 1).x;
            snakeBody.get(i).y = snakeBody.get(i - 1).y;
        }

        switch (currentDirection) {
            case RIGHT:
                moveRight();
                break;
            case LEFT:
                moveLeft();
                break;
            case UP:
                moveUp();
                break;
            case DOWN:
                moveDown();
                break;
        }

        gameOver();
        eatFood();
        eatCoin();
    }

    private void drawBackground(GraphicsContext gc) {
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLUMNS; j++) {
                if ((i + j) % 2 == 0) {
                    gc.setFill(Color.web("AAD751"));
                } else {
                    gc.setFill(Color.web("A2D149"));
                }
                gc.fillRect(i * SQUARE_SIZE, j * SQUARE_SIZE, SQUARE_SIZE, SQUARE_SIZE);
            }
        }
    }
    
    public void generateFood() {
        start:
        while (true) {
            int randomXPos = (int) (Math.random() * ROWS);
            int randomYPos = (int) (Math.random() * COLUMNS);

            for (Point snake : snakeBody) {
                if (snake.getX() == randomXPos 
                && snake.getY() == randomYPos) {
                    continue start;
                }
            }
            
            for (Coin otherCoin : coinList) {
                if (otherCoin.getPosX() == randomXPos 
                && otherCoin.getPosY() == randomYPos) {
                    continue start;
                }
            }
            
            if (food.getPosX() == randomXPos 
                && food.getPosY() == randomYPos) {
                    continue start;
            }
            
            food.setPosX(randomXPos);
            food.setPosY(randomYPos);
            food.setFoodImage();
            foodImage = new Image(food.getPathToImage());
            imageMap.put(foodClassDescriptionString, foodImage);
            break;
        }
    }
    
    public void generateCoin() {
        start:
        while (true) {
            int randomXPos = (int) (Math.random() * ROWS);
            int randomYPos = (int) (Math.random() * COLUMNS);

            for (Point snake : snakeBody) {
                if (snake.getX() == randomXPos 
                && snake.getY() == randomYPos) {
                    continue start;
                }
            }
            
            for (Coin otherCoin : coinList) {
                if (otherCoin.getPosX() == randomXPos 
                && otherCoin.getPosY() == randomYPos) {
                    continue start;
                }
            }
            
            if (food.getPosX() == randomXPos 
                && food.getPosY() == randomYPos) {
                    continue start;
            }
            
            Coin newCoin = new Coin(randomXPos, randomYPos);
            coinList.add(newCoin);
            gameElementList.add(newCoin);
            break;
        }
    }

    private void drawGameElements(GraphicsContext gc) {
        for (GameElement gameElem : gameElementList) {
            gc.drawImage(imageMap.get(gameElem.getClass().getName()), 
                         gameElem.getPosX() * SQUARE_SIZE, 
                         gameElem.getPosY() * SQUARE_SIZE, 
                         SQUARE_SIZE, 
                         SQUARE_SIZE);
        }
    }

    private void drawSnake(GraphicsContext gc) {
        gc.setFill(Color.web("4674E9"));
        gc.fillRoundRect(snakeHead.getX() * SQUARE_SIZE, snakeHead.getY() * SQUARE_SIZE, SQUARE_SIZE - 1, SQUARE_SIZE - 1, 35, 35);

        for (int i = 1; i < snakeBody.size(); i++) {
            gc.fillRoundRect(snakeBody.get(i).getX() * SQUARE_SIZE, snakeBody.get(i).getY() * SQUARE_SIZE, SQUARE_SIZE - 1,
                    SQUARE_SIZE - 1, 20, 20);
        }
    }

    private void moveRight() {
        snakeHead.x++;
    }

    private void moveLeft() {
        snakeHead.x--;
    }

    private void moveUp() {
        snakeHead.y--;
    }

    private void moveDown() {
        snakeHead.y++;
    }

    public void gameOver() {
        if (snakeHead.x < 0 || snakeHead.y < 0 || snakeHead.x * SQUARE_SIZE >= WIDTH || snakeHead.y * SQUARE_SIZE >= HEIGHT) {
            gameOver = true;
        }

        for (int i = 1; i < snakeBody.size(); i++) {
            if (snakeHead.x == snakeBody.get(i).getX() && snakeHead.getY() == snakeBody.get(i).getY()) {
                gameOver = true;
                break;
            }
        }
    }

    private void eatFood() {
        if (snakeHead.getX() == food.getPosX() 
        && snakeHead.getY() == food.getPosY()) {
            food.triggerAction(this);
        }
    }
    
    private void eatCoin() {
        for (Coin coin : coinList) {
            if (snakeHead.getX() == coin.getPosX() 
            && snakeHead.getY() == coin.getPosY()) {
                coin.triggerAction(this);
            }
        }
    }

    private void drawScore() {
        gc.setFill(Color.WHITE);
        gc.setFont(new Font("Digital-7", 35));
        gc.fillText("Score: " + score, 10, 35);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
