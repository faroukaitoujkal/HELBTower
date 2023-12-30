import javafx.animation.Animation;
import java.util.Timer;
import java.util.TimerTask;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.util.Random;
import java.io.PrintWriter;
import java.io.FileNotFoundException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

public class HELBTowerFXGame extends Application {

    protected HelbTowerView view; // on crée la vue

    private static final int RIGHT = 0;
    private static final int LEFT = 1;
    private static final int UP = 2;
    private static final int DOWN = 3;

    private ArrayList<String> potionNameList = new ArrayList<String>();
    private ArrayList<Portal> portalList = new ArrayList<Portal>();
    private ArrayList<Potion> potionList = new ArrayList<Potion>();
    private ArrayList<Wall> wallList = new ArrayList<Wall>();
    private ArrayList<Wall> towerList = new ArrayList<Wall>();
    private ArrayList<Coin> coinList = new ArrayList<Coin>();
    private ArrayList<MagicCape> magicCapeList = new ArrayList<MagicCape>();
    private ArrayList<CollectibleGameElement> collectibleGameElementList = new ArrayList<CollectibleGameElement>();
    private ArrayList<NotCollectibleGameElement> notCollectibleGameElementList = new ArrayList<NotCollectibleGameElement>();

    private boolean gameOver;
    private boolean isSpeedBoosted = false;
    private boolean magicCapeActivated = false;
    private boolean canRedGuardianMove = true;
    private boolean canBlueGuardianMove = true;
    private boolean canPurpleGuardianMove = true;
    private boolean canOrangeGuardianMove = true;
        
    private static final String BEST_SCORE_FILE = "bestScore.txt";
    private int morning = 0;
    private int day = 1;
    private int night = 2;
    private int currentScore = 0;
    private int bestScore = 0;
    private int voidX = -2;
    private int voidY = -2;
    private int currentPeriod = day;
    private int currentSpeedBoost = 0;
    private int coinCounter = 10; // le nombre de piece sur la carte pour tout remplir IL FAUT 241
    private int collectedCoins = 0;

    // private double percentageCollected = (collectedCoins * 100.0) / coinCounter;

    private Hero hero = new Hero(10,2);
    private Wall wall = new Wall(5,5);
    private Guardian orangeGuardian = new OrangeGuardian(3,2);
    private Guardian purpleGuardian = new PurpleGuardian(17,10);
    private Guardian blueGuardian = new BlueGuardian(17,2);
    private Guardian redGuardian = new RedGuardian(3,10);
    
    private String coinClassDescriptionString = new Coin(voidX,voidY).getClass().getName();
    private String magicCapeClassDescriptionString = new MagicCape(voidX,voidY).getClass().getName();
    private String heroClassDescriptionString = hero.getClass().getName();
    private String wallClassDescriptionString = wall.getClass().getName();
    private String orangeGuardianClassDescriptionString = orangeGuardian.getClass().getName();
    private String purpleGuardianClassDescriptionString = purpleGuardian.getClass().getName();
    private String blueGuardianClassDescriptionString = blueGuardian.getClass().getName();
    private String redGuardianClassDescriptionString = redGuardian.getClass().getName();
    
    private Image orangeGuardianImage = new Image(orangeGuardian.getPathToImage());
    private Image purpleGuardianImage = new Image(purpleGuardian.getPathToImage());
    private Image blueGuardianImage = new Image(blueGuardian.getPathToImage());
    private Image redGuardianImage = new Image(redGuardian.getPathToImage());
    private Image wallImage = new Image(wall.getPathToImage());
    private Image heroImage = new Image(hero.getPathToImage());
    private Image magicCapeImage = new Image(new MagicCape(voidX,voidY).getPathToImage());
    private Image coinImage = new Image(new Coin(voidX,voidY).getPathToImage());
        
    private Map<String, Image> imageMap = new HashMap<>();
    private Wall targetTower;
    
    public void setScore(int newScore){
        this.currentScore = newScore;
    }
    
    public void setGameOver(){
        gameOver = true;
    }

    public void decreaseCoinCounter(){
        currentScore++;
        collectedCoins++;
        coinCounter--;
        
    }

    public int getCollectedCoins() {
        return collectedCoins;
    }

    /*public Wall getTargetTower() {
        return targetTower;
    }

    public void setTargetTower(Wall targetTower) {
        this.targetTower = targetTower;
    }*/

    public Wall getRandomTower() {
        Random random = new Random();
        int index = random.nextInt(towerList.size());
        return towerList.get(index);
    }

    public boolean getCanRedGuardianMove() {
        return canRedGuardianMove;
    }

    public boolean getCanBlueGuardianMove() {
        return canBlueGuardianMove;
    }

    public boolean getCanPurpleGuardianMove() {
        return canPurpleGuardianMove;
    }
    
    public boolean getCanOrangeGuardianMove() {
        return canOrangeGuardianMove;
    }

    public int getHeroSpeed() {
        return hero.getSpeed() + currentSpeedBoost;
    }

     public int getCoinCounter(){
        return coinCounter;
    } 

    public int getRows() {
        return view.getRows();
    }

    public int getColumns() {
        return view.getColumns();
    }

    public int getWidth() {
        return view.getWidth();
    }

    public int getHeight() {
        return view.getHeight();
    }
    
    public Hero getHero() {
        return hero;
    }

    public int getScore(){
        return currentScore;
    }
    
    public int getVoidX(){
        return voidX;
    }
    
    public int getVoidY(){
        return voidY;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        view = new HelbTowerView(primaryStage);
        
        view.getScene().setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                KeyCode code = event.getCode();
                if (code == KeyCode.RIGHT || code == KeyCode.D) {
                    moveRight();
                } else if (code == KeyCode.LEFT || code == KeyCode.Q) {   
                    moveLeft();                
                } else if (code == KeyCode.UP || code == KeyCode.Z) {       
                    moveUp();           
                } else if (code == KeyCode.DOWN || code == KeyCode.A) {
                    moveDown();
                } else if (code == KeyCode.K) {
                    resetGame();
                } else if (code == KeyCode.Y) { 
                    System.out.println("mode matin activé");
                    drawBackGround(morning); // periode du matin 
                } else if (code == KeyCode.U) { 
                    drawBackGround(day); // periode du jour
                } else if (code == KeyCode.I) { 
                    drawBackGround(night); // periode de la nuit
                } else if (code == KeyCode.F) {
                    generatePotion("redPotion");
                } else if (code == KeyCode.N) {
                    generateMagicCape();
                } else if (code == KeyCode.DIGIT6) {
                    //generateRandomGuardian();
                } else if (code == KeyCode.R) {
                    // System.out.println("gardien rouge bloqué");
                    canRedGuardianMove = !canRedGuardianMove;
                } else if (code == KeyCode.B) {
                    canBlueGuardianMove = !canBlueGuardianMove;
                } else if (code == KeyCode.M) {
                    canPurpleGuardianMove = !canPurpleGuardianMove;
                } else if (code == KeyCode.O) {
                    canOrangeGuardianMove = !canOrangeGuardianMove;
                } else if (code == KeyCode.S) {
                    System.out.println("fonction supplemnatire desactiver");
                    // option supplémenataire
                } 
            }
        });

        generateWall();
        generateTower();
        generatePortal();
        generateMagicCape();
        generateGuardian();
        generateHero();
        
        // ajout des noms des potions pour les differencier

        potionNameList.add("redPotion");
        potionNameList.add("orangePotion");
        potionNameList.add("yellowPotion");
        
        for (String name : potionNameList) {
            generatePotion(name);    
        }

        for (int i = 0; i < 2; i++) {
            generateMagicCape();
        }

        for (int i = 0; i < coinCounter; i++) {
            generateCoin();
        }

        /*Timeline purpleGuardianTimeline = new Timeline(new KeyFrame(Duration.millis(600), e -> runPurpleGuardian())); // a chaque laps de temp on execute run le gc
        purpleGuardianTimeline.setCycleCount(Animation.INDEFINITE);
        purpleGuardianTimeline.play(); */

        Timeline otherGuardianTimeline = new Timeline(new KeyFrame(Duration.millis(600), e -> runGuardian())); // a chaque laps de temp on execute run le gc
        otherGuardianTimeline.setCycleCount(Animation.INDEFINITE);
        otherGuardianTimeline.play(); 

        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(60), e -> run())); // a chaque laps de temp on execute run le gc
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play(); // creer 2 timeline differnte 1 pour gardien 1 pour heros*/

    }

    private void runGuardian() {    
        orangeGuardian.moveGuardian(this); // appel de la méthode moveguardian de la classe qui instancie orangeGuardian
        redGuardian.moveGuardian(this);
        purpleGuardian.moveGuardian(this);
        blueGuardian.moveGuardian(this);
    }  

    private void run() {
        if (gameOver) {
            view.drawGameOver();
            return;
        }
        drawBackGround(currentPeriod);
        drawScore();
        drawCollectibleGameElements();
        drawNotCollectibleGameElements();
        gameOver();
        checkPortals();
        consumeItem();      
    }

    public void moveRight() {
        if (hero.getPosX() < view.getRows() - 1) { 
            if (!isWall(hero.getPosX() + 1, hero.getPosY()) && !isTower(hero.getPosX() + 1, hero.getPosY())) {
                hero.setPosX(hero.getPosX() + 1); 
                consumeItem();
                if (isSpeedBoosted == true && hero.getPosX() < view.getRows() - 1 && !isWall(hero.getPosX() + 1, hero.getPosY()) && !isTower(hero.getPosX() + 1, hero.getPosY())) {
                    hero.setPosX(hero.getPosX() + 1); 
                }
            } else if (magicCapeActivated == true && !isTower(hero.getPosX() + 1, hero.getPosY()) && hero.getPosX() != 3 && hero.getPosY() != 6) { // on vérifie les exceptions qui sont les tours et les 4 extrémités des murs
                hero.setPosX(hero.getPosX() + 1);
                desactivateMagicCape();
            }       
        }    
    }

    public void moveLeft() {
        if (hero.getPosX() > 0) {
            if (!isWall(hero.getPosX() - 1, hero.getPosY()) && !isTower(hero.getPosX() - 1, hero.getPosY())) {
                hero.setPosX(hero.getPosX() - 1);
                consumeItem(); 
                if (isSpeedBoosted == true && hero.getPosX() > 0 && !isWall(hero.getPosX() - 1, hero.getPosY()) && !isTower(hero.getPosX() - 1, hero.getPosY())) {
                    hero.setPosX(hero.getPosX() - 1); 
                }
                
            } else if (magicCapeActivated == true && !isTower(hero.getPosX() - 1, hero.getPosY()) && hero.getPosX() != 17 && hero.getPosY() != 6) {
                hero.setPosX(hero.getPosX() - 1);
                desactivateMagicCape();
            } 
        }
    }

    public void moveUp() {
        if (hero.getPosY() > 0) {
            if (!isWall(hero.getPosX(), hero.getPosY() - 1) && !isTower(hero.getPosX(), hero.getPosY() - 1)) {
                hero.setPosY(hero.getPosY() - 1);
                consumeItem();
                if (isSpeedBoosted == true && hero.getPosY() > 0 && !isWall(hero.getPosX(), hero.getPosY() - 1) && !isTower(hero.getPosX(), hero.getPosY() - 1)) { // si il a un speedBoost de active due a une potion il avance de 2 cases
                    hero.setPosY(hero.getPosY() - 1); 
                }
                
            } else if (magicCapeActivated == true && !isTower(hero.getPosX(), hero.getPosY() - 1) && hero.getPosX() != 10 && hero.getPosY() != 10) { 
                hero.setPosY(hero.getPosY() - 1); 
                desactivateMagicCape();
            }       
        }
    }

    public void moveDown() {
        if (hero.getPosY() < view.getColumns() - 1) {
            if (!isWall(hero.getPosX(), hero.getPosY() + 1) && !isTower(hero.getPosX(), hero.getPosY() + 1)) {
                hero.setPosY(hero.getPosY() + 1); 
                consumeItem();
                if (isSpeedBoosted == true && hero.getPosY() < view.getColumns() - 1 && !isWall(hero.getPosX(), hero.getPosY() + 1) && !isTower(hero.getPosX(), hero.getPosY() + 1)) {
                    hero.setPosY(hero.getPosY() + 1); 
                }
                
            } else if (magicCapeActivated == true && !isTower(hero.getPosX(), hero.getPosY() + 1) && hero.getPosX() != 10 && hero.getPosY() != 2) {
                hero.setPosY(hero.getPosY() + 1); 
                desactivateMagicCape();
            }
        }
    }    

    public void consumeItem() {
        for (Coin coin : coinList) {
            if (hero.getPosX() == coin.getPosX() 
            && hero.getPosY() == coin.getPosY()) {
                coin.triggerAction(this); // appel de la méthode triggeraction de la classe qui instancie coin
            }
        }
        for (Potion potion : potionList) {
            if (hero.getPosX() == potion.getPosX() 
            && hero.getPosY() == potion.getPosY()) {
                potion.triggerAction(this); // appel de la méthode triggeraction de la classe qui instancie potion
            }
        }
        for (MagicCape magicCape : magicCapeList) {
            if (hero.getPosX() == magicCape.getPosX() 
            && hero.getPosY() == magicCape.getPosY()) {
                magicCape.triggerAction(this); // appel de la méthode triggeraction de la classe qui instancie magicCape
            }
        }
    }

    public boolean isWall(int posX, int posY) { // prend 2 paramétres de positions et les compare avec les positions de tous les murs 
        for (Wall wall : wallList) {
            if (wall.getPosX() == posX && wall.getPosY() == posY) {
            return true; // vraie c'est un mur
            }        
        }
        return false; // faux ce n'est pas un mur
    }

    public boolean isTower(int posX, int posY) { // prend 2 paramétres de positions et les compare avec les positions de toutes les tours 
        for (Wall tower : towerList) {
            if (tower.getPosX() == posX && tower.getPosY() == posY) {
            return true; // vraie c'est une tour
            }
        }
        return false; // faux ce n'est pas une tour
    }

    public void checkPortals() {
        for (Portal portal : portalList) {
            if (hero.getPosX() == portal.getPosX() && hero.getPosY() == portal.getPosY()) { // si le joueur se trouve sur la position d'un portail on le téléporte à l'autre portail correspondant
                hero.setPosX(portal.getDestX());
                hero.setPosY(portal.getDestY());
                // break;
            }
        }
    }

    public void activateMagicCape() {
        magicCapeActivated = true;
    }

    public void desactivateMagicCape() {
        magicCapeActivated = false;
    }

    public void gameOver() { // si il y'a une collision du héro avec 1 des 4 gardiens présents sur la carte c'est GAME OVER
        if (hero.getPosX() == orangeGuardian.getPosX() && hero.getPosY() == orangeGuardian.getPosY() || 
        hero.getPosX() == blueGuardian.getPosX() && hero.getPosY() == blueGuardian.getPosY() ||
        hero.getPosX() == purpleGuardian.getPosX() && hero.getPosY() == purpleGuardian.getPosY() || 
        hero.getPosX() == redGuardian.getPosX() && hero.getPosY() == redGuardian.getPosY()) {
            gameOver = true;
        }
    }

    public void applySpeedBoost(int boostDuration) {
        if (!isSpeedBoosted) {  // si le booleen est true
        /*System.out.println("vitesse actuelle du hero " + getHeroSpeed());
        hero.setSpeed(getHeroSpeed() + 2); // applique le boost de vitesse
        System.out.println("Boost de vitesse activé pour " + boostDuration + " secondes");
        System.out.println("vitesse actuelle du hero " + getHeroSpeed());*/

        isSpeedBoosted = true;

        Timeline potionTimeline = new Timeline(new KeyFrame(Duration.seconds(boostDuration), event -> {
            //isSpeedBoosted = false;
            //hero.setSpeed(getHeroSpeed());
            //System.out.println("Boost de vitesse désactivé " + boostDuration + " se sont écoulé...");
        }));
        potionTimeline.setCycleCount(1);
        potionTimeline.play();
        }
    }

    public void resetGame() {

        this.currentScore = 0;
        this.collectedCoins = 0;
        this.coinCounter = 8;

        for (int i = 0; i < 2; i++) {
            generateMagicCape();
        }

        for (String name : potionNameList) {
            generatePotion(name);
        }

        for (int i = 0; i < coinCounter - collectedCoins; i++) {
            generateCoin();
        }
        generateGuardian();
        drawBackGround(1);
        // cacher les gardiens car ils apparaisent a 25% des piéces
    }

    public void generateHero() {

        notCollectibleGameElementList.add(hero);

        imageMap.put(heroClassDescriptionString, heroImage);
    }

    public void generateGuardian() {

        notCollectibleGameElementList.add(orangeGuardian);
        notCollectibleGameElementList.add(purpleGuardian);
        notCollectibleGameElementList.add(blueGuardian);
        notCollectibleGameElementList.add(redGuardian);

        imageMap.put(orangeGuardianClassDescriptionString, orangeGuardianImage);
        imageMap.put(purpleGuardianClassDescriptionString, purpleGuardianImage);
        imageMap.put(blueGuardianClassDescriptionString, blueGuardianImage);
        imageMap.put(redGuardianClassDescriptionString, redGuardianImage);
    }

    public void generatePortal() {

        Portal bluePortal = new Portal(0, 6, 19, 6, "blue", "portalBlue");
        Portal bluePortal1 = new Portal(20, 6, 1, 6, "blue", "portalBlue");
        Portal greenPortal = new Portal(10, 0, 10, 11, "green", "portalGreen");
        Portal greenPortal1 = new Portal(10, 12, 10, 1, "green", "portalGreen");

        portalList.add(bluePortal);
        portalList.add(bluePortal1);
        portalList.add(greenPortal);
        portalList.add(greenPortal1);

        notCollectibleGameElementList.addAll(portalList);

        for (Portal portal : portalList) {
            String imagePath = portal.getPathToImage();
            Image portalImage = new Image(imagePath);
            imageMap.put(portal.getName(), portalImage);
        }
    }
    
    public void generateTower() {

        Wall tower1 = new Wall(2,2);
        Wall tower2 = new Wall(18,2);
        Wall tower3 = new Wall(2,10);
        Wall tower4 = new Wall(18,10);

        towerList.add(tower1);
        towerList.add(tower2);
        towerList.add(tower3);
        towerList.add(tower4);

        notCollectibleGameElementList.addAll(towerList);  

        imageMap.put(wallClassDescriptionString, wallImage);
    }

    public void generateWall() {

        for (int i = 4; i < 17; i++) {
                Wall horizontalWall = new Wall(i, 6);
                wallList.add(horizontalWall);
                notCollectibleGameElementList.add(horizontalWall);
        }

        for (int j = 3; j < 10; j++) {
                Wall verticalWall = new Wall(10, j);
                wallList.add(verticalWall);
                notCollectibleGameElementList.add(verticalWall);
        }

        imageMap.put(wallClassDescriptionString, wallImage);
    }

    public void generateMagicCape() {
        
        start:
        while (true) {
            int randomXPos = (int) (Math.random() * view.getRows());
            int randomYPos = (int) (Math.random() * view.getColumns());
            
            if (hero.getPosX() == randomXPos && hero.getPosY() == randomYPos || isWall(randomXPos, randomYPos)) { // pas générer des capes sur la position du héro ainsi que sur les murs 
                continue start;
            }

            for (MagicCape otherMagicCape : magicCapeList) {
                if (otherMagicCape.getPosX() == randomXPos && otherMagicCape.getPosY() == randomYPos) { // pas générer des capes sur les autres capes
                    continue start;
                }
            }

            for (Portal portal : portalList) {
                if (portal.getPosX() == randomXPos && portal.getPosY() == randomYPos) { // pas générer des portails sur les capes
                    continue start;
                }
            }
    
            for (Potion potion : potionList) {
                if (potion.getPosX() == randomXPos && potion.getPosY() == randomYPos) { // pas générer des potions sur les capes
                    continue start;
                }
            }

            for (Coin coin : coinList) {
                if (coin.getPosX() == randomXPos && coin.getPosY() == randomYPos) { // pas générer des piéces sur les capes
                    continue start;
                }
            }
            
            MagicCape newMagicCape = new MagicCape(randomXPos, randomYPos);
            magicCapeList.add(newMagicCape);
            collectibleGameElementList.add(newMagicCape);
            break;
        }

        imageMap.put(magicCapeClassDescriptionString, magicCapeImage);
    }

    public void generatePotion(String name) {

        start:
        while (true) {
            int randomXPos = (int) (Math.random() * view.getRows());
            int randomYPos = (int) (Math.random() * view.getColumns());
            int randomPotion = (int) (Math.random()* 3);
            
            if (hero.getPosX() == randomXPos && hero.getPosY() == randomYPos || isWall(randomXPos, randomYPos)) { // pas générer des potions sur la position du héro ainsi que sur les murs 
                continue start;
            }

            for (MagicCape magicCape : magicCapeList) {
                if (magicCape.getPosX() == randomXPos && magicCape.getPosY() == randomYPos) { // pas générer des potions sur les capes
                    continue start;
                }
            }

            for (Portal portal : portalList) {
                if (portal.getPosX() == randomXPos && portal.getPosY() == randomYPos) { // pas générer des potions sur les portails
                    continue start;
                }
            }
    
            for (Potion otherPotion : potionList) {
                if (otherPotion.getPosX() == randomXPos && otherPotion.getPosY() == randomYPos) { // pas générer des potions sur les autres potions
                    continue start;
                }
            }

            for (Coin coin : coinList) {
                if (coin.getPosX() == randomXPos && coin.getPosY() == randomYPos) { // pas générer des potions sur les pieces
                    continue start;
                }
            }
    
            Potion newPotion = new Potion(randomXPos, randomYPos, randomPotion, name);      
            potionList.add(newPotion);
            collectibleGameElementList.addAll(potionList);
            Image potionImage = new Image(newPotion.getPathToImage());
            imageMap.put(newPotion.getName(), potionImage);
            
            break;
        }
    }
    
    public void generateCoin() {

        start:
        while (true) {
            int randomXPos = (int) (Math.random() * view.getRows());
            int randomYPos = (int) (Math.random() * view.getColumns());
            
            if (hero.getPosX() == randomXPos && hero.getPosY() == randomYPos || isWall(randomXPos, randomYPos)) { // pas générer des piéces sur la position du héro ainsi que sur les murs  
                continue start;
            }

            for (MagicCape magicCape : magicCapeList) {
                if (magicCape.getPosX() == randomXPos && magicCape.getPosY() == randomYPos) { // pas générer des piéces sur les capes
                    continue start;
                }
            }

            for (Portal portal : portalList) {
                if (portal.getPosX() == randomXPos && portal.getPosY() == randomYPos) { // pas générer des piéces sur les portails
                    continue start;
                }
            }
    
            for (Potion potion : potionList) {
                if (potion.getPosX() == randomXPos && potion.getPosY() == randomYPos) { // pas générer des piéces sur les potions
                    continue start;
                }
            }

            for (Coin otherCoin : coinList) {
                if (otherCoin.getPosX() == randomXPos && otherCoin.getPosY() == randomYPos) { // pas générer des piéces sur les autres piéces
                    continue start;
                }
            }
            
            Coin newCoin = new Coin(randomXPos, randomYPos);
            coinList.add(newCoin);
            collectibleGameElementList.add(newCoin);
            break;
        }

        imageMap.put(coinClassDescriptionString, coinImage);
    }

    private void drawBackGround(int period) {
        currentPeriod = period;
        for (int i = 0; i < view.getRows(); i++) {
            for (int j = 0; j < view.getColumns(); j++) {
                if ((i + j) % 2 == 0 && currentPeriod == morning) {
                    view.drawBackGroundColorMorning("FF69B4");
                } else if (currentPeriod == morning) {
                    view.drawBackGroundColorMorning("FFE4B5");
                } else if ((i + j) % 2 == 0 && currentPeriod == day) {
                    view.drawBackGroundColorDay("00FA9A");
                } else if (currentPeriod == day) {
                    view.drawBackGroundColorDay("3CB371");
                } else if ((i + j) % 2 == 0 && currentPeriod == night) {
                    view.drawBackGroundColorEvening("000000");
                } else if (currentPeriod == night) {
                    view.drawBackGroundColorEvening("808080");
                }
                view.drawBackGround(i, j);
            }
        }
    }

    /*private void updateScore(int newScore) {
        currentScore = newScore;
        int bestScore = loadBestScore();

        if (currentScore > bestScore) {
            bestScore = currentScore;
            saveBestScore(bestScore);
        }

        drawScore();
    }

    private void saveBestScore(int bestScore) {
        try (PrintWriter writer = new PrintWriter(BEST_SCORE_FILE)) {
            writer.println(bestScore);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private int loadBestScore() {
        int bestScore = 0;

        try (BufferedReader reader = new BufferedReader(new FileReader(BEST_SCORE_FILE))) {
            String line = reader.readLine();
            if (line != null) {
                bestScore = Integer.parseInt(line.trim());
            }
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }

        return bestScore;
    }*/

    private void drawScore() {   
        view.drawScore(currentScore, bestScore);
    }
    
    private void drawCollectibleGameElements() {
        for (CollectibleGameElement gameElemColl : collectibleGameElementList) {       
            if (gameElemColl.getClass().getName() == "Potion") {
                view.drawCollectibleGameElements("potion", gameElemColl, imageMap);
        } else {
                view.drawCollectibleGameElements(gameElemColl, imageMap);
                }                   
            }
        }

    private void drawNotCollectibleGameElements() {
        for (NotCollectibleGameElement gameElemNotColl : notCollectibleGameElementList) { 
            if (gameElemNotColl.getClass().getName() == "Portal") {
                    view.drawNotCollectibleGameElements("portal", gameElemNotColl, imageMap);
            } else {
                    view.drawNotCollectibleGameElements(gameElemNotColl, imageMap);
                }                        
            }
        }

    public static void main(String[] args) {
        launch(args);
    }
}
