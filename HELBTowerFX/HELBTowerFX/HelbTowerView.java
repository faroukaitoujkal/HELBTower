import javafx.scene.canvas.GraphicsContext;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Map;

public class HelbTowerView {

    private static final int WIDTH = 1050; // définis par essaie erreur pour que tout soit symétrique
    private static final int HEIGHT = 650;
    private static final int ROWS = 21;
    private static final int COLUMNS = 13;
    private static final int SQUARE_SIZE = WIDTH / ROWS;

    private Scene scene;
    
    private GraphicsContext gc;

    public Scene getScene() {
        return scene;
    }
    
    public int getRows() {
        return ROWS;
    }

    public int getColumns() {
        return COLUMNS;
    }

    public int getWidth() {
        return WIDTH;
    }

    public int getHeight() {
        return HEIGHT;
    }

    public HelbTowerView(Stage primaryStage) {
        primaryStage.setTitle("HELBTower");
        Group root = new Group();
        Canvas canvas = new Canvas(WIDTH, HEIGHT);
        root.getChildren().add(canvas);
        scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
        this.gc = canvas.getGraphicsContext2D();          
    }

    public void drawGameOver() {
        gc.setFill(Color.RED);
        gc.setFont(new Font("Digital-7", 70));
        gc.fillText("Game Over", WIDTH / 3.5, HEIGHT / 2);
    }

    public void drawBackGroundColorMorning(String color) {
        gc.setFill(Color.web(color));  
    }
    
    public void drawBackGroundColorDay(String color) {
        gc.setFill(Color.web(color));   
    }

    public void drawBackGroundColorEvening(String color) {
        gc.setFill(Color.web(color));  
    }

    public void drawBackGround(int i, int j) {
        gc.fillRect(i * SQUARE_SIZE, j * SQUARE_SIZE, SQUARE_SIZE, SQUARE_SIZE);
    }

    public void drawScore(int currentScore, int bestScore) {

        //gc.clearRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());

        gc.setFill(Color.WHITE);
        gc.setFont(new Font("Digital-7", 35));
        gc.fillText("Score: " + currentScore, 4, 35);

        gc.setFill(Color.WHITE);
        gc.setFont(new Font("Digital-7", 35));
        gc.fillText("Best Score: " + bestScore, 800, 35); // best score
    }


    // surcharge de la méthode drawCollectibleGameElements() pour generer aussi les portails

    public void drawCollectibleGameElements(CollectibleGameElement gameElemColl, Map<String, Image> imageMap) {
        gc.drawImage(imageMap.get(gameElemColl.getClass().getName()), 
        gameElemColl.getPosX() * SQUARE_SIZE, 
        gameElemColl.getPosY() * SQUARE_SIZE, 
                         SQUARE_SIZE, 
                         SQUARE_SIZE);
    }

    public void drawCollectibleGameElements(String potioon, CollectibleGameElement gameElemColl, Map<String, Image> imageMap) {
        gc.drawImage(imageMap.get(gameElemColl.getName()), 
        gameElemColl.getPosX() * SQUARE_SIZE, 
        gameElemColl.getPosY() * SQUARE_SIZE, 
                         SQUARE_SIZE, 
                         SQUARE_SIZE);
    }
    
    public void drawNotCollectibleGameElements(String portaill, NotCollectibleGameElement gameElemNotColl, Map<String, Image> imageMap) {
        gc.drawImage(imageMap.get(gameElemNotColl.getName()), 
        gameElemNotColl.getPosX() * SQUARE_SIZE, 
        gameElemNotColl.getPosY() * SQUARE_SIZE, 
                         SQUARE_SIZE, 
                         SQUARE_SIZE);
    }

    public void drawNotCollectibleGameElements(NotCollectibleGameElement gameElemNotColl, Map<String, Image> imageMap) {
        gc.drawImage(imageMap.get(gameElemNotColl.getClass().getName()), 
        gameElemNotColl.getPosX() * SQUARE_SIZE, 
        gameElemNotColl.getPosY() * SQUARE_SIZE, 
                         SQUARE_SIZE, 
                         SQUARE_SIZE);
    }



}